package client.scenes;

import client.utils.LanguageComboBoxUtil;

import client.utils.ServerUtils;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import server.dto.view.EventOverviewDto;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

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
    public ComboBox<String> languageComboBox;

    @FXML
    private VBox eventContainer;

    @FXML
    private Button enrollBtn;


    /**
     * Injector for EventOverviewCtrl
     * @param mainCtrl The Main Controller
     * @param serverUtils the server utilities
     */
    @Inject
    public EventOverviewCtrl(MainCtrl mainCtrl, ServerUtils serverUtils){
        this.mainCtrl = mainCtrl;
        this.serverUtils=serverUtils;
    }

    /**
     * Updates the languages of all scenes (except admin)
     */
    public void initialize() {
        LanguageComboBoxUtil.updateLanguageComboBox(languageComboBox);
    }

    /**
     * When a language has been selected
     * Update the config file
     * Update all scenes with the new languages
     */
    public void uponSelectionLanguage() {
        String[] selection = languageComboBox.getValue().split("-");
        LanguageComboBoxUtil.setLocaleFromConfig(selection[0], selection[1]);
        mainCtrl.updateLanguagesOfScenes();
    }
    /**
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = mainCtrl.lang;
            titleLabel.setText(lang.getString("event_overview"));
            newEventLabel.setText(lang.getString("new_event"));
            paymentLabel.setText(lang.getString("payments"));
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
        long userId=1L; //TODO: get the id of the current user

//        List<EventOverviewDto> events = this.serverUtils.getEventsByUser(userId);
        List<EventOverviewDto> events = this.serverUtils.getAllEvents();
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
            final EventOverviewDto event=events.get(i);

            Button eventButton = (Button) currentNode.lookup("#eventTitle");
            eventButton.setText(events.get(i).getTitle());

            Button inviteBtn=(Button) currentNode.lookup("#inviteCodeButton");
            inviteBtn.setOnAction(e -> copyInvite(event.getInviteCode()));

            eventButton.setOnAction(e -> showDetails(event.getId()));
        }
        this.eventContainer.getChildren().clear();
        this.eventContainer.getChildren().addAll(nodes);
    }

    private void showDetails(long id) {
        mainCtrl.showEventDetails(id);
    }

    /**
     * Event listener that copies the invite code of the selected event to the clipboard
     * @param inviteCode the invite code for the event
     */
    public void copyInvite(String inviteCode){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(inviteCode);
        clipboard.setContent(content);
    }

    /**
     * Shows the enroll page
     */
    public void showEnrollPage(){
        mainCtrl.showEnrollPage();
    }
}
