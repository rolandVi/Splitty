package client.scenes;

import javax.inject.Inject;

public class EventOverviewCtrl {

    private final MainCtrl mainCtrl;

    /**
     * Injector for EventOverviewCtrl
     * @param mainCtrl The Main Controller
     */
    @Inject
    public EventOverviewCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }
}
