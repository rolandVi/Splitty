package client.scenes;

import client.utils.ServerUtils;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;


public class EventCreationCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
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
     * Will show event overview when the return button is pressed
     */
    public void returnToOverView(){
        mainCtrl.showOverview();
    }

    /**
     * Creates HTTP request to the server using the contents of text field as name of event
     * @return HTTP response from the server
     */
    public void createEvent() {
        mainCtrl.showOverview();
        serverUtils.createEvent(eventNameTextField.getText());
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
