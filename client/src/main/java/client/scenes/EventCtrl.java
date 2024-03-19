package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Callback;
import server.dto.view.EventDetailsDto;

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
    public ListView<String> expenseList;
    @FXML
    public Button addExpenseButton;
    @FXML
    public Button addParticipant;
    @FXML
    public Text tempText;

    private EventDetailsDto eventDetailsDto;

    private final EventCtrl self = this;

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
        var event=serverUtils.getEventDetails(id);
        eventNameLabel.setText(event.getTitle());
        loadExpenseList();
    }

    public void loadExpenseList(){
        ObservableList<String> items = FXCollections.observableArrayList("1", "2", "3", "4");
        expenseList.setCellFactory(new Callback<ListView<String>,
                ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
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

    private static class ExpenseListCell extends ListCell<String>{
        private final Button button;

        public ExpenseListCell(EventCtrl ctrl){
            button = new Button("Edit");

            button.setOnAction(event -> {
                String item = getItem();
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
        protected void updateItem(String item, boolean empty){
            super.updateItem(item, empty);
            if (empty || item==null){
                setText(null);
            }else {
                ((Text) ((HBox) getGraphic()).getChildren().get(0)).setText(item);
                // I'm using the if statement, due to a weird error
                if (((HBox) getGraphic()).getChildren().size()<2){
                    ((HBox) getGraphic()).getChildren().add(button);
                }
            }
        }
    }
}
