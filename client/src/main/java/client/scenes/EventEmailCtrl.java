package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventEmailCtrl implements MultiLanguages{

    private final MainCtrl mainCtrl;
    private final ServerUtils serverUtils;
    private String inviteCode;
    @FXML
    public Label titleLabel;
    @FXML
    public Label descriptionLabel;
    @FXML
    public TextArea inputTextArea;
    @FXML
    public Button sendButton;
    @FXML
    public Button returnButton;
    @FXML
    public Text sentMessage;
    @FXML
    public Text errorMessage;

    /**
     * Injector for EventOverviewCtrl
     * @param mainCtrl The Main Controller
     * @param serverUtils the server utilities
     */
    @Inject
    public EventEmailCtrl(MainCtrl mainCtrl, ServerUtils serverUtils){
        this.mainCtrl = mainCtrl;
        this.serverUtils=serverUtils;
    }

    /**
     * Updates the language of the scene using the resource bundle
     */
    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = mainCtrl.lang;
            // Labels
            titleLabel.setText(lang.getString("event_email_title"));
            descriptionLabel.setText(lang.getString("event_email_description"));
            // Buttons
            sendButton.setText(lang.getString("send"));
            returnButton.setText(lang.getString("return"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverview(){
        sentMessage.setOpacity(0);
        errorMessage.setOpacity(0);
        inputTextArea.clear();
        mainCtrl.showEvent();
    }

    /**
     * Send emails to all inserted email addresses
     */
    public void sendInvite(){
        sentMessage.setOpacity(0);
        errorMessage.setOpacity(0);
        String[] arr = inputTextArea.getText().split("\n");

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        boolean format = true;
        for (String email : arr){
            Matcher matcher = pattern.matcher(email);
            System.out.println(email +" : "+ matcher.matches()+"\n");
            if (matcher.matches()) {
                serverUtils.sendEmail(email, inviteCode);
            } else {
                format = false;
            }
        }
        if (format) {
            sentMessage.setOpacity(1);
            inputTextArea.clear();
        } else {
            errorMessage.setOpacity(1);
        }
    }

    /**
     * Refreshes the scene
     * Used to update the invite code
     * @param inviteCode the invite code of the event to send
     */
    public void refresh(String inviteCode) {
        this.inviteCode = inviteCode;
        sentMessage.setOpacity(0);
        errorMessage.setOpacity(0);
        inputTextArea.clear();
    }
}
