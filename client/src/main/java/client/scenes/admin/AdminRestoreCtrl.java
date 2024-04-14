package client.scenes.admin;

import client.scenes.MultiLanguages;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ResourceBundle;

public class AdminRestoreCtrl implements MultiLanguages {
    private final AdminMainCtrl adminMainCtrl;
    @FXML
    public Label titleLabel;
    @FXML
    public Button restoreBtn;
    @FXML
    public TextArea textField;
    @FXML
    public Button returnBtn;

    @FXML
    public Text restoreMessage;

    @FXML
    private AnchorPane rootPane;

    private final ServerUtils serverUtils;


    /**
     * Injector for Restore control
     * @param adminMainCtrl the admin main control
     * @param serverUtils the ServerUtils
     */
    @Inject
    public AdminRestoreCtrl(AdminMainCtrl adminMainCtrl, ServerUtils serverUtils) {
        this.adminMainCtrl = adminMainCtrl;
        this.serverUtils = serverUtils;
    }
    /**
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = adminMainCtrl.lang;
            titleLabel.setText(lang.getString("restore_title"));
            restoreBtn.setText(lang.getString("restore"));
            textField.setPromptText(lang.getString("restore_body"));
            restoreMessage.setText(lang.getString("restore_message"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Will return to the admin overview
     */
    public void returnOverview() {
        restoreMessage.setOpacity(0);
        adminMainCtrl.showAdminOverview();
    }


    /**
     * Method to create a POST to the server with the JSON,
     * which, if correct, will restore the event related to the JSON dump
     */
    public void restoreData() {
        String jsonData = textField.getText();
        textField.clear();
        restoreMessage.setOpacity(1);
        serverUtils.restoreData(jsonData);

    }

    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) throws IOException, InterruptedException {
        switch (e.getCode()) {
            case ENTER:
                restoreData();
                break;
            case ESCAPE:
                returnOverview();
                break;
            default:
                break;
        }
    }

    /**
     * Method to ensure that when the scene is left and re-entered the text field will be cleared
     */
    @FXML
    public void initialize() {
        // Add listener to scene property to handle scene showing event
        restoreMessage.setOpacity(0);
        rootPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // Scene is being shown for the first time
                // Add listener to handle scene hiding event
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow != null && newWindow == null) {
                        // Scene is being hidden
                        if (!textField.getText().isEmpty()) {
                            // If text field is not empty, clear it
                            textField.clear();
                        }
                    }
                });
            }
        });
    }

}
