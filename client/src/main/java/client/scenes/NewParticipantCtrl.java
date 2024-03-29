package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import server.dto.BankAccountCreationDto;
import server.dto.UserCreationDto;

import java.io.IOException;
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

    private final ServerUtils serverUtils;

    /**
     * Injector for PaymentPageCtrl
     * @param mainCtrl The Main Controller
     * @param  serverUtils The ServerUtils
     */
    @Inject
    public NewParticipantCtrl(MainCtrl mainCtrl, ServerUtils serverUtils){
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
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
//        mainCtrl.showEventDetails();
    }
//TODO : We really need to review this class
    /**
     * Creates HTTP request to the server using the contents of text fields as user info
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createUser() throws IOException, InterruptedException {
        String url = mainCtrl.configManager.getProperty("serverURL");
        createBankAccount();
        // Prepare user data from text fields
        UserCreationDto user = getUserEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(user);

        return serverUtils.createUser(url, requestBody);
    }

    private UserCreationDto getUserEntity() {
        String firstName = firstNameField.getText();
        String surName = surNameField.getText();
        String email = emailField.getText();

        // Create a UserEntity object

        return new UserCreationDto()
                .setFirstName(firstName)
                .setLastName(surName)
                .setEmail(email);
    }

    /**
     * Creates HTTP request to the server using the contents of text fields as user info
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createBankAccount()
            throws IOException, InterruptedException {
        String url = mainCtrl.configManager.getProperty("serverURL");
        // Prepare user data from text fields
        String email = emailField.getText();
        String iban = ibanField.getText();
        String bic = bicField.getText();
        BankAccountCreationDto bankAccount = new BankAccountCreationDto()
            .setIban(iban)
            .setHolder(email)// Assuming holder's email is the same as the user's email
            .setBic(bic);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bankAccount);

        return serverUtils.createBankAccount(requestBody, url);
    }

}
