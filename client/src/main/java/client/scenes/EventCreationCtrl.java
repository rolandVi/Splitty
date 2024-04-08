package client.scenes;

import client.utils.ServerUtils;
import dto.CreatorToTitleDto;
import dto.view.EventDetailsDto;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ResourceBundle;


public class EventCreationCtrl implements MultiLanguages{
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    @FXML
    public Label titleLabel;
    @FXML
    public TextField eventNameTextField;

    @FXML
    public TextField creatorEmailTextField;
    @FXML
    public TextField creatorLastNameTextField;
    @FXML
    public TextField creatorFirstNameTextField;

    @FXML
    public Label errorMessage;

    @FXML
    public Button createButton;
    @FXML
    public Button returnButton;

    @FXML
    private Label creatorLabel;

    @FXML
    private Label eventLabel;

    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     * @param serverUtils The Server Utilities
     */
    @Inject
    public EventCreationCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }

    /**
     * Sets the initial state of the page
     */
    public void init(){
        invalidateFields();
    }

    /**
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = mainCtrl.lang;
            titleLabel.setText(lang.getString("event_creation"));
            returnButton.setText(lang.getString("return"));
            eventNameTextField.setPromptText(lang.getString("event_name"));
            createButton.setText(lang.getString("create"));
            creatorLabel.setText(lang.getString("event_details_label"));
            eventLabel.setText(lang.getString("creator_details_label"));
            creatorFirstNameTextField.setPromptText(lang.getString("first_name_placeholder"));
            creatorLastNameTextField.setPromptText(lang.getString("last_name_placeholder"));
            creatorEmailTextField.setPromptText(lang.getString("email_placeholder"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverView(){
        mainCtrl.showOverview();
    }

    /**
     * Creates HTTP request to the server using the contents of text field as name of event
     */
    public void createEvent() {
        if (eventNameTextField.getText().isEmpty() ||
            creatorLastNameTextField.getText().isEmpty() ||
            creatorFirstNameTextField.getText().isEmpty()){
            errorMessage.setVisible(true);
            return;
        }

        EventDetailsDto newEvent=serverUtils.createEvent(new CreatorToTitleDto(
                creatorFirstNameTextField.getText(),
                creatorLastNameTextField.getText(),
                creatorEmailTextField.getText(),
                eventNameTextField.getText()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(mainCtrl.lang.getString("add_event_alert_header"));
        alert.setContentText(mainCtrl.lang.getString("add_event_alert_content")
                + " " + eventNameTextField.getText());
        alert.showAndWait().ifPresent(response -> this.mainCtrl.showEventDetails(newEvent.getId()));


        this.errorMessage.setVisible(false);
        this.eventNameTextField.setText("");
        this.creatorEmailTextField.setText("");
        this.creatorFirstNameTextField.setText("");
        this.creatorLastNameTextField.setText("");
    }


    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) throws IOException, InterruptedException {
        switch (e.getCode()) {
            case ENTER:
                createEvent();
                break;
            case ESCAPE:
                returnToOverView();
                break;
            default:
                break;
        }
    }

    /**
     * Resets the text fields
     */
    private void invalidateFields(){
        this.eventNameTextField.setText("");
        this.creatorEmailTextField.setText("");
        this.creatorFirstNameTextField.setText("");
        this.creatorLastNameTextField.setText("");
    }
}
