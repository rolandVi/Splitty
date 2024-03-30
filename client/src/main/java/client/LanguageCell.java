package client;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class LanguageCell extends ListCell<String> {
    /**
     * Updates the items
     * @param item The new item for the cell.
     * @param empty whether or not this cell represents data from the list. If it
     *        is empty, then it does not represent any domain data, but is a cell
     *        being used to render an "empty" row.
     */
    protected void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if (item!=null){
            String flagPath = "/images/flags/flag_" + item + ".png";
            Image flagImage = null;
            try {
                flagImage = new Image(Objects.requireNonNull(getClass()
                        .getResourceAsStream(flagPath)),
                        40, 40, true, true);
            } catch (Exception e) {
                flagImage = new Image(Objects.requireNonNull(getClass()
                        .getResourceAsStream("/images/not_found.png")),
                        40, 40, true, true);
            }
            setGraphic(new ImageView(flagImage));
        }
    }
}
