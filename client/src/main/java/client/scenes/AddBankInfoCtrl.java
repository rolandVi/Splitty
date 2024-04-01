package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import dto.BankAccountCreationDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class AddBankInfoCtrl {

    private final MainCtrl mainCtrl;
    @FXML
    public TextField ibanField;
    @FXML
    public TextField bicField;

    @FXML
    public TextField holder;
    @FXML
    public Button goBackButton;
    @FXML
    public Button addButton;
    @FXML
    public Label incorrectData;

    private final ServerUtils serverUtils;

    /**
     * Injector for PaymentPageCtrl
     * @param mainCtrl The Main Controller
     * @param serverUtils The ServerUtils
     */
    @Inject
    public AddBankInfoCtrl(MainCtrl mainCtrl, ServerUtils serverUtils){
        this.mainCtrl = mainCtrl;
        this.serverUtils = serverUtils;
    }


    /**
     * Will show event overview when the goBack button is pressed
     */
    public void returnToOverview(){
        mainCtrl.showOverview();
    }

    /**
     * Checks for key press
     *
     * @param e The key
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case BACK_SPACE:
                returnToOverview();
                break;
            default:
                break;
        }
    }

    /**
     * Creates HTTP request to the server using the contents of text fields as user info
     */
    public void createBankAccount()
            throws IOException {
        String url = mainCtrl.configManager.getProperty("serverURL");
        BankAccountCreationDto bankAccount = new BankAccountCreationDto();
        bankAccount.setIban(ibanField.getText());
        bankAccount.setHolder(holder.getText());
        Long userId=Long.parseLong(mainCtrl.configManager.getProperty("userID"));
        bankAccount.setBic(bicField.getText());

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bankAccount);

        serverUtils.createBankAccount(userId, requestBody, url);
        returnToOverview();
    }

    // todo : implement checking for the credentials
     /* copied from startpagectrl
        Optional<HttpResponse<String>> bankAccountResponse = createBankAccount();
        if (bankAccountResponse.isEmpty() || bankAccountResponse.get().statusCode()==400){
            this.incorrectData.setVisible(true);
            return;
        }
         */

}
