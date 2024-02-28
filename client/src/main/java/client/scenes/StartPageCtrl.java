package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.awt.*;

public class StartPageCtrl {

    private final MainCtrl mainCtrl;

    @FXML
    public TextField serverField;

    @FXML
    public Text errorMessage;


    @Inject
    public StartPageCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }

    public void connect(){
        String serverInserted = serverField.getText();

        if(!serverInserted.equals("http://localhost:8080")){
            errorMessage.setOpacity(1.0d);
        }
        else{
            mainCtrl.showOverview();
        }
    }


    public void refresh(){
        //server calls missing
    }
}
