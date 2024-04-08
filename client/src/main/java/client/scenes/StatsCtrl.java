package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class StatsCtrl {

    private final MainCtrl mainCtrl;

    private final ServerUtils serverUtils;

    @FXML
    public PieChart pieChart;
    @FXML
    public Text totalSum;
    @FXML
    public Button backBtn;

    @Inject
    public StatsCtrl(MainCtrl mainCtrl, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;

    }

    public void retToEventOverview(){
        mainCtrl.showOverview();
    }


}
