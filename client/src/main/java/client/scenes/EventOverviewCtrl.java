package client.scenes;

import javax.inject.Inject;

public class EventOverviewCtrl {

    private final MainCtrl mainCtrl;

    @Inject
    public EventOverviewCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }
}
