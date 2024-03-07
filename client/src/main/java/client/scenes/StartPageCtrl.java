package client.scenes;

import client.Main;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.BankAccountEntity;
import commons.UserEntity;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import com.google.inject.Inject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class StartPageCtrl {

    private final MainCtrl mainCtrl;

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
    public TextField ibanField;
    @FXML
    public TextField bicField;


    /**
     * The constructor
     *
     * @param mainCtrl The main controller
     */
    @Inject
    public StartPageCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * The button press activates this
     */
    public void connect() throws IOException, InterruptedException {
        String serverInserted = serverField.getText();
        createBankAccount();
        createUser();

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
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createUser() throws IOException, InterruptedException {
        // Todo: replace temporary value with host selected at start
        String url = "http://localhost:8080";

        // Prepare user data from text fields
        UserEntity user = getUserEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(user);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(url + "/api/users/"))
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

    private UserEntity getUserEntity() {
        String firstName = firstNameField.getText();
        String surName = surNameField.getText();
        String email = emailField.getText();
//        String iban = ibanField.getText();
//        String bic = bicField.getText();

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
    public Optional<HttpResponse<String>> createBankAccount() throws IOException, InterruptedException {
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
