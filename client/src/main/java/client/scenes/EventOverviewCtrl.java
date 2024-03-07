package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class EventOverviewCtrl {



    private final MainCtrl mainCtrl;

    @FXML
    public Button b; // temporary button to access event scene
    @FXML
    public Button newEventButton;
    @FXML
    public Button paymentButton;

    @FXML
    private VBox eventContainer;


    /**
     * Injector for EventOverviewCtrl
     * @param mainCtrl The Main Controller
     */
    @Inject
    public EventOverviewCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
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
     * temporary button to access event scene
     */
    public void b(){
        mainCtrl.b();
    }

    public void loadEvents() {
        var events=new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            events.add("event"+i);
        }
        var nodes=new Node[events.size()];


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

            Button eventButton = (Button) currentNode.lookup("#eventTitle");
            eventButton.setText(events.get(i));
            eventButton.getStyleClass().clear();

            Label hidden=(Label) currentNode.lookup("#hiddenText");
            hidden.setText(i+1+"");

            Button inviteBtn=(Button) currentNode.lookup("#inviteCodeButton");
            inviteBtn.setOnAction(e -> copyInvite());

            eventButton.setOnAction(e -> showDetails(eventButton, currentNode));
        }
        this.eventContainer.getChildren().clear();
        this.eventContainer.getChildren().addAll(nodes);
    }

    private void showDetails(Button source, Node currentNode) {
        Label hidden=(Label) currentNode.lookup("#hiddenText");
        long id=Long.parseLong(hidden.getText());

        mainCtrl.showEventDetails(id);
    }

    public void copyInvite(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString("Invite code");
        clipboard.setContent(content);
    }
}
