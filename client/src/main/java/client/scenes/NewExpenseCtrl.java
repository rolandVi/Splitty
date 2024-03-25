package client.scenes;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import javafx.scene.text.Text;
import javafx.util.Callback;

import server.dto.ExpenseCreationDto;
import server.dto.view.EventDetailsDto;
import server.dto.view.UserNameDto;

import java.util.Date;
import java.util.HashSet;

import java.util.Set;


public class NewExpenseCtrl {
    private final MainCtrl mainCtrl;

    private final ServerUtils serverUtils;

    private EventDetailsDto parentEvent;

    @FXML
    public TextField titleField;
    @FXML
    public TextField amountField;
    @FXML
    public ComboBox<UserNameDto> authorBox;
    @FXML
    public ListView<UserNameDto> debtorsCheckList;
    @FXML
    public Button returnButton;

    Set<Long> debtors;

    /**
     * Constructor injection
     * @param mainCtrl - the main controller
     * @param serverUtils - the server utilities
     */
    @Inject
    public NewExpenseCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.parentEvent = null;
    }

    /**
     * sets the id of the parent event
     * @param parentEvent - parent event
     */
    public void setParentEvent(EventDetailsDto parentEvent){

    }

    /**
     * initializes the parent event of the expense
     * and initializes the author choice box
     * @param event - the parent event
     */
    public void init(EventDetailsDto event){
        this.parentEvent = event;
        ObservableList<UserNameDto> participants = FXCollections.
                observableArrayList(parentEvent.getParticipants());

        authorBox.setCellFactory(new Callback<ListView<UserNameDto>, ListCell<UserNameDto>>() {
            @Override
            public ListCell<UserNameDto> call(ListView<UserNameDto> param) {
                return new ParticipantListCell();
            }
        });
        authorBox.setItems(participants);

        debtors = new HashSet<>();
        debtorsCheckList.setCellFactory(new Callback<ListView<UserNameDto>,
                ListCell<UserNameDto>>() {
            @Override
            public ListCell<UserNameDto> call(ListView<UserNameDto> param) {
                return new DebtorsListCell(debtors);
            }
        });
        debtorsCheckList.setItems(participants);
    }

    /**
     * Returns to the details of the parent event
     */
    public void returnToEvent() {
        mainCtrl.showEventDetails(parentEvent.getId());
    }

    /**
     * creates new expense based on the input
     */
    public void  createExpense(){
        String title = titleField.getText();
        double amount = Double.parseDouble(amountField.getText());
        UserNameDto author = authorBox.getValue();
        for (int i=0; i<debtorsCheckList.getItems().size(); i++) {
            if (debtorsCheckList.getSelectionModel().isSelected(i)) {
                debtors.add(debtorsCheckList.getItems().get(i).getId());
            }
        }

        serverUtils.addExpense(parentEvent.getId(),
                new ExpenseCreationDto(title, amount, author.getId(),
                        debtors, parentEvent.getId(), new Date()));
        mainCtrl.showEventDetails(parentEvent.getId());

    }
    private static class ParticipantListCell extends ListCell<UserNameDto>{
        public ParticipantListCell(){
            HBox hBox = new HBox();
            hBox.getChildren().add(new Text());
            setGraphic(hBox);
        }

        @Override
        protected void updateItem(UserNameDto item, boolean empty){
            super.updateItem(item, empty);
            if (empty || item==null) {
                setText(null);
            }else {
                ((Text) ((HBox) getGraphic()).getChildren().get(0))
                        .setText(item.getFirstName() + " " + item.getLastName());
            }
        }
    }

    private static class DebtorsListCell extends ListCell<UserNameDto>{
        Set<Long> debtors;
        public DebtorsListCell(Set<Long> debtors){
            this.debtors = debtors;
            HBox hBox = new HBox();
            setGraphic(hBox);
        }
        @Override
        protected void updateItem(UserNameDto item, boolean empty){
            super.updateItem(item, empty);
            if (empty || item==null) {
                setText(null);
            }else {
                CheckBox checkBox = new CheckBox(item.getFirstName() + " " + item.getLastName());

                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue){
                        debtors.add(item.getId());
                    }else {
                        debtors.remove(item);
                    }
                });

                ((HBox) getGraphic()).getChildren()
                        .add(checkBox);
            }
        }

    }
}
