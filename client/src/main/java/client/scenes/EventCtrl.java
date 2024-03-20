package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import server.dto.view.EventDetailsDto;
import server.dto.view.UserNameDto;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class EventCtrl {
    private final MainCtrl mainCtrl;

    private final ServerUtils serverUtils;
    @FXML
    public Label eventNameLabel;
    @FXML
    public Button returnButton;
    @FXML
    public TextField changeTextField;
    @FXML
    public Button changeButton;
    @FXML
    public Label participantsLabel;
    @FXML
    public Label inviteCode;
    @FXML
    public Label expensesLabel;
    @FXML
    public Button addExpenseButton;
    @FXML
    public Button addParticipant;
    @FXML
    public Button leaveButton;
    @FXML
    private VBox participantsContainer;
    private EventDetailsDto eventDetailsDto;
    private long eventId;

    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     * @param serverUtils The Server Utilities
     */
    @Inject
    public EventCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverview(){
        mainCtrl.showOverview();
    }


    /**
     * Updates teh view information with the details of the event with the given id
     * @param id the id of the event
     */
    public void init(long id) {
        this.eventId = id;
        var event=serverUtils.getEventDetails(id);
        eventNameLabel.setText(event.getTitle());
    }

    /**
     * Will update the event name to the server and update the current event name
     * @throws JsonProcessingException when the objectMapper cannot properly
     * turn the EventTitleDto into Json format string
     */
    public void changeEventName() throws JsonProcessingException {
        serverUtils.changeEventName(1L, changeTextField.getText());
        this.eventNameLabel.setText(this.changeTextField.getText());
        this.changeTextField.setText("");
    }

    /**
     * Will show add expense scene, allowing the user to add an expense
     */
    public void addExpense(){
        mainCtrl.showNewExpense();
    }

    /**
     * Getter for the eventDetailsDto
     * @return the Data transfer object of current event showing
     */
    public EventDetailsDto getEventDetailsDto() {
        return eventDetailsDto;
    }

    /**
     * Setter for the eventDetailsDto
     * @param eventDetailsDto the Data transfer object of current event showing
     */
    public void setEventDetailsDto(EventDetailsDto eventDetailsDto) {
        this.eventDetailsDto = eventDetailsDto;
    }

    /**
     * shows the newParticipant scene
     */
    public void newParticipant(){
        mainCtrl.showNewParticipant();
    }

    /**
     * Loads the participants and displays them on the page
     */
    public void loadParticipants() {
        long eventId= this.eventDetailsDto.getId();

        List<UserNameDto> participants = this.serverUtils.getParticipantsByEvent(eventId);
        Node[] nodes=new Node[participants.size()];


        for (int i = 0; i < nodes.length; i++) {
            var loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader()
                    .getResource(Path.of("client.scenes", "participantItem.fxml").toString()));
            try {
                nodes[i]=loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Node currentNode=nodes[i];
            final UserNameDto participant=participants.get(i);

            Button eventButton = (Button) currentNode.lookup("#participantName");
            eventButton.setText(participants.get(i).getFirstName() + " "
                    + participants.get(i).getFirstName());

            eventButton.setOnAction(e -> showParticipantEdit(participant.getId(), eventId));
        }
        this.participantsContainer.getChildren().clear();
        this.participantsContainer.getChildren().addAll(nodes);
    }

    /**
     * Show the page for editing a participant
     * @param parID id of the participant
     * @param eventId id of the event (for deletion of participant)
     */
    private void showParticipantEdit(long parID, long eventId) {
        mainCtrl.showParticipantEdit(parID, eventId);
    }

    private void leave(){
        long userId = 1L; // TODO replace with the actual user id
        serverUtils.deleteEventParticipant(this.eventId, userId);
        returnToOverview();
    }

}
