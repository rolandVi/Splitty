package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TagEntity;
import dto.view.TagDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class CustomTagCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    @FXML
    public Button addCustomTagBtn;
    @FXML
    public Button back;
    @FXML
    public TextField tagName;
    @FXML
    public FlowPane flowPane;
    @FXML
    public ColorPicker colorPick;

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
        String hex = colorPick.getValue().toString();
        TagDto tagDto = new TagDto();
        tagDto.setTagType(tagType);
        tagDto.setHexValue(hex);
        serverUtils.createTag(tagDto);
    }

}
