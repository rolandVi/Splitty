package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.dto.view.EventDetailsDto;

import java.util.ResourceBundle;

public class EventCtrl implements MultiLanguages{
    private final MainCtrl mainCtrl;

    private final ServerUtils serverUtils;
    @FXML
    public Label eventNameLabel;
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
    @FXML
    public Button addParticipant;
    private EventDetailsDto eventDetailsDto;

    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     * @param serverUtils The Server Utilities
     */
    @Inject
    public EventCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }
    /**
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = mainCtrl.lang;
            returnButton.setText(lang.getString("return"));
            changeTextField.setPromptText(lang.getString("event_name"));
            changeButton.setText(lang.getString("change"));
            participantsLabel.setText(lang.getString("participants"));
            expensesLabel.setText(lang.getString("expenses"));
            addExpenseButton.setText(lang.getString("add_expense"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverview(){
        mainCtrl.showOverview();
    }


    /**
     * Updates teh view information with the details of the event with the given id
     * @param id the id of the event
     */
    public void init(long id) {
        var event=serverUtils.getEventDetails(id);
        eventNameLabel.setText(event.getTitle());
    }

    /**
     * Will update the event name to the server and update the current event name
     * @throws JsonProcessingException when the objectMapper cannot properly
     * turn the EventTitleDto into Json format string
     */
    public void changeEventName() throws JsonProcessingException {
        serverUtils.changeEventName(1L, changeTextField.getText());
        this.eventNameLabel.setText(this.changeTextField.getText());
        this.changeTextField.setText("");
    }

    /**
     * Will show add expense scene, allowing the user to add an expense
     */
    public void addExpense(){
        mainCtrl.showNewExpense();
    }

    /**
     * Getter for the eventDetailsDto
     * @return the Data transfer object of current event showing
     */
    public EventDetailsDto getEventDetailsDto() {
        return eventDetailsDto;
    }

    /**
     * Setter for the eventDetailsDto
     * @param eventDetailsDto the Data transfer object of current event showing
     */
    public void setEventDetailsDto(EventDetailsDto eventDetailsDto) {
        this.eventDetailsDto = eventDetailsDto;
    }

    /**
     * shows the newParticipant scene
     */
    public void newParticipant(){
        mainCtrl.showNewParticipant();
    }

}
