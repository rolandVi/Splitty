package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CustomTagCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    @FXML
    public Button addCustomTagBtn;
    @FXML
    public Button back;
    @FXML
    public TextField tagName;

    @Inject
    public CustomTagCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }

    public void showCreateExpense(){
        mainCtrl.showNewExpense();
    }

    public void createCustomTag(){
        String tagType = tagName.getText();
        serverUtils.createTag(tagType);
    }
}
