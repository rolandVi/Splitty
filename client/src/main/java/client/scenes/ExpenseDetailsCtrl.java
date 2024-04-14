package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import dto.view.ExpenseDetailsDto;
import dto.view.ParticipantNameDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

public class ExpenseDetailsCtrl {

    private final MainCtrl mainCtrl;
    private final NewExpenseCtrl newExpenseCtrl;
    private final ServerUtils serverUtils;
    private Long expenseId;
    private Long eventId;

    @FXML
    public Label titleLabel;
    @FXML
    public Label authorLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Button returnButton;
    @FXML
    public ListView<ParticipantNameDto> debtorsList;
    @FXML
    public Label costLabel;
    @FXML
    public Label expenseTitleLabel;
    @FXML
    public Text tagText;
    @FXML
    public Rectangle tagBackground;

    /**
     * Constructor injection
     * @param mainCtrl    - the main controller
     * @param newExpenseCtrl The controller of the new expense page
     * @param serverUtils - the server utilities
     */
    @Inject
    public ExpenseDetailsCtrl(MainCtrl mainCtrl, NewExpenseCtrl newExpenseCtrl,
                              ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
        this.newExpenseCtrl = newExpenseCtrl;
    }

    /**
     * Updates all the information on the page, according to given participant id and event id
     * @param expenseId ID of the expense
     * @param eventId ID of the event
     */
    public void init(Long expenseId, Long eventId) {
        this.expenseId = expenseId;
        this.eventId = eventId;

        ExpenseDetailsDto expense = serverUtils.getExpenseDetailsById(expenseId);

        authorLabel.setText(expense.getAuthor().toString());
        titleLabel.setText("Expense details");
        expenseTitleLabel.setText(expense.getTitle());
        if (expense.getTag() != null){
            tagText.setText(expense.getTag().getTagType());
            tagText.setFill(Color.BLACK);
            tagBackground.setFill(Color.web(expense.getTag().getHexValue()));
            tagText.setOpacity(1);
            tagBackground.setOpacity(1);
        }else {
            tagText.setOpacity(0);
            tagBackground.setOpacity(0);
        }


        Date date = expense.getDate();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = formatter.format(date);
        dateLabel.setText(dateString);
        costLabel.setText("€" + String.valueOf(expense.getMoney()));

        ObservableList<ParticipantNameDto> participants = expense.getDebtors().stream()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        debtorsList.setItems(participants);

        debtorsList.setCellFactory(new Callback<ListView<ParticipantNameDto>,
                ListCell<ParticipantNameDto>>() {
            @Override
            public ListCell<ParticipantNameDto> call(ListView<ParticipantNameDto> param) {
                return new ExpenseDetailsCtrl.DebtorsListCell();
            }
        });
        debtorsList.setItems(participants);
    }

    /**
     * Returns to the overview of the event
     */
    public void returnToOverview(){
        mainCtrl.showEventDetails(eventId);
    }

    private static class DebtorsListCell extends ListCell<ParticipantNameDto> {
        private Text participantText = new Text();

        public DebtorsListCell() {}

        @Override
        protected void updateItem(ParticipantNameDto item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                participantText.setText(item.getFirstName() + " " + item.getLastName());
                setGraphic(participantText);
            }
        }
    }
}
