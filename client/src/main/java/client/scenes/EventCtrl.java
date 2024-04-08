package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import dto.view.EventDetailsDto;
import dto.view.ExpenseDetailsDto;
import dto.view.ParticipantNameDto;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class EventCtrl implements MultiLanguages{
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
    public ListView<ExpenseDetailsDto> expenseList;
    @FXML
    public Button addExpenseButton;
    @FXML
    public Button addParticipant;

    @FXML
    public Button inviteBtn;
    @FXML
    public Button statsBtn;

    private EventDetailsDto eventDetailsDto;

    private NewExpenseCtrl newExpenseCtrl;

    private final EventCtrl self = this;

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
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = mainCtrl.lang;
            returnButton.setText(lang.getString("return"));
            changeTextField.setPromptText(lang.getString("event_name"));
            changeButton.setText(lang.getString("change"));
            participantsLabel.setText(lang.getString("participants"));
            expensesLabel.setText(lang.getString("expenses"));
            addExpenseButton.setText(lang.getString("add_expense"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverview(){
        mainCtrl.showOverview();
    }


    /**
     * Updates the view information with the details of the event with the given id
     * @param id the id of the event
     */
    public void init(long id) {
        this.eventDetailsDto=serverUtils.getEventDetails(id);
        eventNameLabel.setText(eventDetailsDto.getTitle());
        inviteCode.setText(eventDetailsDto.getInviteCode());
        loadExpenseList();
        loadParticipants();
    }

    /**
     * Load the list of expenses
     */
    public void loadExpenseList(){
        ObservableList<ExpenseDetailsDto> items = FXCollections
                .observableArrayList(eventDetailsDto.getExpenses());
        expenseList.setCellFactory(new Callback<ListView<ExpenseDetailsDto>,
                ListCell<ExpenseDetailsDto>>() {
            @Override
            public ListCell<ExpenseDetailsDto> call(ListView<ExpenseDetailsDto> param) {
                return new ExpenseListCell(self);
            }
        });
        expenseList.setItems(items);
    }


    /**
     * Will update the event name to the server and update the current event name
     * @throws JsonProcessingException when the objectMapper cannot properly
     * turn the EventTitleDto into Json format string
     */
    public void changeEventName() throws JsonProcessingException {
        serverUtils.changeEventName(eventDetailsDto.getId(), changeTextField.getText());
        this.eventNameLabel.setText(this.changeTextField.getText());
        this.changeTextField.setText("");
    }

    /**
     * Will show add expense scene, allowing the user to add an expense
     */
    public void addExpense(){
        newExpenseCtrl.init(eventDetailsDto);
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


    private static class ExpenseListCell extends ListCell<ExpenseDetailsDto> {
        private final Button editButton;
        private final Text expenseInfo;
        private final StackPane tagPane;
        private final Rectangle tagBackground;
        private final Text tagText;
        private final HBox hbox;

        public ExpenseListCell(EventCtrl ctrl) {
            editButton = new Button("Edit");
            editButton.setOnAction(event -> {
                ExpenseDetailsDto item = getItem();
                if (item != null) {
                    ctrl.newExpenseCtrl.initEdit(ctrl.eventDetailsDto, item);
                    ctrl.mainCtrl.showEditExpense();
                }
            });

            expenseInfo = new Text();

            tagText = new Text();
            tagBackground = new Rectangle();
            tagBackground.setFill(Color.LIGHTGRAY); // Default background color for the tag
            tagBackground.setArcWidth(10); // Set arc width for rounded corners
            tagBackground.setArcHeight(10);
            tagPane = new StackPane(tagBackground, tagText);
            tagPane.setAlignment(Pos.CENTER); // Center the text within the stack pane

            hbox = new HBox(expenseInfo, tagPane, editButton);
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER_LEFT);

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(hbox);
        }

        @Override
        protected void updateItem(ExpenseDetailsDto item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                expenseInfo.setText(item.getTitle() + "\n"
                        + item.getAuthor().toString() + " paid: " + item.getMoney() + " euro");

                tagText.setText(item.getTag().getTagType());
                tagText.setFill(Color.BLACK);
                tagBackground.setWidth(tagText.getBoundsInLocal().getWidth() + 10);
                tagBackground.setHeight(tagText.getBoundsInLocal().getHeight() + 5);
                tagBackground.setFill(Color.web(item.getTag().getHexValue()));

                setGraphic(hbox);
            }
        }
    }

    /**
     * Loads the participants and displays them on the page
     */
    public void loadParticipants() {
        long eventId= this.eventDetailsDto.getId();

        List<ParticipantNameDto> participants = this.serverUtils.getParticipantsByEvent(eventId);
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
            final ParticipantNameDto participant=participants.get(i);

            Button eventButton = (Button) currentNode.lookup("#participantName");
            eventButton.setText(participants.get(i).getFirstName() + " "
                    + participants.get(i).getLastName());

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
     * Copies the invite code to the clipboard
     */
    public void copyInvite(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(this.inviteCode.getText());
        clipboard.setContent(content);
    }

    /**
     * Brings user to the statistics page
     */
    public void showStats(){
        mainCtrl.showStats();
    }

}
