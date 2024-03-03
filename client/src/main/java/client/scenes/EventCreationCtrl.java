package client.scenes;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EventCreationCtrl {
    private final MainCtrl mainCtrl;
    @FXML
    public TextField eventNameTextField;
    @FXML
    public Button createButton;
    @FXML
    public Button returnButton;

    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     */
    @Inject
    public EventCreationCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverView(){
        mainCtrl.showOverview();
    }
    /**
     * Will create the event and add it to the database
     */
    public void createEvent(){
        String eventName = eventNameTextField.getText();
        // To be implemented
        // todo PUT operation to the server
    }

}
