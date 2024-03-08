package client.scenes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import commons.BankAccountEntity;
import commons.UserEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class NewParticipantCtrl {
    private final MainCtrl mainCtrl;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField surNameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField ibanField;
    @FXML
    public TextField bicField;
    @FXML
    public Button goBackButton;
    @FXML
    public Button addButton;

    /**
     * Injector for PaymentPageCtrl
     * @param mainCtrl The Main Controller
     */
    @Inject
    public NewParticipantCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }

    /**
     * Will show event overview when the goBack button is pressed
     */
    public void returnToOverview(){
        mainCtrl.showOverview();
    }

    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case BACK_SPACE:
                returnToOverview();
                break;
            default:
                break;
        }
    }

    /**
     * after new participant is added return to current event
     */
    public void showEvent(){
        mainCtrl.b();
    }

    /**
     * Creates HTTP request to the server using the contents of text fields as user info
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createUser() throws IOException, InterruptedException {
        // Todo: replace temporary value with host selected at start
        String url = "http://localhost:8080";
        createBankAccount();
        // Prepare user data from text fields
        UserEntity user = getUserEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(user);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(url + "/api/users/"))
                .header("Content-Type", "application/json")
                .build();
        showEvent();
        // Send HTTP request to server
        // Return HTTP response from server
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

    private UserEntity getUserEntity() {
        String firstName = firstNameField.getText();
        String surName = surNameField.getText();
        String email = emailField.getText();

        // Create a UserEntity object
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(surName);
        user.setEmail(email);

        return user;
    }

    /**
     * Creates HTTP request to the server using the contents of text fields as user info
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createBankAccount()
            throws IOException, InterruptedException {
        // Todo: replace temporary value with host selected at start
        String url = "http://localhost:8080";

        // Prepare user data from text fields
        String email = emailField.getText();
        String iban = ibanField.getText();
        String bic = bicField.getText();
        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setIban(iban);
        bankAccount.setHolder(email); // Assuming holder's email is the same as the user's email
        bankAccount.setBic(bic);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bankAccount);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(url + "/api/bankaccounts/"))
                .header("Content-Type", "application/json")
                .build();

        // Send HTTP request to server
        // Return HTTP response from server
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

}
