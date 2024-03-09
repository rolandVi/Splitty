package client.scenes;

import client.Main;

import client.sceneUtils.LanguageComboBoxUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import com.google.inject.Inject;

import java.util.List;
import java.util.ResourceBundle;

public class StartPageCtrl implements MultiLanguages{

    private final MainCtrl mainCtrl;

    @FXML
    public TextField serverField;

    @FXML
    public Text errorMessage;

    @FXML
    public Button connectButton;
    @FXML
    public Button openAdminButton;
    @FXML
    public ComboBox<String> languageComboBox;


    /**
     * The constructor
     *
     * @param mainCtrl The main controller
     */
    @Inject
    public StartPageCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void initialize() {
        LanguageComboBoxUtil.updateLanguageComboBox(languageComboBox);
    }
    public void uponSelectionLanguage(){
        String[] selection = languageComboBox.getValue().split("-");
        LanguageComboBoxUtil.setLocaleFromConfig(selection[0], selection[1]);
        mainCtrl.updateLanguagesOfScenes();
    }
    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = mainCtrl.lang;
            connectButton.setText(lang.getString("connect"));
            openAdminButton.setText(lang.getString("open_admin"));
        } catch (Exception e) {
            System.out.println("Incorrect key");
        }
    }

    /**
     * The button press activates this
     */
    public void connect() {
        String serverInserted = serverField.getText();

        if (!serverInserted.equals("http://localhost:8080")) {
            errorMessage.setOpacity(1.0d);
        } else {
            mainCtrl.showOverview();
        }
    }

    /**
     * Open new admin overview window through Main
     */
    public void enterAdmin(){
        Main.openAdminOverview();
    }

    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                connect();
                break;
            default:
                break;
        }
    }


    /**
     * Refreshes the page, not needed now
     */
    public void refresh() {
        //server calls missing
    }
}
