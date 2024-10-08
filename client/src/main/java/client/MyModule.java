package client;

import client.scenes.*;
import client.scenes.admin.AdminLoginPageCtrl;
import client.scenes.admin.AdminMainCtrl;
import client.scenes.admin.AdminOverviewPageCtrl;
import client.utils.ServerUtils;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import jakarta.ws.rs.client.Client;

public class MyModule implements Module {

    /**
     * Configure
     * @param binder the binder
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(StartPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EventOverviewCtrl.class).in(Scopes.SINGLETON);
        binder.bind(PaymentPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminMainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminLoginPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EventItemCtrl.class).in(Scopes.NO_SCOPE);
        binder.bind(AdminOverviewPageCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ServerUtils.class).in(Scopes.SINGLETON);
        binder.bind(Client.class).toProvider(ClientProvider.class);
        binder.bind(NewExpenseCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ParticipantDetailsCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ExpenseDetailsCtrl.class).in(Scopes.SINGLETON);
    }
}
