package client.scenes;

import client.sceneUtils.LanguageComboBoxUtil;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.ResourceBundle;

public class EventCreationCtrl implements MultiLanguages{
    private final MainCtrl mainCtrl;
    @FXML
    public Label titleLabel;
    @FXML
    public TextField eventNameTextField;
    @FXML
    public Button createButton;
    @FXML
    public Button returnButton;
    @FXML
    public ComboBox<String> languageComboBox;

    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     */
    @Inject
    public EventCreationCtrl(MainCtrl mainCtrl) {
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
            titleLabel.setText(lang.getString("event_creation"));
            returnButton.setText(lang.getString("return"));
            eventNameTextField.setPromptText(lang.getString("event_name"));
            createButton.setText(lang.getString("create"));
        } catch (Exception e) {
            System.out.println("Incorrect key");
        }
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverView(){
        mainCtrl.showOverview();
    }

    /**
     * Creates HTTP request to the server using the contents of text field as name of event
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createEvent() throws IOException, InterruptedException {
        // Todo: replace temporary value with host selected at start
        String url = "http://localhost:8080";

        // Create HTTP request body
        String requestBody = eventNameTextField.getText();

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(url + "/api/events/"))
                .header("Content-Type", "text/plain")
                .build();

        // Send HTTP request to server
        // Return HTTP response from server
        mainCtrl.showOverview();
        Optional<HttpResponse<String>> response;
        try {
            response = Optional.of(HttpClient
                            .newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) throws IOException, InterruptedException {
        switch (e.getCode()) {
            case ENTER:
                createEvent();
                break;
            default:
                break;
        }
    }
}
