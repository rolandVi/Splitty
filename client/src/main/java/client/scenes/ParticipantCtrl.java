package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ParticipantCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    @FXML
    private Button deleteButton;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @Inject
    public ParticipantCtrl(MainCtrl mainCtrl, ServerUtils serverUtils){
        this.mainCtrl = mainCtrl;
        this.serverUtils=serverUtils;
    }



}
