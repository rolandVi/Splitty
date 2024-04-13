package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
import dto.view.ParticipantNameDto;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

public class ParticipantCtrl implements MultiLanguages {

    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    private long participantId;
    private long eventId;
    @FXML
    private Button deleteButton;
    @FXML
    public Button returnButton;
    @FXML
    public Button updateParticipantButton;
    @FXML
    public Button updateBankButton;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label ibanLabel;
    @FXML
    private Label bicLabel;

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField ibanTextField;
    @FXML
    private TextField bicTextField;

    @FXML
    private Label participantError;
    @FXML
    private Label accountError;
    @FXML
    private Button bankDeleteBtn;


    /**
     * Injector for Participant Controller
     *
     * @param mainCtrl    The Main Controller
     * @param serverUtils The Server Utilities
     */
    @Inject
    public ParticipantCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }

    /**
     * Returns to the Event details scene
     */
    public void returnToEvent() {
        mainCtrl.showEventDetails(eventId);
    }

    /**
     * Updates the view information with the details of the participant with the given id
     * , saves event id
     *
     * @param parID   id of the participant
     * @param eventId id of the event
     */
    public void init(long parID, long eventId) {
        this.participantId = parID;
        this.eventId = eventId;
        updateLanguage();

        ParticipantNameDto participant = serverUtils.getParticipantDetails(parID, eventId);
        firstNameTextField.setText(participant.getFirstName());
        lastNameTextField.setText(participant.getLastName());
        emailTextField.setText(participant.getEmail());
        participantError.setVisible(false);

        String url = mainCtrl.configManager.getProperty("serverURL");
        BankAccountDto bank = serverUtils.findBankDetails(parID);
        ibanTextField.setText(bank.getIban());
        bicTextField.setText(bank.getBic());
        accountError.setVisible(false);
    }

    /**
     * If a key presses is pressed in the participant menu
     * @param e the pressed key
     */
    public void onKeyPressedParticipant(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                updateParticipant();
                break;
            default:
                break;
        }
        onKeyPressed(e);
    }

    /**
     * If a key is pressed in the account menu
     * @param e the pressed key
     */
    public void onKeyPressedAccount(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                updateBank();
                break;
            default:
                break;
        }
        onKeyPressed(e);
    }

    /**
     * If a key is pressed in the menu
     * @param e the pressed key
     */
    public void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ESCAPE:
                returnToEvent();
                break;
            default:
                break;
        }
    }

    /**
     * Deletes a participant from the event
     */
    public void deleteParticipant() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete participant?");
        alert.setContentText("Are you aure you want to delete this participant");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                serverUtils.deleteEventParticipant(eventId, participantId);
                mainCtrl.showEventDetails(eventId);
            }
        });
    }

    /**
     * Update the participant of the event using method in ServerUtils
     */
    public void updateParticipant() {
        if (firstNameTextField.getText().trim().isBlank() || lastNameTextField.getText().trim().isBlank()) {
            this.participantError.setVisible(true);
            return;
        }
        serverUtils.editParticipant(getParticipantEntity());
        mainCtrl.showEventDetails(eventId);
    }

    /**
     * Retrieve the filled in data of the textFields and create a dto
     *
     * @return the dto to be sent as the request body
     */
    public ParticipantNameDto getParticipantEntity() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        return new ParticipantNameDto(participantId, firstName, lastName, email);
    }

    /**
     * Update the bank info of the participant
     */
    public void updateBank() {
        if (ibanTextField.getText() == null || bicTextField.getText() == null
                || ibanTextField.getText().trim().isBlank()
                || bicTextField.getText().trim().isBlank()) {
            this.accountError.setVisible(true);
            return;
        }
        serverUtils.editBankAccount(participantId, getBankEntity());
        mainCtrl.showEventDetails(eventId);
    }

    /**
     * Deletes the bank account of teh current participant
     */
    public void deleteBankAccount() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete bank account?");
        alert.setContentText("Are you aure you want to delete this bank account");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    this.serverUtils.deleteBankAccountOf(participantId);
                    returnToEvent();
                } catch (IOException | URISyntaxException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Retrieve the filled in data of the textFields and create a dto
     *
     * @return the dto to be sent as the request body
     */
    private BankAccountCreationDto getBankEntity() {
        String iban = ibanTextField.getText();
        String bic = bicTextField.getText();
        BankAccountCreationDto result = new BankAccountCreationDto();
        result.setIban(iban);
        result.setBic(bic);
        return result;
    }

    /**
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = mainCtrl.lang;
            returnButton.setText(lang.getString("return"));
            deleteButton.setText(lang.getString("delete"));
            firstNameLabel.setText(lang.getString("first_name"));
            lastNameLabel.setText(lang.getString("last_name"));
            emailLabel.setText(lang.getString("email"));
//            ibanLabel.setText(lang.getString(""));
//            bicLabel.setText(lang.getString(""));
            participantError.setText(lang.getString("participant_update_error"));
            accountError.setText(lang.getString("account_update_error"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
