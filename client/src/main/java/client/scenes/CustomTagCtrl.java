package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TagEntity;
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
import javafx.scene.text.Text;

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
    @FXML
    public Text errorMessage;
    private String tagTypeSave;

    /**
     * Injected constructor
     * @param mainCtrl mainctrl
     * @param serverUtils serverUtils
     * @param newExpenseCtrl newExpenseCtrl
     */
    @Inject
    public CustomTagCtrl(MainCtrl mainCtrl, ServerUtils serverUtils,
                         NewExpenseCtrl newExpenseCtrl) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.newExpenseCtrl = newExpenseCtrl;
    }

    /**
     * Lets the user create a custom tag
     */
    public void createCustomTag() {
        String tagType = tagName.getText();
        String hex = colorPick.getValue().toString();
        TagDto tagDto = new TagDto();
        tagDto.setTagType(tagType);
        tagDto.setHexValue(hex);


        TagEntity existingTag = serverUtils.getTagByTagType(tagType);
        if (existingTag != null) {
            errorMessage.setText("Tag already exists.");
            errorMessage.setOpacity(1.0);
            return;
        }


        serverUtils.createTag(tagDto);
        loadtags();
        newExpenseCtrl.refreshTags();
    }

    /**
     * Loads all the tags from the database into the flowpane
     */
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

    /**
     * Handles some functionality in the flowpane
     * @param tag the selected tag
     */
    private void handleTagButtonClick(TagEntity tag) {
        // Set tag name to the text field
        tagName.setText(tag.getTagType());
        tagTypeSave = tagName.getText();
        // Set color to the color picker
        colorPick.setValue(Color.web(tag.getHexValue()));
    }

    /**
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadtags(); // Load events when the scene is opened
    }

    /**
     * Deletes a tag
     */
    public void deleteTag() {
        String tagType = tagName.getText();

        // Check if the tag already exists in the database
        TagEntity existingTag = serverUtils.getTagByTagType(tagType);
        if (existingTag != null) {
            // Tag exists, delete it
            serverUtils.deleteTag(existingTag.getId());
        } else {
            errorMessage.setText("Could not find tag!");
            errorMessage.setOpacity(1.0);
            return;
        }

        loadtags();
        newExpenseCtrl.refreshTags();
    }

    /**
     * edits a tag
     */
    public void editTag() {
        String oldTagType = tagTypeSave;
        String newTagType = tagName.getText();
        String hex = colorPick.getValue().toString();

        if (serverUtils.getTagByTagType(newTagType) != null && !oldTagType.equals(newTagType)){
            errorMessage.setText("Tag already exists!");
            errorMessage.setOpacity(1.0);
            return;
        }

        TagEntity existingTag = serverUtils.getTagByTagType(oldTagType);
        if (existingTag != null) {
            existingTag.setTagType(newTagType);
            existingTag.setHexValue(hex);
            serverUtils.updateTag(existingTag);
        } else {
            //error handling here
        }

        loadtags();
        newExpenseCtrl.refreshTags();
    }


}

