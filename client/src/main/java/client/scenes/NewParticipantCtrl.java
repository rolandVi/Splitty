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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.http.HttpResponse;
import java.util.Optional;

public class NewParticipantCtrl implements MultiLanguages{
    private final MainCtrl mainCtrl;

    @FXML
    public Label headLabel;
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
    @FXML
    public Label errorMessage;

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
     * Sets the languages
     */
    @Override
    public void updateLanguage() {
        headLabel.setText(mainCtrl.lang.getString("add_participant"));
        firstNameField.setPromptText(mainCtrl.lang.getString("first_name_placeholder"));
        surNameField.setPromptText(mainCtrl.lang.getString("last_name_placeholder"));
        emailField.setPromptText(mainCtrl.lang.getString("email_placeholder"));
        ibanField.setPromptText(mainCtrl.lang.getString("iban_placeholder"));
        bicField.setPromptText(mainCtrl.lang.getString("bic_placeholder"));
        errorMessage.setText(mainCtrl.lang.getString("participant_error"));
        addButton.setText(mainCtrl.lang.getString("add_participant"));
        goBackButton.setText(mainCtrl.lang.getString("return"));
    }

    /**
     * Creates a participant by sending an HTTP request.
     * After retrieving the response, create a bank account by also using an HTTP request.
     * Lastly show the event again.
     */
    public void addParticipant(){
        if (firstNameField.getText().isBlank() || surNameField.getText().isBlank()){
            this.errorMessage.setVisible(true);
            return;
        }

        HttpResponse<String> response = createUser().get();
        ObjectMapper o = new ObjectMapper();
        long participantId;
        try {
            ParticipantNameDto dto = o.readValue(response.body(), ParticipantNameDto.class);
            participantId = dto.getId();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (!ibanField.getText().isBlank() && !bicField.getText().isBlank()) {
            createBankAccount(participantId);
        }
        showEvent();
        clearTextFields();
    }

    /**
     * Clear all textFields
     */
    private void clearTextFields() {
        firstNameField.setText("");
        surNameField.setText("");
        emailField.setText("");
        ibanField.setText("");
        bicField.setText("");
        errorMessage.setVisible(false);
    }

    /**
     * Will show event overview when the goBack button is pressed
     */
    public void returnToEvent(){
        mainCtrl.showEventDetails(mainCtrl.getEventID());
        clearTextFields();
    }

    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ESCAPE:
                returnToEvent();
                break;
            default:
                break;
        }
    }

    /**
     * Checks what keys have been pressed in the participant part
     * @param e the key
     */
    public void keyPressedParticipant(KeyEvent e){
        switch (e.getCode()) {
            case ENTER:
                addParticipant();
                break;
            default:
                break;
        }
        keyPressed(e);
    }

    /**
     * after new participant is added return to current event
     */
    public void showEvent(){
        mainCtrl.showEventDetails(mainCtrl.getEventID());
    }

    /**
     * Creates HTTP request to the server using the contents of text fields as user info
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createUser() {
        // Prepare user data from text fields
        ParticipantCreationDto user = getUserEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return serverUtils.createUser(requestBody, mainCtrl.getEventID());
    }

    /**
     * Retrieve the filled in data of the textFields and create a dto
     * @return the dto to be sent as the request body
     */
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

    /**
     * Create a bankAccount by sending an HTTP request to the server
     * @param userId the participant/user ID of which the bank account belongs to
     * @return HTTP response
     */
    public Response createBankAccount(long userId) {
        BankAccountCreationDto bank = getBankEntity();
        return serverUtils.createBankAccount(userId, bank);
    }
    /**
     * Retrieve the filled in data of the textFields and create a dto
     * @return the dto to be sent as the request body
     */
    public BankAccountCreationDto getBankEntity() {
        String iban = ibanField.getText();
        String bic = bicField.getText();
        return new BankAccountCreationDto()
                .setIban(iban)
                .setBic(bic);
    }

}
