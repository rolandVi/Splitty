package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.checkerframework.checker.index.qual.IndexFor;

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

    @Inject
    public EnrollEventCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }

    public void enrollInEvent(){
        serverUtils.enrollInEvent(inviteCodeField.getText().trim());
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
