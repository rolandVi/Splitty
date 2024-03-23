package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

import java.util.ResourceBundle;

public class PaymentPageCtrl implements MultiLanguages {

    private final MainCtrl mainCtrl;
    @FXML
    public Button sendButton;
    @FXML
    public Button showOpenButton;
    @FXML
    public Button showAllButton;
    @FXML
    public Button goBackButton;
    @FXML
    public Button addBank;

    /**
     * Injector for PaymentPageCtrl
     * @param mainCtrl The Main Controller
     */
    @Inject
    public PaymentPageCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }
    /**
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = mainCtrl.lang;
            showOpenButton.setText(lang.getString("open"));
            showAllButton.setText(lang.getString("all"));
            goBackButton.setText(lang.getString("return"));
            sendButton.setText(lang.getString("send"));
        } catch (Exception e) {
            System.out.println("Incorrect key");
        }
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

    /**
     * Allows the user to access the page to add bank information to their accounts
     */
    public void addBankInfo(){
        mainCtrl.showAddNewBank();
    }



}
