package client.scenes.admin;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class AdminRestoreCtrl {
    private final AdminMainCtrl adminMainCtrl;
    @FXML
    public Button restoreBtn;
    @FXML
    public TextArea textField;
    @FXML
    public Button returnBtn;
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
     * Will return to the admin overview
     */
    public void returnOverview() {
        adminMainCtrl.showAdminOverview();
    }


    /**
     * Method to create a POST to the server with the JSON,
     * which if correct will restore the event related to the JSON dump
     */
    public void restoreData() {
        String jsonData = textField.getText();

        serverUtils.restoreData(jsonData);
        textField.clear();
    }

    /**
     * Method to ensure that when the scene is left and re-entered the text field will be cleared
     */
    @FXML
    public void initialize() {
        // Add listener to scene property to handle scene showing event
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
