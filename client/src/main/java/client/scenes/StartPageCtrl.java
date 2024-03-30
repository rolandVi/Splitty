package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import server.dto.UserCreationDto;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;

import java.util.ResourceBundle;

public class StartPageCtrl implements MultiLanguages{

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
    public Button connectButton;
    @FXML
    public Button openAdminButton;
    @FXML
    public ComboBox<String> languageBox;


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
    }

    /**
     * When a language has been selected
     * Update the config file
     * Update all scenes with the new languages
     */
    public void uponSelectionLanguage() {
        String[] selection = languageBox.getValue().split("_");
        MultiLanguages.setLocaleFromConfig(selection[0], selection[1]);
        updateLanguageBox(languageBox, languageBox.getValue());
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
    public void connect() throws IOException {
        this.incorrectData.setVisible(false);
        this.errorMessage.setOpacity(0d);

        UserCreationDto user = getUserEntity();

        String serverInserted = serverField.getText();
        if (!this.serverUtils.checkUserValidity(user)){
            this.incorrectData.setVisible(true);
            return;
        }


        if (!serverInserted.equals("http://localhost:8080")) {
            errorMessage.setOpacity(1.0d);

        } else {
            mainCtrl.configManager.setProperty("serverURL", serverInserted);
            Optional<HttpResponse<String>> userResponse = createUser(user);
            if (userResponse.isEmpty() || userResponse.get().statusCode()==400){
                this.incorrectData.setVisible(true);
                return;
            }
            saveUserToConfig();
            mainCtrl.showOverview();
        }
    }

    private void saveUserToConfig(){
        mainCtrl.configManager.setProperty("loggedIn", "TRUE");
        mainCtrl.configManager.setProperty("userFirstName", firstNameField.getText());
        mainCtrl.configManager.setProperty("userLastName", surNameField.getText());
        mainCtrl.configManager.setProperty("userMail", emailField.getText());
        mainCtrl.configManager
                .setProperty("userID", String.valueOf(getUserID(emailField.getText())));
        mainCtrl.configManager.saveConfig();
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
     * Creates HTTP request to the server using the contents of text fields as user info
     * @param user th user to create
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createUser(UserCreationDto user)
            throws JsonProcessingException {
        String url = serverField.getText();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(user);

        return serverUtils.createUser(url, requestBody);
    }

    private UserCreationDto getUserEntity() {
        String firstName = firstNameField.getText();
        String surName = surNameField.getText();
        String email = emailField.getText();

        // Create a UserEntity object
        UserCreationDto user = new UserCreationDto();
        user.setFirstName(firstName);
        user.setLastName(surName);
        user.setEmail(email);

        return user;
    }

    /**
     * Creates HTTP request to the server using the email as a parameter
     * @param email the email of the user
     * @return ID of user with the email
     */
    private Long getUserID(String email) {
        String url = mainCtrl.configManager.getProperty("serverURL");
        // Prepare user data from text fields
        email = URLEncoder.encode(email, StandardCharsets.UTF_8);

        return serverUtils.getUserId(url, email);
    }

}
