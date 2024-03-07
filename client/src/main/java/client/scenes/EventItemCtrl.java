package client.scenes;

import commons.dto.view.EventOverviewDto;
import javafx.fxml.FXML;

import java.awt.*;

public class EventItemCtrl {

    private EventOverviewDto event;
    @FXML
    private Label eventTitle;
    @FXML
    private Button inviteCodeButton;

    public void init(EventOverviewDto event){
        this.event=event;
        this.eventTitle.setText(event.getTitle());
    }
}
