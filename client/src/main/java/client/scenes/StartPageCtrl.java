package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class StartPageCtrl implements MultiLanguages {

    private final MainCtrl mainCtrl;

    private final ServerUtils serverUtils;

    @FXML
    public TextField serverField;
    @FXML
    public Text errorMessage;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField surNameField;
    @FXML
    public TextField emailField;
    @FXML
    public Label incorrectData;
    @FXML
    public Button connectButton;
    @FXML
    public Button openAdminButton;
    @FXML
    public ComboBox<String> languageBox;
    @FXML
    public ImageView logoImageView;

    @FXML
    Image logo = new Image(getClass().getResourceAsStream("/images/splittyLogo.png"));


    /**
     * The constructor
     *
     * @param mainCtrl    The main controller
     * @param serverUtils the server utils
     */
    @Inject
    public StartPageCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }

    /**
     * Updates the languages of all scenes (except admin)
     */
    public void initialize() {
        String locale = configManager.getProperty("language")
                + "_" + configManager.getProperty("country");
        updateLanguageBox(languageBox, locale);

        displayLogo();
    }

    /**
     * When a language has been selected
     * Update the config file
     * Update all scenes with the new languages
     */
    public void uponSelectionLanguage() {
        String[] selection = languageBox.getValue().split("_");
        MultiLanguages.setLocaleFromConfig(selection[0], selection[1]);
        mainCtrl.updateLanguagesOfScenes();
    }

    /**
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        Locale l = Locale.getDefault();
        String locale = l.getLanguage() + "_" + l.getCountry();

        languageBox.setValue(locale);
        try {
            ResourceBundle lang = mainCtrl.lang;
            connectButton.setText(lang.getString("connect"));
            openAdminButton.setText(lang.getString("open_admin"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * The button press activates this
     */
    public void connect(){
        this.errorMessage.setOpacity(0d);

        String serverInserted = serverField.getText();

        if (!serverInserted.equals("http://localhost:8080")) {
            errorMessage.setOpacity(1.0d);
            return;
        }

        mainCtrl.showOverview();
    }

    /**
     * Open new admin overview window through Main
     */
    public void enterAdmin() {
        Main.openAdminOverview();
    }

    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) throws IOException, InterruptedException {
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

    /**
     * Setter for the logo
     */
    public void displayLogo(){
        logoImageView.setImage(logo);
    }
}
