package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

public class EventOverviewCtrl implements MultiLanguages {
    private final MainCtrl mainCtrl;
    @FXML
    public Label titleLabel;
    @FXML
    public Button b; // temporary button to access event scene
    @FXML
    public Label newEventLabel;
    @FXML
    public Button newEventButton;
    @FXML
    public Label paymentLabel;
    @FXML
    public Button paymentButton;


    /**
     * Injector for EventOverviewCtrl
     * @param mainCtrl The Main Controller
     */
    @Inject
    public EventOverviewCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = mainCtrl.lang;
            titleLabel.setText(lang.getString("event_overview"));
            newEventLabel.setText(lang.getString("new_event"));
            paymentLabel.setText(lang.getString("payments"));
        } catch (Exception e) {
            System.out.println("Incorrect key");
        }
    }

    /**
     * Will take the user to create a new event
     */
    public void newEvent(){
        mainCtrl.showNewEvent();
    }
    /**
     * Will take the user to the payment screen, where they can settle their debts
     */
    public void payment(){
        mainCtrl.showPayment();
    }

    /**
     * temporary button to access event scene
     */
    public void b(){
        mainCtrl.b();
    }
}
