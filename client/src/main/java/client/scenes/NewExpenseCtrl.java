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
import server.dto.view.ExpenseDetailsDto;
import server.dto.view.UserNameDto;

import java.util.*;


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
    @FXML
    public Button removeButton;
    @FXML
    public Button editButton;
    @FXML
    public Button addExpenseButton;
    @FXML
    public Text errorField;

    private List<CheckBox> debtorsCheckBoxes;

    private Set<UserNameDto> debtors;

    private ExpenseDetailsDto expense;

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
     * Getter for the expense details
     * @return the expense details
     */
    public ExpenseDetailsDto getExpenseDetails(){
        return expense;
    }

    /**
     * initializes the parent event of the expense
     * and initializes the author choice box
     * @param event - the parent event
     */
    public void init(EventDetailsDto event){
        this.parentEvent = event;
        debtorsCheckBoxes = new ArrayList<>();
        this.expense = null;

        removeButton.setVisible(false);
        editButton.setVisible(false);
        addExpenseButton.setVisible(true);


        titleField.clear();
        amountField.clear();
        errorField.setOpacity(0);

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
                return new DebtorsListCell(debtors, debtorsCheckBoxes);
            }
        });
        debtorsCheckList.setItems(participants);
    }

    /**
     * Initializes the edition page
     * @param event parent event
     * @param expense the details of expense to edit
     */
    public void initEdit(EventDetailsDto event, ExpenseDetailsDto expense){
        this.parentEvent = event;
        debtorsCheckBoxes = new ArrayList<>();
        this.expense = expense;

        removeButton.setVisible(true);
        editButton.setVisible(true);
        addExpenseButton.setVisible(false);

        titleField.setText(expense.getTitle());
        amountField.setText(expense.getMoney().toString());
        errorField.setOpacity(0);

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
                return new DebtorsListCell(debtors, debtorsCheckBoxes);
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
     * Checks all participants as debtors
     */
    public void splitEqually(){
        for (CheckBox c : debtorsCheckBoxes) {
            c.setSelected(true);
        }
    }


    /**
     * creates new expense based on the input
     */
    public void  createExpense(){
        String title = titleField.getText();
        try {
            double amount = Double.parseDouble(amountField.getText());
            UserNameDto author = authorBox.getValue();
            for (int i=0; i<debtorsCheckList.getItems().size(); i++) {
                if (debtorsCheckList.getSelectionModel().isSelected(i)) {
                    debtors.add(debtorsCheckList.getItems().get(i));
                }
            }

            serverUtils.addExpense(parentEvent.getId(),
                    new ExpenseCreationDto(title, amount, author.getId(),
                            debtors, parentEvent.getId(), new Date()));
            mainCtrl.showEventDetails(parentEvent.getId());
        }catch (NumberFormatException e){
            errorField.setText("Enter a valid amount");
            errorField.setOpacity(1);
        }
    }

    /**
     * Control for the edit button
     */
    public void editExpense(){
        try {
            expense.setAuthor(authorBox.getValue());
            //expense.setDate();
            for (int i=0; i<debtorsCheckList.getItems().size(); i++){
                if (debtorsCheckList.getSelectionModel().isSelected(i)) {
                    debtors.add(debtorsCheckList.getItems().get(i));
                }
            }
            expense.setDebtors(debtors);
            expense.setMoney(Double.parseDouble(amountField.getText()));
            expense.setTitle(titleField.getText());

            serverUtils.editExpense(expense);
            mainCtrl.showEventDetails(parentEvent.getId());
        }catch (NumberFormatException e){
            errorField.setText("Enter a valid amount");
            errorField.setOpacity(1);
        }

    }
    /**
     * Control for the remove expense button
     */
    public void remove(){
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
        Set<UserNameDto> debtors;
        List<CheckBox> debtorsCheckBoxes;
        public DebtorsListCell(Set<UserNameDto> debtors, List<CheckBox> debtorsCheckBoxes){
            this.debtors = debtors;
            this.debtorsCheckBoxes = debtorsCheckBoxes;
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
                debtorsCheckBoxes.add(checkBox);

                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue){
                        debtors.add(item);
                    }else {
                        debtors.remove(item);
                    }
                });
                if (((HBox) getGraphic()).getChildren().isEmpty()){
                    ((HBox) getGraphic()).getChildren()
                            .add(checkBox);
                }
            }
        }

    }
}
