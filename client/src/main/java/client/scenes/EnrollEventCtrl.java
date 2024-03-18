package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class EnrollEventCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    @FXML
    public TextField inviteCodeField;
    @FXML
    public Button enrollBtn;
    @FXML
    public Button returnButton;

    /**
     * Injector constructor
     * @param mainCtrl teh main controller
     * @param serverUtils the server utils class
     */
    @Inject
    public EnrollEventCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }

    /**
     * Enrolls the current user for the chosen event
     */
    public void enrollInEvent(){
        serverUtils.enrollInEvent(inviteCodeField.getText().trim());
        inviteCodeField.setText("");
        this.mainCtrl.showOverview();
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverView(){
        mainCtrl.showOverview();
    }

    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) throws IOException, InterruptedException {
        switch (e.getCode()) {
            case ENTER:
                enrollInEvent();
                break;
            default:
                break;
        }
    }


}
