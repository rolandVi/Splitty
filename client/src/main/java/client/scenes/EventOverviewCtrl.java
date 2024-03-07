package client.scenes;

import client.Main;
import client.MyFXML;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
            try {
                var loader=new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource(Path.of("client.scenes", "eventItem.fxml").toString()));
                loader.setController(EventItemCtrl.class);
                nodes[i]=loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Label eventLabel = (Label) nodes[i].lookup("#eventTitle");
            eventLabel.setText(events.get(i));
        }

        this.eventContainer.getChildren().addAll(nodes);
    }
}
