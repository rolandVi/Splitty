package client.scenes;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import server.dto.view.EventDetailsDto;
import server.dto.view.UserNameDto;



public class NewExpenseCtrl {
    private final MainCtrl mainCtrl;

    private final ServerUtils serverUtils;

    private EventDetailsDto parentEvent;

    @FXML
    public TextField titleField;
    @FXML
    public TextField amountField;
    @FXML
    public ChoiceBox<UserNameDto> authorChoiceBox;
    @FXML
    public Button createExpenseButton;
    @FXML
    public Button returnButton;

    /**
     * Constructor injection
     * @param mainCtrl - the main controller
     * @param serverUtils - the server utilities
     */
    @Inject
    public NewExpenseCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.parentEvent = null;
    }

    /**
     * sets the id of the parent event
     * @param parentEvent - parent event
     */
    public void setParentEvent(EventDetailsDto parentEvent){

    }

    /**
     * initializes the parent event of the expense
     * and initializes the author choice box
     * @param event - the parent event
     */
    public void init(EventDetailsDto event){
        this.parentEvent = event;
        authorChoiceBox.getItems().addAll(parentEvent.getParticipants());
    }

    /**
     * Returns to the details of the parent event
     */
    public void returnToEvent() {
        mainCtrl.showEventDetails(parentEvent.getId());
    }

    /**
     * creates new expense based on the input
     */
    public void  createExpense(){
        String title = titleField.getText();
        double amount = Double.parseDouble(amountField.getText());
        UserNameDto author = authorChoiceBox.getValue();

        mainCtrl.showEventDetails(parentEvent.getId());

    }
}
