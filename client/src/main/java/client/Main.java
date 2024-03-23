/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import client.scenes.*;
import client.scenes.admin.AdminLoginPageCtrl;
import client.scenes.admin.AdminMainCtrl;
import client.scenes.admin.AdminOverviewPageCtrl;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Main function
     * @param args the args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage) {

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

        var startPage = FXML.load(StartPageCtrl.class, "client.scenes", "startPage.fxml");
        var eventOverview = FXML.load(EventOverviewCtrl.class,
                "client.scenes", "eventOverview.fxml");
        var paymentPage = FXML.load(PaymentPageCtrl.class, "client.scenes", "paymentPage.fxml");
        var eventPage = FXML.load(EventCtrl.class, "client.scenes", "event.fxml");
        var eventCreationPage = FXML.load(EventCreationCtrl.class,
                "client.scenes", "eventCreation.fxml");
        var eventItemPage=FXML.load(EventItemCtrl.class,
                "client.scenes", "eventItem.fxml");
        var newParticipant = FXML.load(NewParticipantCtrl.class,
                "client.scenes", "newParticipant.fxml");

        var participantItem = FXML.load(ParticipantItemCtrl.class,
                "client.scenes", "participantItem.fxml");
        var participant = FXML.load(ParticipantCtrl.class,
                "client.scenes", "Participant.fxml");
        var addBankInfo = FXML.load(AddBankInfoCtrl.class,
                "client.scenes", "addBankInfo.fxml");
        var newExpense = FXML.load(NewExpenseCtrl.class,
                "client.scenes", "newExpense.fxml");

        var enrollEventPage=FXML.load(EnrollEventCtrl.class,
                "client.scenes", "enroll.fxml");

        mainCtrl.initialize(
                new SceneInputWrapper(primaryStage, startPage,
                        eventOverview, paymentPage, eventPage,
                        eventCreationPage, eventItemPage, newParticipant,
                        participantItem, participant, newExpense, enrollEventPage, addBankInfo));
    }

    /**
     * Creates a new stage for admin overview
     */
    public static void openAdminOverview(){

        Stage adminOverviewStage = new Stage();
        var adminMainCtrl = INJECTOR.getInstance(AdminMainCtrl.class);

        var adminLoginPage = FXML.load(AdminLoginPageCtrl.class,
                "client.scenes", "adminLoginPage.fxml");
        var adminOverviewPage = FXML.load(AdminOverviewPageCtrl.class,
                "client.scenes", "adminOverviewPage.fxml");

        adminMainCtrl.initialize(adminOverviewStage, adminLoginPage, adminOverviewPage);
    }

}