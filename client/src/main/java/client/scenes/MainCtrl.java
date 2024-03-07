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

import java.io.IOException;


public class MainCtrl {

    private StartPageCtrl startPageCtrl;

    private EventOverviewCtrl eventOverviewCtrl;
    private Scene startPage;

    private Scene eventOverview;
    private Stage primaryStage;

    private Scene paymentPage;

    private PaymentPageCtrl paymentPageCtrl;

    private EventCtrl eventCtrl;
    private EventCreationCtrl eventCreationCtrl;
    private Scene eventPage;
    private Scene eventCreationPage;

    private Scene eventItemPage;

    private EventItemCtrl eventItemCtrl;

    /**
     * The initialize method
     *
     * @param primaryStage      The primary Stage
     * @param startPage         The start Page
     * @param eventOverview     The event Overview
     * @param paymentPage       The payment page
     * @param eventPage         The event page
     * @param eventCreationPage The create an event page
     * @param eventItemPage
     */
    public void initialize(Stage primaryStage, Pair<StartPageCtrl, Parent> startPage,
                           Pair<EventOverviewCtrl, Parent> eventOverview,
                           Pair<PaymentPageCtrl, Parent> paymentPage,
                           Pair<EventCtrl, Parent> eventPage,
                           Pair<EventCreationCtrl, Parent> eventCreationPage,
                           Pair<EventItemCtrl, Parent> eventItemPage) {
        this.primaryStage = primaryStage;

        this.startPageCtrl = startPage.getKey();
        this.eventOverviewCtrl = eventOverview.getKey();
        this.paymentPageCtrl = paymentPage.getKey();
        this.eventCtrl = eventPage.getKey();
        this.eventCreationCtrl = eventCreationPage.getKey();
        this.eventItemCtrl=eventItemPage.getKey();

        this.startPage = new Scene(startPage.getValue());
        this.eventOverview = new Scene(eventOverview.getValue());

        this.paymentPage = new Scene(paymentPage.getValue());

        this.eventPage = new Scene(eventPage.getValue());
        this.eventCreationPage = new Scene(eventCreationPage.getValue());

        this.eventItemPage=new Scene(eventItemPage.getValue());

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
    public void showOverview() {
        primaryStage.setTitle("Events Overview");
        primaryStage.setScene(eventOverview);
        eventOverviewCtrl.loadEvents();
    }


    /**
     * Shows the new event scene
     */
    public void showNewEvent(){
        primaryStage.setTitle("New Event");
        primaryStage.setScene(eventCreationPage);
    }

    /**
     * Shows the payment screen to the user where they can choose to settle their debts
     */
    public void showPayment(){
        primaryStage.setTitle("Payment Page");
        primaryStage.setScene(paymentPage);
    }

    /**
     * Shows the expense screen to add a new expense to an event
     */
    public void showNewExpense(){
        primaryStage.setTitle("New Expense");
//        primaryStage.setScene(expensePage); expense page has not been created yet
    }
    /**
     * Temporary button to access event scene
     */
    public void b(){
        primaryStage.setTitle(" Page");
        primaryStage.setScene(eventPage);
    }


}