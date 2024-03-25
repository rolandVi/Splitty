package client.scenes.admin;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class AdminRestoreCtrl {
    private final AdminMainCtrl adminMainCtrl;
    @FXML
    public Button restoreBtn;
    @FXML
    public TextArea textField;
    @FXML
    public Button returnBtn;


    @Inject
    public AdminRestoreCtrl(AdminMainCtrl adminMainCtrl) {
        this.adminMainCtrl = adminMainCtrl;
    }
    public void returnOverview(){
        adminMainCtrl.showAdminOverview();
    }
}
