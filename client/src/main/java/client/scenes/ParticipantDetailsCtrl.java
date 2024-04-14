package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.ExpenseEntity;
import dto.view.BankAccountDto;
import dto.view.ParticipantNameDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ParticipantDetailsCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    private Long participantId;
    private Long eventId;

    @FXML
    public Label titleLabel;
    @FXML
    public Text nameText;
    @FXML
    public Text emailText;
    @FXML
    public Text ibanText;
    @FXML
    public Text bicText;
    @FXML
    public Label paidTotalLabel;
    @FXML
    public Label paidTotalAmountLabel;
    @FXML
    public Label owesIsOwedLabel;
    @FXML
    public Label owesIsOwedAmountLabel;
    @FXML
    public Button returnButton;
    @FXML
    public Button editButton;

    /**
     * Injector for ParticipantDetailsCtrl
     * @param mainCtrl The Main Controller
     * @param  serverUtils The ServerUtils
     */
    @Inject
    public ParticipantDetailsCtrl(MainCtrl mainCtrl, ServerUtils serverUtils){
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }

    /**
     * Updates all the information on the page, according to given participant id and event id
     * @param parId ID of the participant
     * @param eventId ID of the event
     */
    public void init(Long parId, Long eventId) {
        this.participantId = parId;
        this.eventId = eventId;

        ParticipantNameDto participant = serverUtils.getParticipantDetails(parId, eventId);
        nameText.setText(participant.getFirstName() + " " + participant.getLastName());
        if (participant.getEmail() != null && !participant.getEmail().equals("")){
            emailText.setText(participant.getEmail());
        }else {
            emailText.setText("Not set...");
        }


        String url = mainCtrl.configManager.getProperty("serverURL");
        BankAccountDto bank = serverUtils.findBankDetails(parId, url);
        if (bank.getIban() != null){
            ibanText.setText(bank.getIban());
        }else {
            ibanText.setText("Not set...");
        }

        if (bank.getBic() != null){
            bicText.setText(bank.getBic());
        }else {
            bicText.setText("Not set...");
        }

        paidTotalLabel.setText(participant.getFirstName() + " has paid:");
        paidTotalAmountLabel.setText(String.valueOf(getSharePaidByParticipant()));

    }

    private double getSharePaidByParticipant(){
        List<ExpenseEntity> expenses = serverUtils.getAllExpensesOfEvent(eventId);
        double share = 0.0;
        for (ExpenseEntity e : expenses){
            if (e.getAuthor().getId().equals(this.participantId)){
                share += e.getMoney();
            }
        }
        return share;
    }

    /**
     * Returns to the overview of the event
     */
    public void returnToOverview(){
        mainCtrl.showEventDetails(eventId);
    }

    /**
     * Opens the edition page for the participant
     */
    public void edit(){
        mainCtrl.showParticipantEdit(participantId, eventId);
    }

}
