package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import dto.ExpenseCreationDto;
import dto.view.EventDetailsDto;
import dto.view.ExpenseDetailsDto;
import dto.view.ParticipantNameDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
    public ComboBox<ParticipantNameDto> authorBox;
    @FXML
    public ListView<ParticipantNameDto> debtorsCheckList;
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

    private Set<ParticipantNameDto> debtors;

    private ExpenseDetailsDto expense;

    /**
     * Constructor injection
     *
     * @param mainCtrl    - the main controller
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
     *
     * @param parentEvent - parent event
     */
    public void setParentEvent(EventDetailsDto parentEvent) {

    }

    /**
     * Getter for the expense details
     * @return the expense details
     */
    public ExpenseDetailsDto getExpenseDetails() {
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

        ObservableList<ParticipantNameDto> participants = FXCollections.
                observableArrayList(parentEvent.getParticipants());

        authorBox.setCellFactory(new Callback<ListView<ParticipantNameDto>,
                ListCell<ParticipantNameDto>>() {
            @Override
            public ListCell<ParticipantNameDto> call(ListView<ParticipantNameDto> param) {
                return new ParticipantListCell();
            }
        });
        authorBox.setItems(participants);

        debtors = new HashSet<>();
        debtorsCheckList.setCellFactory(new Callback<ListView<ParticipantNameDto>,
                ListCell<ParticipantNameDto>>() {
            @Override
            public ListCell<ParticipantNameDto> call(ListView<ParticipantNameDto> param) {
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

        ObservableList<ParticipantNameDto> participants = FXCollections.
                observableArrayList(parentEvent.getParticipants());

        authorBox.setCellFactory(new Callback<ListView<ParticipantNameDto>,
                ListCell<ParticipantNameDto>>() {
            @Override
            public ListCell<ParticipantNameDto> call(ListView<ParticipantNameDto> param) {
                return new ParticipantListCell();
            }
        });
        authorBox.setItems(participants);

        debtors = new HashSet<>();
        debtorsCheckList.setCellFactory(new Callback<ListView<ParticipantNameDto>,
                ListCell<ParticipantNameDto>>() {
            @Override
            public ListCell<ParticipantNameDto> call(ListView<ParticipantNameDto> param) {
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
            ParticipantNameDto author = authorBox.getValue();
            for (int i=0; i<debtorsCheckList.getItems().size(); i++) {
                if (debtorsCheckList.getSelectionModel().isSelected(i)) {
                    debtors.add(debtorsCheckList.getItems().get(i));
                }
            }

            serverUtils.send("/app/expenses/create", new ExpenseCreationDto(title, amount,
                    author.getId(), debtors, parentEvent.getId(), new Date()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(mainCtrl.lang.getString("add_expense_alert_header"));
            alert.setContentText(mainCtrl.lang.getString("add_expense_alert_content") +
                    " " + titleField.getText());
            alert.showAndWait().ifPresent(response -> {
                mainCtrl.showEventDetails(parentEvent.getId());
            });
        }catch (NumberFormatException e) {
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
            for (int i=0; i<debtorsCheckList.getItems().size(); i++){
                if (debtorsCheckList.getSelectionModel().isSelected(i)) {
                    debtors.add(debtorsCheckList.getItems().get(i));
                }
            }
            expense.setDebtors(debtors);
            expense.setMoney(Double.parseDouble(amountField.getText()));
            expense.setTitle(titleField.getText());

            serverUtils.editExpense(expense);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(mainCtrl.lang.getString("edit_expense_alert_header"));
            alert.setContentText(mainCtrl.lang.getString("edit_expense_alert_content") +
                    " " + titleField.getText());
            alert.showAndWait().ifPresent(response -> {
                mainCtrl.showEventDetails(parentEvent.getId());
            });
        } catch (NumberFormatException e) {
            errorField.setText("Enter a valid amount");
            errorField.setOpacity(1);
        }

    }
    /**
     * Control for the remove expense button
     */
    public void remove(){
        serverUtils.removeExpense(parentEvent.getId(), expense.getId());
        mainCtrl.showEventDetails(parentEvent.getId());
    }

    /**
     * Adds an enter shortcut if you click enter
     *
     * @param e the key pressed
     */
    public void keyPressedCreate(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                createExpense();
                break;
            case ESCAPE:
                returnToEvent();
                break;
            default:
                break;
        }
    }

    /**
     * Adds an enter shortcut if you click enter
     *
     * @param e the key pressed
     */
    public void keyPressedEdit(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                createExpense();
                break;
            default:
                break;
        }
    }

    private static class ParticipantListCell extends ListCell<ParticipantNameDto> {
        public ParticipantListCell() {
            HBox hBox = new HBox();
            hBox.getChildren().add(new Text());
            setGraphic(hBox);
        }

        @Override
        protected void updateItem(ParticipantNameDto item, boolean empty){
            super.updateItem(item, empty);
            if (empty || item==null) {
                setText(null);
            }else {
                ((Text) ((HBox) getGraphic()).getChildren().get(0))
                        .setText(item.getFirstName() + " " + item.getLastName());
            }
        }
    }

    private static class DebtorsListCell extends ListCell<ParticipantNameDto>{
        Set<ParticipantNameDto> debtors;
        List<CheckBox> debtorsCheckBoxes;
        public DebtorsListCell(Set<ParticipantNameDto> debtors, List<CheckBox> debtorsCheckBoxes){
            this.debtors = debtors;
            this.debtorsCheckBoxes = debtorsCheckBoxes;
            HBox hBox = new HBox();
            setGraphic(hBox);
        }
        @Override
        protected void updateItem(ParticipantNameDto item, boolean empty){
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
