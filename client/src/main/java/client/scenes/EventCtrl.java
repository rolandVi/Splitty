package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import server.dto.view.EventDetailsDto;
import server.dto.view.ExpenseDetailsDto;

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
    public ListView<ExpenseDetailsDto> expenseList;
    @FXML
    public Button addExpenseButton;
    @FXML
    public Button addParticipant;
    @FXML
    public Text tempText;

    private EventDetailsDto event;

    private NewExpenseCtrl newExpenseCtrl;

    private final EventCtrl self = this;

    public Button leaveButton;
    @FXML
    private VBox participantsContainer;


    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     * @param serverUtils The Server Utilities
     * @param newExpenseCtrl The new expense page controller
     */
    @Inject
    public EventCtrl(MainCtrl mainCtrl, ServerUtils serverUtils, NewExpenseCtrl newExpenseCtrl) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.newExpenseCtrl = newExpenseCtrl;
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
        event = serverUtils.getEventDetails(id);

        eventNameLabel.setText(event.getTitle());
        loadExpenseList();
    }

    /**
     * Load the list of expenses
     */
    public void loadExpenseList(){
//        Set<UserNameDto> debtors = new HashSet<>();
//        UserNameDto user2 = new UserNameDto(2L, "debtor1", "Surname1");
//        UserNameDto user3 = new UserNameDto(3L, "debtor2", "Surname2");
//        UserNameDto user1 = new UserNameDto(1L, "Tymon", "Slepowronski");
//        Set<UserNameDto> participants = new HashSet<>();
//        participants.add(user1);
//        participants.add(user2);
//        participants.add(user3);
//        debtors.add(user2);
//        debtors.add(user3);
//        ExpenseDetailsDto expenseDetailsDto1 = new ExpenseDetailsDto(1L, 12.50, user1,
//        "Test expense 1", debtors, new Date(2024, 03, 19) );
//        ExpenseDetailsDto expenseDetailsDto2 = new ExpenseDetailsDto(1L, 12.50, user1,
//        "Test expense 2", debtors, new Date(2024, 03, 19) );
//        Set<ExpenseDetailsDto> expenses = new HashSet<>();
//        expenses.add(expenseDetailsDto1);
//        expenses.add(expenseDetailsDto2);
//        event = new EventDetailsDto(1L, "ABC01", "Test Event 1", expenses, participants);
        ObservableList<ExpenseDetailsDto> items = FXCollections
                .observableArrayList(event.getExpenses());
        expenseList.setCellFactory(new Callback<ListView<ExpenseDetailsDto>,
                ListCell<ExpenseDetailsDto>>() {
            @Override
            public ListCell<ExpenseDetailsDto> call(ListView<ExpenseDetailsDto> param) {
                return new ExpenseListCell(self);
            }
        });
        expenseList.setItems(items);
        //set amount of rows visible
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
        newExpenseCtrl.init(event);
        mainCtrl.showNewExpense();
    }

    /**
     * Getter for the eventDetailsDto
     * @return the Data transfer object of current event showing
     */
    public EventDetailsDto getEventDetailsDto() {
        return event;
    }

    /**
     * Setter for the eventDetailsDto
     * @param eventDetailsDto the Data transfer object of current event showing
     */
    public void setEventDetailsDto(EventDetailsDto eventDetailsDto) {
        this.event = eventDetailsDto;
    }

    /**
     * shows the newParticipant scene
     */
    public void newParticipant(){
        mainCtrl.showNewParticipant();
    }


    private static class ExpenseListCell extends ListCell<ExpenseDetailsDto>{
        private final Button button;

        public ExpenseListCell(EventCtrl ctrl){
            button = new Button("Edit");

            button.setOnAction(event -> {
                ExpenseDetailsDto item = getItem();
                if (item!=null){
                    ctrl.tempText.setText("You pressed: " + item);
                }
            });

            HBox hBox = new HBox();
            //HBox.setHgrow(text, Priority.ALWAYS);
            //HBox.setMargin(button, new javafx.geometry.Insets(0, 0, 0, 1));

            hBox.getChildren().add(new Text());
            //System.out.println(getItem());
//            if (getItem()!=null){
//                hBox.getChildren().add(button);
//            }
            //hBox.getChildren().add(button);
            hBox.setSpacing(10);

            setGraphic(hBox);
        }

        @Override
        protected void updateItem(ExpenseDetailsDto item, boolean empty){
            super.updateItem(item, empty);
            if (empty || item==null){
                setText(null);
            }else {
                ((Text) ((HBox) getGraphic()).getChildren().get(0)).setText(item.getTitle());
                // I'm using the if statement, due to a weird error
                if (((HBox) getGraphic()).getChildren().size()<2){
                    ((HBox) getGraphic()).getChildren().add(button);
                }
            }
        }
    }

    /**
     * Loads the participants and displays them on the page
     */
    public void loadParticipants() {
        long eventId= this.event.getId();

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

    /**
     * The current user leaves the event
     */
    public void leave(){
        long userId = 1L; // TODO replace with the actual user id
        serverUtils.deleteEventParticipant(this.event.getId(), userId);
        returnToOverview();
    }

}
