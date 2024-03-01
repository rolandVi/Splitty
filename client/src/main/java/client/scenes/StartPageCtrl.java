package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import com.google.inject.Inject;

import java.util.Objects;

public class StartPageCtrl {

    private final MainCtrl mainCtrl;

    @FXML
    public TextField serverField;

    @FXML
    public Text errorMessage;


    /**
     * The constructor
     * @param mainCtrl The main controller
     */
    @Inject
    public StartPageCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }

    /**
     * The button press activates this
     */
    public void connect(){
        String serverInserted = serverField.getText();

        if (!serverInserted.equals("http://localhost:8080")){
            errorMessage.setOpacity(1.0d);
        } else {
            mainCtrl.showOverview();
        }
    }

    /**
     * Checks for key press
     * @param e The key
     */
    public void keyPressed(KeyEvent e) {
        if (Objects.requireNonNull(e.getCode()) == KeyCode.ENTER) {
            connect();
        }
    }


    /**
     * Refreshes the page, not needed now
     */
    public void refresh(){
        //server calls missing
    }
}
