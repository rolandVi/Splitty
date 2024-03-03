package client.scenes;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EventCreationCtrl {
    private final MainCtrl mainCtrl;
    @FXML
    public TextField eventNameTextField;
    @FXML
    public Button createButton;
    @FXML
    public Button returnButton;

    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     */
    @Inject
    public EventCreationCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverView(){
        mainCtrl.showOverview();
    }
    /**
     * Creates HTTP request using the contents of text field as name of event
     */
    public void createEvent() throws IOException, InterruptedException {
        String requestBody = eventNameTextField.getText();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create("http://localhost:8080/api/events/"))
                .header("Content-Type", "text/plain")
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());

        mainCtrl.showOverview();
    }

}
