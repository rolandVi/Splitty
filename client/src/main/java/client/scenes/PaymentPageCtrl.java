package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

public class PaymentPageCtrl {

    private final MainCtrl mainCtrl;
    @FXML
    public Button sendButton;
    @FXML
    public Button showOpenButton;
    @FXML
    public Button showAllButton;
    @FXML
    public Button goBackButton;

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
     * Will show event overview when the goBack button is pressed
     */
    public void returnToOverview(){
        mainCtrl.showOverview();
    }

    /**
     * Will show the user their open payments
     */
    public void showOpenPayments(){
        // scene needs to be made
    }

    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case BACK_SPACE:
                returnToOverview();
                break;
            default:
                break;
        }
    }



}
