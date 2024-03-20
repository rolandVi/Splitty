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
package client.scenes.admin;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;


public class AdminMainCtrl {

    private Stage adminOverviewStage;

    private AdminLoginPageCtrl loginPageCtrl;
    private AdminOverviewPageCtrl overviewPageCtrl;

    private Scene loginPage;
    private Scene overviewPage;

    /**
     * The initialize method
     * @param adminOverviewStage The admin overview stage
     * @param loginPage The login page
     * @param overviewPage the admin overview page
     *
     */
    public void initialize(Stage adminOverviewStage,
                           Pair<AdminLoginPageCtrl, Parent> loginPage,
                           Pair<AdminOverviewPageCtrl, Parent> overviewPage) {
        this.adminOverviewStage = adminOverviewStage;

        this.loginPageCtrl = loginPage.getKey();
        this.overviewPageCtrl = overviewPage.getKey();

        this.loginPage = new Scene(loginPage.getValue());
        this.overviewPage = new Scene(overviewPage.getValue());


        showLogin();
        adminOverviewStage.show();
    }


    /**
     * Shows the login scene
     */
    public void showLogin() {
        adminOverviewStage.setTitle("Admin Login Page");
        adminOverviewStage.setScene(loginPage);

        loginPage.setOnKeyPressed(k -> loginPageCtrl.keyPressed(k));
    }

    /**
     * Shows the admin overview
     */
    public void showAdminOverview(){
        adminOverviewStage.setTitle("Admin Overview");
        adminOverviewStage.setScene(overviewPage);
        overviewPageCtrl.loadEvents();
    }


}