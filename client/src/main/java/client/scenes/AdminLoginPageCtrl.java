package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import com.google.inject.Inject;

import java.util.ResourceBundle;

public class AdminLoginPageCtrl implements MultiLanguages {

    private final AdminMainCtrl adminMainCtrl;

    @FXML
    public TextField passwordField;

    @FXML
    public Label passwordLabel;

    @FXML
    public Text incorrectPasswordError;

    @FXML
    public Button loginButton;

    /**
     * The constructor
     * @param adminMainCtrl the main admin controller
     */
    @Inject
    public AdminLoginPageCtrl(AdminMainCtrl adminMainCtrl){
        this.adminMainCtrl=adminMainCtrl;
    }

    @Override
    public void updateLanguage() {
        try {
            ResourceBundle lang = adminMainCtrl.lang;
            passwordLabel.setText(lang.getString("enter_password"));
            loginButton.setText(lang.getString("login"));
            incorrectPasswordError.setText(lang.getString("incorrect_password"));
        } catch (Exception e) {
            System.out.println("Incorrect key");
        }
    }

    /**
     * Checks for ENTER key press
     *
     * @param k The key
     */
    public void keyPressed(KeyEvent k){
        switch (k.getCode()){
            case ENTER:
                login();
                break;
            default:
                break;
        }
    }

    /**
     * Validated the password and shows the admin overview
     */
    public void login(){
        String passwordInserted = passwordField.getText();

        // temporary solution. When password generation is ready, will update it
        if (passwordInserted.equals("12345")){
            adminMainCtrl.showAdminOverview();
        }else {
            incorrectPasswordError.setOpacity(1.0d);
        }
    }

}
