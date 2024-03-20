package client.scenes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import server.dto.BankAccountCreationDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
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

    /**
     * Injector for PaymentPageCtrl
     * @param mainCtrl The Main Controller
     */
    @Inject
    public AddBankInfoCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
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
            throws IOException, InterruptedException {
        // Todo: replace temporary value with host selected at start
        String url = "http://localhost:8080";


        // Prepare user data from text fields
//        String holder = firstNameField.getText() + " " + surNameField.getText();
        String iban = ibanField.getText();
        String bic = bicField.getText();
        BankAccountCreationDto bankAccount = new BankAccountCreationDto();
        bankAccount.setIban(iban);
        bankAccount.setHolder("holder"); // Temporary solution since we can't link the bankAccount to the user yet we also don't have access to the email of the user.
        bankAccount.setBic(bic);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bankAccount);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(url + "/api/bankaccounts/"))
                .header("Content-Type", "application/json")
                .build();

        returnToOverview();

        // Send HTTP request to server
        // Return HTTP response from server
        Optional<HttpResponse<String>> response;
        try {
            response = Optional.of(HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
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
