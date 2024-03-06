package client.scenes;

import com.google.inject.Inject;
import commons.dto.view.EventTitleDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventOverviewCtrl implements Initializable {



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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
