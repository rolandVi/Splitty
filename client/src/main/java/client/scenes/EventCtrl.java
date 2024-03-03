package client.scenes;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EventCtrl {
    private final MainCtrl mainCtrl;
    @FXML
    public Button returnButton;
    @FXML
    public TextField changeTextField;
    @FXML
    public Button changeButton;
    @FXML
    public Label participantsLabel;
    @FXML
    public Label inviteCode;
    @FXML
    public Label expensesLabel;
    @FXML
    public Button addExpenseButton;

    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     */
    @Inject
    public EventCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverview(){
        mainCtrl.showOverview();
    }

    /**
     * Will update the event name to the server and updates the current event name
     */
    public void changeEventName(){
        String eventName = changeTextField.getText();
        // To be implemented
        // todo UPDATE operation to the server
        // todo GET operation to the server
    }

    /**
     * Will show add expense scene, allowing the user to add an expense
     */
    public void addExpense(){
        mainCtrl.showNewExpense();
    }

}
