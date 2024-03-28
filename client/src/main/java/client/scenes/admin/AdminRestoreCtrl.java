package client.scenes.admin;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AdminRestoreCtrl {
    private final AdminMainCtrl adminMainCtrl;
    @FXML
    public Button restoreBtn;
    @FXML
    public TextArea textField;
    @FXML
    public Button returnBtn;


    /**
     * Injector for Restore control
     * @param adminMainCtrl the admin main control
     */
    @Inject
    public AdminRestoreCtrl(AdminMainCtrl adminMainCtrl) {
        this.adminMainCtrl = adminMainCtrl;
    }

    /**
     * Will return to the admin overview
     */
    public void returnOverview() {
        adminMainCtrl.showAdminOverview();
    }


    /**
     * Method to create a POST to the server with the,
     * which if correct will restore the event related to the JSON dump
     */
    public void restoreData() {
        String jsonData = textField.getText(); // Get JSON data from TextArea

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/events/restore"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData)).build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201) {
                throw new RuntimeException("Failed to restore data. HTTP status code: "
                        + response.statusCode());
            }
            // Data restored successfully
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle the exception appropriately, e.g., log it
            throw new RuntimeException("Failed to restore data due to an exception: "
                    + e.getMessage(), e);
        }
    }
}
