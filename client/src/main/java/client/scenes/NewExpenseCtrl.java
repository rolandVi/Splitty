package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TagEntity;
import dto.ExpenseCreationDto;
import dto.view.EventDetailsDto;
import dto.view.ExpenseDetailsDto;
import dto.view.ParticipantNameDto;
import dto.view.TagDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.*;


public class NewExpenseCtrl implements MultiLanguages{
    private final MainCtrl mainCtrl;

    private final ServerUtils serverUtils;

    private EventDetailsDto parentEvent;
    @FXML
    public ComboBox<TagEntity> tags;
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

    @FXML
    public ToggleButton splitEquallyButton;

    @FXML
    public Label tagLabel;
    @FXML
    public Label titleLabel;
    @FXML
    public Label amountLabel;
    @FXML
    public Label paidLabel;

    @FXML
    public Button newTag;

    private List<CheckBox> debtorsCheckBoxes;

    private Set<ParticipantNameDto> debtors;

    private ExpenseDetailsDto expense;

    private String titleState;
    private String amountState;
    private ParticipantNameDto authorState;
    private Set<ParticipantNameDto> debtorsState;
    private TagEntity tagState;

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
     *
     * @return the expense details
     */
    public ExpenseDetailsDto getExpenseDetails() {
        return expense;
    }

    /**
     * initializes the parent event of the expense
     * and initializes the author choice box
     *
     * @param event - the parent event
     */
    public void init(EventDetailsDto event) {
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


        tags.getItems().clear();
        // Fetch all tags from the server
        List<TagEntity> allTags = serverUtils.getAllTags();

        // Add fetched tags to the ComboBox
        tags.getItems().addAll(allTags);

        // Set the cell factory and converter for proper display of tags
        tags.setCellFactory(param -> new TagCell());
        tags.setConverter(new TagStringConverter());
    }

    /**
     * Initializes the edition page
     *
     * @param event   parent event
     * @param expense the details of expense to edit
     */
    public void initEdit(EventDetailsDto event, ExpenseDetailsDto expense) {
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
    public void splitEqually() {
        for (CheckBox c : debtorsCheckBoxes) {
            c.setSelected(true);
        }
    }


    /**
     * creates new expense based on the input
     */
    public void createExpense() {
        String title = titleField.getText();
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount<0){
                errorField.setOpacity(1);
            }
            ParticipantNameDto author = authorBox.getValue();
            for (int i = 0; i < debtorsCheckList.getItems().size(); i++) {
                if (debtorsCheckList.getSelectionModel().isSelected(i)) {
                    debtors.add(debtorsCheckList.getItems().get(i));
                }
            }

            TagEntity tag = tags.getValue();
            TagDto newTag = null;

            if (tag != null) {
                newTag = new TagDto(tag.getId(), tag.getTagType(), tag.getHexValue());
            }

            serverUtils.send("/app/expenses/create", new ExpenseCreationDto(title, amount,
                    author.getId(), debtors, parentEvent.getId(), new Date(), newTag));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(mainCtrl.lang.getString("add_expense_alert_header"));
            alert.setContentText(mainCtrl.lang.getString("add_expense_alert_content") +
                    " " + titleField.getText());
            alert.showAndWait().ifPresent(response -> {
                mainCtrl.showEventDetails(parentEvent.getId());
            });
        } catch (NumberFormatException e) {
            errorField.setOpacity(1);
        }
    }

    /**
     * Control for the edit button
     */
    public void editExpense() {
        try {
            expense.setAuthor(authorBox.getValue());
            for (int i = 0; i < debtorsCheckList.getItems().size(); i++) {
                if (debtorsCheckList.getSelectionModel().isSelected(i)) {
                    debtors.add(debtorsCheckList.getItems().get(i));
                }
            }
            expense.setDebtors(debtors);
            expense.setMoney(Double.parseDouble(amountField.getText()));
            expense.setTitle(titleField.getText());
            TagDto tagDto = new TagDto(tags.getValue().getId(), tags.getValue().getTagType(),
                    tags.getValue().getHexValue());
            expense.setTag(tagDto);

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
    public void remove() {
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

    /**
     * Brings the user to the customtag scene
     */
    public void showCustomTag() {
        Main.openCustomtag();
    }

    @Override
    public void updateLanguage() {
        this.returnButton.setText(mainCtrl.lang.getString("return"));
        this.splitEquallyButton.setText(mainCtrl.lang.getString("split"));
        this.editButton.setText(mainCtrl.lang.getString("edit"));
        this.addExpenseButton.setText(mainCtrl.lang.getString("create"));
        this.newTag.setText(mainCtrl.lang.getString("new_tag"));
        this.tagLabel.setText(mainCtrl.lang.getString("tag_label"));
        this.amountLabel.setText(mainCtrl.lang.getString("amount_label"));
        this.paidLabel.setText(mainCtrl.lang.getString("paid_label"));
        this.titleLabel.setText(mainCtrl.lang.getString("title_label"));
        this.errorField.setText(mainCtrl.lang.getString("error_amount"));
    }

    private static class ParticipantListCell extends ListCell<ParticipantNameDto> {
        public ParticipantListCell() {
            HBox hBox = new HBox();
            hBox.getChildren().add(new Text());
            setGraphic(hBox);
        }

        @Override
        protected void updateItem(ParticipantNameDto item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                ((Text) ((HBox) getGraphic()).getChildren().get(0))
                        .setText(item.getFirstName() + " " + item.getLastName());
            }
        }
    }

    private static class DebtorsListCell extends ListCell<ParticipantNameDto> {
        Set<ParticipantNameDto> debtors;
        List<CheckBox> debtorsCheckBoxes;

        public DebtorsListCell(Set<ParticipantNameDto> debtors, List<CheckBox> debtorsCheckBoxes) {
            this.debtors = debtors;
            this.debtorsCheckBoxes = debtorsCheckBoxes;
            HBox hBox = new HBox();
            setGraphic(hBox);
        }

        @Override
        protected void updateItem(ParticipantNameDto item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                CheckBox checkBox = new CheckBox(item.getFirstName() + " " + item.getLastName());
                debtorsCheckBoxes.add(checkBox);

                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        debtors.add(item);
                    } else {
                        debtors.remove(item);
                    }
                });
                if (((HBox) getGraphic()).getChildren().isEmpty()) {
                    ((HBox) getGraphic()).getChildren()
                            .add(checkBox);
                }
            }
        }

    }

    /**
     * Refreshes the tags in the ComboBox
     */
    public void refreshTags() {
        // Fetch all tags from the server
        List<TagEntity> allTags = serverUtils.getAllTags();

        // Clear the existing items and add fetched tags to the ComboBox
        tags.getItems().clear();
        tags.getItems().addAll(allTags);

        // Set the cell factory and converter for proper display of tags
        tags.setCellFactory(param -> new TagCell());
        tags.setConverter(new TagStringConverter());
    }

    // Define custom cell factory to display tag names in the ComboBox
    private static class TagCell extends ListCell<TagEntity> {
        @Override
        protected void updateItem(TagEntity item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.getTagType());
            }
        }
    }


    private static class TagStringConverter extends StringConverter<TagEntity> {
        @Override
        public String toString(TagEntity tag) {
            return tag == null ? null : tag.getTagType();
        }

        @Override
        public TagEntity fromString(String tagName) {
            return null;
        }
    }
}

