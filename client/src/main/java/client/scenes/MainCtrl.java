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


public class MainCtrl {

    private StartPageCtrl startPageCtrl;

    private EventOverviewCtrl eventOverviewCtrl;
    private Scene startPage;

    private Scene eventOverview;
    private Stage primaryStage;

    /**
     * The initialize method
     * @param primaryStage The primary Stage
     * @param startPage The start Page
     * @param eventOverview The event Overview
     */
    public void initialize(Stage primaryStage, Pair<StartPageCtrl, Parent> startPage,
                           Pair<EventOverviewCtrl, Parent> eventOverview) {
        this.primaryStage = primaryStage;

        this.startPageCtrl = startPage.getKey();
        this.eventOverviewCtrl = eventOverview.getKey();

        this.startPage = new Scene(startPage.getValue());
        this.eventOverview = new Scene(eventOverview.getValue());

        showStart();
        primaryStage.show();
    }

    /**
     * Shows the start scene
     */
    public void showStart() {
        primaryStage.setTitle("Start Page");
        primaryStage.setScene(startPage);

        startPage.setOnKeyPressed(e -> startPageCtrl.keyPressed(e));

        startPageCtrl.refresh();
    }

    /**
     * Shows the overview scene
     */
    public void showOverview(){

        primaryStage.setTitle("Events Overview");
        primaryStage.setScene(eventOverview);
    }


}