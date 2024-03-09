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
package client.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.util.Pair;

import java.util.Locale;
import java.util.ResourceBundle;


public class AdminMainCtrl {

    protected ResourceBundle config;
    protected ResourceBundle lang;

    private Stage adminOverviewStage;

    private AdminLoginPageCtrl loginPageCtrl;

    private Scene loginPage;

    /**
     * The initialize method
     * @param adminOverviewStage The admin overview Stage
     * @param loginPage The login Page
     */
    public void initialize(Stage adminOverviewStage,
                           Pair<AdminLoginPageCtrl, Parent> loginPage) {
        this.config = ResourceBundle.getBundle("config");

        this.adminOverviewStage = adminOverviewStage;

        this.loginPageCtrl = loginPage.getKey();

        this.loginPage = new Scene(loginPage.getValue());


        showLogin();
        adminOverviewStage.show();
        updateLanguagesOfScenes();
    }

    private void updateLanguagesOfScenes(){
        Locale.setDefault(getLocalFromConfig());
        lang = ResourceBundle.getBundle("languages.lang");
        loginPageCtrl.updateLanguage();
    }

    private Locale getLocalFromConfig(){
        String language = config.getString("locale");
        String country = config.getString("country");
        return Locale.of(language, country);
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
        //to be done
    }

}