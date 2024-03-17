package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import server.dto.view.EventOverviewDto;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class EventOverviewCtrl {



    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;

    @FXML
    public Button newEventButton;
    @FXML
    public Button paymentButton;

    @FXML
    private VBox eventContainer;


    /**
     * Injector for EventOverviewCtrl
     * @param mainCtrl The Main Controller
     * @param serverUtils the server utilities
     */
    @Inject
    public EventOverviewCtrl(MainCtrl mainCtrl, ServerUtils serverUtils){
        this.mainCtrl = mainCtrl;
        this.serverUtils=serverUtils;
    }

    /**
     * Will take the user to create a new event
     */
    public void newEvent(){
        mainCtrl.showNewEvent();
    }

    /**
     * Will take the user to the payment screen, where they can settle their debts
     */
    public void payment(){
        mainCtrl.showPayment();
    }

    /**
     * Loads the events and displays them on the page
     */
    public void loadEvents() {
        long userId=1L; //TODO: get the id of the current user

//        List<EventOverviewDto> events = this.serverUtils.getEventsByUser(userId);
        List<EventOverviewDto> events = this.serverUtils.getAllEvents();
        Node[] nodes=new Node[events.size()];


        for (int i = 0; i < nodes.length; i++) {
            var loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader()
                    .getResource(Path.of("client.scenes", "eventItem.fxml").toString()));
            try {
                nodes[i]=loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Node currentNode=nodes[i];
            final EventOverviewDto event=events.get(i);

            Button eventButton = (Button) currentNode.lookup("#eventTitle");
            eventButton.setText(events.get(i).getTitle());

            Button inviteBtn=(Button) currentNode.lookup("#inviteCodeButton");
            inviteBtn.setOnAction(e -> copyInvite(event.getInviteCode()));

            eventButton.setOnAction(e -> showDetails(event.getId()));
        }
        this.eventContainer.getChildren().clear();
        this.eventContainer.getChildren().addAll(nodes);
    }

    private void showDetails(long id) {
        mainCtrl.showEventDetails(id);
    }

    /**
     * Event listener that copies the invite code of the selected event to the clipboard
     * @param inviteCode the invite code for the event
     */
    public void copyInvite(String inviteCode){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(inviteCode);
        clipboard.setContent(content);
    }
}
