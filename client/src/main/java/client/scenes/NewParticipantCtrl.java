package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import dto.BankAccountCreationDto;
import dto.ParticipantCreationDto;
import dto.view.ParticipantNameDto;
import jakarta.ws.rs.core.Response;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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

    public void addParticipant(){
        HttpResponse<String> response = createUser().get();
        ObjectMapper o = new ObjectMapper();
        long participantId;
        try {
            ParticipantNameDto dto = o.readValue(response.body(), ParticipantNameDto.class);
            participantId = dto.getId();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        createBankAccount(participantId);
        showEvent();
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
        mainCtrl.showEventDetails(mainCtrl.getEventID());
    }
//TODO : We really need to review this class
    /**
     * Creates HTTP request to the server using the contents of text fields as user info
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createUser() {
        String url = mainCtrl.configManager.getProperty("serverURL");
        // Prepare user data from text fields
        ParticipantCreationDto user = getUserEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return serverUtils.createUser(url, requestBody, mainCtrl.getEventID());
    }

    private ParticipantCreationDto getUserEntity() {
        String firstName = firstNameField.getText();
        String surName = surNameField.getText();
        String email = emailField.getText();

        // Create a UserEntity object

        return new ParticipantCreationDto()
                .setFirstName(firstName)
                .setLastName(surName)
                .setEmail(email);
    }

    public Response createBankAccount(long userId) {
        String url = mainCtrl.configManager.getProperty("serverURL");
        // Prepare user data from text fields
        BankAccountCreationDto bank = getBankEntity();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBody = null;
//        try {
//            requestBody = objectMapper.writeValueAsString(bank);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

        return serverUtils.createBankAccount(userId, bank, url);
    }

    public BankAccountCreationDto getBankEntity() {
        String iban = ibanField.getText();
        if (iban.isEmpty()) iban = "NA";
        String bic = bicField.getText();
        if (bic.isEmpty()) bic = "NA";
        return new BankAccountCreationDto()
                .setIban(iban)
                .setBic(bic);
    }

}
