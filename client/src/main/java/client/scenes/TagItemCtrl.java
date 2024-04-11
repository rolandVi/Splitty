package client.scenes;

import commons.TagEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class TagItemCtrl {
    @FXML
    private Button tag;

    /**
     * setter for the currently selected tag
     * @param tagEntity the tagentity
     */
    public void setTag(TagEntity tagEntity) {
        tag.setText(tagEntity.getTagType()); // Set button text
        Color color = Color.valueOf(tagEntity.getHexValue()); // Convert hex string to Color
        tag.setStyle("-fx-background-color: " + toRGBCode(color) + ";"); // Set background color
    }

    /**
     * helper to convert hex to rgb
     * @param color color
     * @return string with the rgb
     */
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    /**
     * gets the tag from the flowpane
     * @return the 'tag' button
     */
    public Button getTagButton() {
        return tag;
    }
}
