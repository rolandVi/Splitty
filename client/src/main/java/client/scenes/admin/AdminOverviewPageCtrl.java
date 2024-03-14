package client.scenes.admin;


import com.google.inject.Inject;


public class AdminOverviewPageCtrl {

    private final AdminMainCtrl adminMainCtrl;

    /**
     * The constructor
     * @param adminMainCtrl - the main admin controller
     */
    @Inject
    public AdminOverviewPageCtrl(AdminMainCtrl adminMainCtrl){
        this.adminMainCtrl=adminMainCtrl;
    }
}
