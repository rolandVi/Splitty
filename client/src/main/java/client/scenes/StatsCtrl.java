package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.ExpenseEntity;
import commons.TagEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatsCtrl implements MultiLanguages{

    private final MainCtrl mainCtrl;

    private final ServerUtils serverUtils;
    private final EventCtrl eventCtrl;

    @FXML
    public PieChart pieChart;
    @FXML
    public Text totalSum;
    @FXML
    public Button backBtn;
    @FXML
    public Label moneyLabel;
    @FXML
    public Label pieLabel;


    /**
     * Injected constructor
     *
     * @param mainCtrl    mainctrl
     * @param serverUtils serverutils
     * @param eventCtrl   eventctrl
     */
    @Inject
    public StatsCtrl(MainCtrl mainCtrl, ServerUtils serverUtils, EventCtrl eventCtrl) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;

        this.eventCtrl = eventCtrl;
    }


    /**
     * Updates the language of the page
     */
    @Override
    public void updateLanguage() {
        moneyLabel.setText(mainCtrl.lang.getString("money_label"));
        pieLabel.setText(mainCtrl.lang.getString("pie_label"));
        backBtn.setText(mainCtrl.lang.getString("return"));
    }

    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ESCAPE:
                retToEvent();
                break;
            default:
                break;
        }
    }

    /**
     * returns to overview
     */
    public void retToEvent(){
        mainCtrl.showEventDetails(mainCtrl.getEventID());
    }

    /**
     * sets the correct values for the piechart
     */
    public void setPieChart(){
        List<ExpenseEntity> expenses = serverUtils.getAllExpensesOfEvent(mainCtrl.getEventID());

        // Create a map to store total expenses per tag
        Map<String, Double> expensesPerTag = new HashMap<>();

        // Calculate total expenses per tag
        for (ExpenseEntity expense : expenses) {
            TagEntity tag = expense.getTag();
            String tagName = tag.getTagType();
            double expenseAmount = expense.getMoney();

            expensesPerTag.put(tagName, expensesPerTag.getOrDefault(tagName, 0.0) + expenseAmount);
        }

        // Create an ObservableList to hold the data for the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Populate the pie chart data
        for (Map.Entry<String, Double> entry : expensesPerTag.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey() +
                    " (" + entry.getValue() + ")", entry.getValue());
            pieChartData.add(slice);
        }

        // Set the pie chart data
        pieChart.setData(pieChartData);
    }

    /**
     * Calculates the total spent in the whole event
     */
    public void displayTotalMoney(){
        Double sum = 0.0;
        List<ExpenseEntity> expenses = serverUtils.getAllExpensesOfEvent(mainCtrl.getEventID());
        for (ExpenseEntity e : expenses){
            sum += e.getMoney();
        }

        StringBuilder sumToString = new StringBuilder();
        sumToString.append(sum.toString()).append("€");
        totalSum.setText(sumToString.toString());
    }

}
