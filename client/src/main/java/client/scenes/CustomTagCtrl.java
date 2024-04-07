package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CustomTagCtrl {
    private final MainCtrl mainCtrl;
    @FXML
    public Button addCustomTagBtn;

    public CustomTagCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }
}
