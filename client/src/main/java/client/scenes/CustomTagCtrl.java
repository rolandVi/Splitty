package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TagEntity;
import dto.view.EventOverviewDto;
import dto.view.TagDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class CustomTagCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    private final NewExpenseCtrl newExpenseCtrl;
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
    private String tagTypeSave;

    @Inject
    public CustomTagCtrl(MainCtrl mainCtrl, ServerUtils serverUtils, NewExpenseCtrl newExpenseCtrl) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.newExpenseCtrl = newExpenseCtrl;
    }

    public void createCustomTag() {
        String tagType = tagName.getText();
        String hex = colorPick.getValue().toString();
        TagDto tagDto = new TagDto();
        tagDto.setTagType(tagType);
        tagDto.setHexValue(hex);
        serverUtils.createTag(tagDto);
        loadtags();
        newExpenseCtrl.refreshTags();
    }

    public void loadtags() {
        List<TagEntity> tagDtos = this.serverUtils.getAllTags();
        Node[] nodes = new Node[tagDtos.size()];

        for (int i = 0; i < nodes.length; i++) {
            var loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader()
                    .getResource(Path.of("client.scenes", "tagItem.fxml").toString()));
            try {
                nodes[i] = loader.load();
                TagItemCtrl controller = loader.getController(); // Get the controller
                TagEntity tag = tagDtos.get(i);
                controller.setTag(tag); // Pass tag to controller

                // Add event handler to each button
                Button tagButton = controller.getTagButton();
                tagButton.setOnAction(event -> handleTagButtonClick(tag));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.flowPane.getChildren().clear();
        this.flowPane.getChildren().addAll(nodes);
    }

    private void handleTagButtonClick(TagEntity tag) {
        // Set tag name to the text field
        tagName.setText(tag.getTagType());
        tagTypeSave = tagName.getText();
        // Set color to the color picker
        colorPick.setValue(Color.web(tag.getHexValue()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadtags(); // Load events when the scene is opened
    }

    public void deleteTag() {
        String tagType = tagName.getText();

        // Check if the tag already exists in the database
        TagEntity existingTag = serverUtils.getTagByTagType(tagType);
        if (existingTag != null) {
            // Tag exists, delete it
            serverUtils.deleteTag(existingTag.getId());
        } else {
            // Show an error message or handle the case where the tag doesn't exist
        }

        loadtags();
        newExpenseCtrl.refreshTags();
    }

    public void editTag() {
        String oldTagType = tagTypeSave; // Get the old tag type
        String newTagType = tagName.getText(); // Get the new tag type
        String hex = colorPick.getValue().toString(); // Get the new hex value

        // Check if the tag already exists in the database
        TagEntity existingTag = serverUtils.getTagByTagType(oldTagType);
        if (existingTag != null) {
            // Tag exists, update its tag type and hex value
            existingTag.setTagType(newTagType);
            existingTag.setHexValue(hex);
            serverUtils.updateTag(existingTag);
        } else {
            // Show an error message or handle the case where the tag doesn't exist
        }

        loadtags();
        newExpenseCtrl.refreshTags();
    }


}

