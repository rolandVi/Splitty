package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import dto.view.ParticipantNameDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ParticipantCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    private long participantId;
    private long eventId;
    @FXML
    private Button deleteButton;
    @FXML
    public Button returnButton;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

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
        ParticipantNameDto participant = serverUtils.getParticipantDetails(parID);

        firstNameLabel.setText(participant.getFirstName());
        lastNameLabel.setText(participant.getLastName());
    }

    /**
     * Deletes a participant from the event
     */
    public void deleteParticipant(){
        serverUtils.deleteEventParticipant(eventId, participantId);
    }

}
