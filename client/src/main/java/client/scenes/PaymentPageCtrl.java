package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PaymentPageCtrl {

    private final MainCtrl mainCtrl;
    @FXML
    public Button sendButton;
    @FXML
    public Button showOpenButton;
    @FXML
    public Button showAllButton;

    /**
     * Injector for PaymentPageCtrl
     * @param mainCtrl The Main Controller
     */
    @Inject
    public PaymentPageCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }

    /**
     * Will show the user all payments
     */
    public void showAllPayments(){
        // scene needs to be made
    }

    /**
     * Will show the user their open payments
     */
    public void showOpenPayments(){
        // scene needs to be made
    }

}
