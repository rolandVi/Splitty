package client.scenes;

import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    public Button createButton;
    @FXML
    public Button returnButton;

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
        //TODO: change to current user
        long userId=1L;
        serverUtils.createEvent(eventNameTextField.getText(), userId);
        mainCtrl.showOverview();
        this.eventNameTextField.setText("");
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
            default:
                break;
        }
    }
}
