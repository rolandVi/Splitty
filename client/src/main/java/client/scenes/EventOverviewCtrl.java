package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EventOverviewCtrl {



    private final MainCtrl mainCtrl;

    @FXML
    public Button newEventButton;
    @FXML
    public Button paymentButton;


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
}
