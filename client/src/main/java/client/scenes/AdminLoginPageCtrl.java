package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import com.google.inject.Inject;

public class AdminLoginPageCtrl {

    private final AdminMainCtrl adminMainCtrl;

    @FXML
    public TextField passwordField;

    @FXML
    public Label passwordLabel;

    @FXML
    public Text incorrectPasswordError;

    /**
     * The constructor
     * @param adminMainCtrl the main admin controller
     */
    @Inject
    public AdminLoginPageCtrl(AdminMainCtrl adminMainCtrl){
        this.adminMainCtrl=adminMainCtrl;
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

        if (passwordInserted.equals("12345")){
            adminMainCtrl.showAdminOverview();
        }else {
            incorrectPasswordError.setOpacity(1.0d);
        }
    }


}
