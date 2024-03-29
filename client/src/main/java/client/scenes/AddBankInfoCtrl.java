package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import server.dto.BankAccountCreationDto;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;

public class AddBankInfoCtrl {

    private final MainCtrl mainCtrl;
    @FXML
    public TextField ibanField;
    @FXML
    public TextField bicField;
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
     * @return HTTP response from the server
     */
    public Optional<HttpResponse<String>> createBankAccount()
            throws IOException {
        String url = mainCtrl.configManager.getProperty("serverURL");
        String iban = ibanField.getText();
        String bic = bicField.getText();
        BankAccountCreationDto bankAccount = new BankAccountCreationDto();
        bankAccount.setIban(iban);
        bankAccount.setHolder("holder");
        // Temporary solution since we can't link the bankAccount to the user
        // ,yet we also don't have access to the email of the user.
        bankAccount.setBic(bic);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bankAccount);

        returnToOverview();

        return serverUtils.createBankAccount(requestBody, url);
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
