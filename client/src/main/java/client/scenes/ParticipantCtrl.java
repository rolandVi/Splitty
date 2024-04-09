package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
import dto.view.ParticipantNameDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class ParticipantCtrl implements MultiLanguages{

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


    /**
     * Injector for Participant Controller
     * @param mainCtrl The Main Controller
     * @param serverUtils The Server Utilities
     */
    @Inject
    public ParticipantCtrl(MainCtrl mainCtrl, ServerUtils serverUtils){
        this.mainCtrl = mainCtrl;
        this.serverUtils=serverUtils;
    }

    /**
     * Returns to the Event details scene
     */
    public void returnToOverview(){
        mainCtrl.showEventDetails(eventId);
    }

    /**
     * Updates the view information with the details of the participant with the given id
     * , saves event id
     * @param parID id of the participant
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

        String url = mainCtrl.configManager.getProperty("serverURL");
        BankAccountDto bank = serverUtils.findBankDetails(parID, url);
        ibanTextField.setText(bank.getIban());
        bicTextField.setText(bank.getBic());
    }

    /**
     * Deletes a participant from the event
     */
    public void deleteParticipant(){
        serverUtils.deleteEventParticipant(eventId, participantId);
        mainCtrl.showEventDetails(eventId);
    }

    /**
     * Update the participant of the event using method in ServerUtils
     */
    public void updateParticipant() {
        String url = mainCtrl.configManager.getProperty("serverURL");
        serverUtils.editParticipant(getParticipantEntity(), url);
        mainCtrl.showEventDetails(eventId);
    }

    /**
     * Retrieve the filled in data of the textFields and create a dto
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
        String url = mainCtrl.configManager.getProperty("serverURL");
        serverUtils.editBankAccount(participantId, getBankEntity(), url);
        mainCtrl.showEventDetails(eventId);
    }

    /**
     * Retrieve the filled in data of the textFields and create a dto
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
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
