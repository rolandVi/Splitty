package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import dto.BankAccountCreationDto;
import jakarta.ws.rs.core.Response;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.http.HttpResponse;
import java.util.Optional;

public class AddBankInfoCtrl {

    private final MainCtrl mainCtrl;

    @FXML
    public Label headLabel;
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
     *  Initializes the page before setting it as a view
     */
    public void init(){
        var bankInfo=this.serverUtils.findBankDetails(mainCtrl.configManager.getProperty("userID"),
                mainCtrl.configManager.getProperty("serverURL"));
        this.incorrectData.setVisible(false);
        if (bankInfo.getIban()!=null){
            headLabel.setText("Edit Bank Account");
            ibanField.setText(bankInfo.getIban());
            bicField.setText(bankInfo.getBic());
            holder.setText(bankInfo.getHolder());
            addButton.setText("Edit");
            addButton.setOnAction(e -> editBankAccount());
        }
    }

    /**
     * Edits the bank account data of the current user with the data from the text fields
     */
    private void editBankAccount() {
        this.incorrectData.setVisible(false);

        if (!checkFields()){
            return;
        }

        String url = mainCtrl.configManager.getProperty("serverURL");
        BankAccountCreationDto bankAccount = new BankAccountCreationDto();
        bankAccount.setIban(ibanField.getText());
        bankAccount.setHolder(holder.getText());
        Long userId=Long.parseLong(mainCtrl.configManager.getProperty("userID"));
        bankAccount.setBic(bicField.getText());

        Optional<HttpResponse<String>> response =
                serverUtils.editBankAccount(userId, bankAccount, url);

        if (response.isEmpty()){
            this.incorrectData.setText("Invalid bank details");
            this.incorrectData.setVisible(true);
            return;
        }
        if (response.get().statusCode()==400){
            this.incorrectData.setText(response.get().body());
            this.incorrectData.setVisible(true);
            return;
        }
        returnToOverview();
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
    public void createBankAccount() {
        this.incorrectData.setVisible(false);

        if (!checkFields()){
            return;
        }

        String url = mainCtrl.configManager.getProperty("serverURL");
        BankAccountCreationDto bankAccount = new BankAccountCreationDto();
        bankAccount.setIban(ibanField.getText());
        bankAccount.setHolder(holder.getText());
        Long userId=Long.parseLong(mainCtrl.configManager.getProperty("userID"));
        bankAccount.setBic(bicField.getText());

        Response response=serverUtils.createBankAccount(userId, bankAccount, url);

        if (response.getStatus()==400){
            this.incorrectData.setText(response.readEntity(String.class));
            this.incorrectData.setVisible(true);
            return;
        }
        returnToOverview();
    }

    /**
     * Checks whether the fields of the bank account are empty
     * @return whether they are empty or not
     */
    private boolean checkFields(){
        if (ibanField.getText().isEmpty()
            || holder.getText().isEmpty()
            || bicField.getText().isEmpty()){
            this.incorrectData.setText("Please fill all fields");
            this.incorrectData.setVisible(true);
            return false;
        }
        return true;
    }

}
