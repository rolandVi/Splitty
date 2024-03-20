package client.scenes.admin;


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


public class AdminOverviewPageCtrl {

    private final AdminMainCtrl adminMainCtrl;
    private final ServerUtils serverUtils;

    @FXML
    private VBox eventContainer;


    /**
     * Injector for EventOverviewCtrl
     * @param adminMainCtrl the admin controller
     * @param serverUtils   the server utilities
     */
    @Inject
    public AdminOverviewPageCtrl( AdminMainCtrl adminMainCtrl, ServerUtils serverUtils){
        this.adminMainCtrl = adminMainCtrl;
        this.serverUtils=serverUtils;
    }



    /**
     * Loads the events and displays them on the page
     */
    public void loadEvents() {
        List<EventOverviewDto> events = this.serverUtils.getAllEvents();
        Node[] nodes=new Node[events.size()];


        for (int i = 0; i < nodes.length; i++) {
            var loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader()
                    .getResource(Path.of("client.scenes", "adminEventItem.fxml").toString()));
            try {
                nodes[i]=loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Node currentNode=nodes[i];
            final EventOverviewDto event=events.get(i);

            Button eventButton = (Button) currentNode.lookup("#eventTitle");
            eventButton.setText(events.get(i).getTitle());
            eventButton.setOnAction(e -> showDetails(event.getId()));

            Button inviteBtn=(Button) currentNode.lookup("#jsonBtn");
            inviteBtn.setOnAction(e -> getJSON(event.getId()));

            Button deleteBtn= (Button) currentNode.lookup("#deleteBtn");
            deleteBtn.setOnAction(e -> deleteEvent(event.getId()));
        }
        this.eventContainer.getChildren().clear();
        this.eventContainer.getChildren().addAll(nodes);
    }

    /**
     * Deletes the event
     * @param id the id of the event
     */
    private void deleteEvent(long id) {
        //TODO: delete the event
    }

    /**
     * Shows the event details page
     * @param id the id of the event
     */
    private void showDetails(long id) {
        //todo: get admin details?
    }

    /**
     * Copies a json of the chosen event
     * @param id the id of the event
     */
    public void getJSON(Long id){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        //TODO: get the JSON dump
        clipboard.setContent(content);
    }

    /**
     * Refreshes the page()
     * //todo: remove this when web sockets are implemented
     */
    public void refresh(){
        adminMainCtrl.showAdminOverview();
    }
}
