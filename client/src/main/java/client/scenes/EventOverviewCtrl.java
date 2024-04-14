package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import dto.view.EventDetailsDto;
import dto.view.EventOverviewDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class EventOverviewCtrl implements MultiLanguages {
    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    @FXML
    public Label titleLabel;
    @FXML
    public Label newEventLabel;
    @FXML
    public Button newEventButton;
    @FXML
    public Label paymentLabel;
    @FXML
    public Button paymentButton;
    @FXML
    public ComboBox<String> languageBox;

    @FXML
    private VBox eventContainer;

    @FXML
    private Button enrollBtn;

    @FXML
    private TextField inviteCodeTextField;

    @FXML
    private Button enterButton;

    @FXML
    private Label inviteCodeErrorMessage;

    @FXML
    private Label inviteCodeLabel;

    private final NewExpenseCtrl newExpenseCtrl;


    /**
     * Injector for EventOverviewCtrl
     *
     * @param mainCtrl       The Main Controller
     * @param serverUtils    the server utilities
     * @param newExpenseCtrl new expense controller
     */
    @Inject
    public EventOverviewCtrl(MainCtrl mainCtrl, ServerUtils serverUtils,
                             NewExpenseCtrl newExpenseCtrl){
        this.mainCtrl = mainCtrl;
        this.serverUtils=serverUtils;
        this.newExpenseCtrl = newExpenseCtrl;
    }

    /**
     * Updates the languages of all scenes (except admin)
     */
    public void initialize() {
        inviteCodeTextField.setText("");
        String locale = configManager.getProperty("language")
                + "_" + configManager.getProperty("country");
        updateLanguageBox(languageBox, locale);
    }

    /**
     * When a language has been selected
     * Update the config file
     * Update all scenes with the new languages
     */
    public void uponSelectionLanguage() {
        if (!languageBox.getValue().contains("_")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(mainCtrl.lang.getString("add_language_header"));
            alert.setContentText(mainCtrl.lang.getString("add_language_body"));
            alert.showAndWait().ifPresent(response -> createNewLanguage());
        } else {
            String[] selection = languageBox.getValue().split("_");
            MultiLanguages.setLocaleFromConfig(selection[0], selection[1]);
        }
        mainCtrl.updateLanguagesOfScenes();
    }

    /**
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        Locale l = Locale.getDefault();
        String locale = l.getLanguage() + "_" + l.getCountry();

        languageBox.setValue(locale);
        try {
            ResourceBundle lang = mainCtrl.lang;
            titleLabel.setText(lang.getString("event_overview"));
            newEventLabel.setText(lang.getString("new_event"));
            paymentLabel.setText(lang.getString("payments"));
            inviteCodeLabel.setText(lang.getString("event_invite_code"));
            enterButton.setText(lang.getString("enter_event"));
            inviteCodeErrorMessage.setText(lang.getString("invalid_message"));
        } catch (Exception e) {
            throw new RuntimeException();
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
     * Loads the events and displays them on the page
     */
    public void loadEvents() {
        List<EventOverviewDto> events = this.serverUtils.getAllEvents()
                .stream()
                .sorted((e1, e2) -> e2.getLastModifiedDate().compareTo(e1.getLastModifiedDate()))
                .toList();
        Node[] nodes=new Node[events.size()];


        for (int i = 0; i < nodes.length; i++) {
            var loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader()
                    .getResource(Path.of("client.scenes", "eventItem.fxml").toString()));
            try {
                nodes[i]=loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Node currentNode=nodes[i];

            Button eventButton = (Button) currentNode.lookup("#eventTitle");
            eventButton.setText(events.get(i).getTitle());
        }
        this.eventContainer.getChildren().clear();
        this.eventContainer.getChildren().addAll(nodes);
    }

    private void showDetails(long id) {
        mainCtrl.showEventDetails(id);
    }

    /**
     * Loads the event details of the event with the given invite code (from the text field)
     */
    public void loadEventDetails(){
        this.inviteCodeErrorMessage.setVisible(false);
        String inviteCode=this.inviteCodeTextField.getText().trim();
        if (inviteCode.isBlank()){
            inviteCodeErrorMessage.setVisible(true);
            return;
        }
        EventDetailsDto event=this.serverUtils.getEventDetailsByInviteCode(inviteCode);
        if (event.getId()==null){
            this.inviteCodeErrorMessage.setVisible(true);
            return;
        }

        this.mainCtrl.showEventDetails(event.getId());
        newExpenseCtrl.init(event);
        this.inviteCodeTextField.setText("");
    }


    /**
     * Adds an enter shortcut if you click enter when you enter an invite code
     * @param e the key pressed
     */
    public void enterWithInvite(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                loadEventDetails();
                break;
            default:
                break;
        }
    }

}
