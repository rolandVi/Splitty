package client.scenes.admin;

import commons.exceptions.PasswordExpiredException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import com.google.inject.Inject;

import client.utils.ServerUtils;

public class AdminLoginPageCtrl {

    private final AdminMainCtrl adminMainCtrl;

    private final ServerUtils serverUtils;

    @FXML
    public TextField passwordField;

    @FXML
    public Label passwordLabel;

    @FXML
    public Text incorrectPasswordError;

    /**
     * The constructor
     * @param adminMainCtrl the main admin controller
     * @param serverUtils the server utilities
     */
    @Inject
    public AdminLoginPageCtrl(AdminMainCtrl adminMainCtrl, ServerUtils serverUtils){
        this.adminMainCtrl=adminMainCtrl;
        this.serverUtils=serverUtils;
    }

    /**
     * Checks for ENTER key press
     *
     * @param k The key
     */
    public void keyPressed(KeyEvent k){
        if (k.getCode()== KeyCode.ENTER) login();
    }

    /**
     * Prompts the server to generate a new password
     */
    public void generatePassword(){
        serverUtils.generatePassword();
    }

    /**
     * Validated the password and shows the admin overview
     */
    public void login(){
        String passwordInserted = passwordField.getText();
        try {
            if (serverUtils.validatePassword(passwordInserted)){
                incorrectPasswordError.setOpacity(0.0d);
                adminMainCtrl.showAdminOverview();
            }else {
                incorrectPasswordError.setText("Incorrect password. Try again...");
                incorrectPasswordError.setOpacity(1.0d);
            }
        }catch (PasswordExpiredException e){
            incorrectPasswordError.setText(e.getMessage());
            incorrectPasswordError.setOpacity(1.0d);
        }
    }


}
