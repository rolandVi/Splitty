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

import client.ConfigManager;
import client.utils.ServerUtils;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainCtrl {
    public static final String CONFIG_FILE_PATH = "client/src/main/resources/config.properties";
    protected ResourceBundle lang;
    protected ConfigManager configManager;
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

    private Scene newParticipant;
    private NewParticipantCtrl newParticipantCtrl;
    private Scene participantItem;
    private Scene participantEdit;
    private ParticipantCtrl participantCtrl;

    private Scene newExpensePage;
    private NewExpenseCtrl newExpenseCtrl;

    private CustomTagCtrl customTagCtrl;
    private Scene customTag;

    private StatsCtrl statsCtrl;
    private Scene statistics;

    private EventEmailCtrl eventEmailCtrl;
    private Scene eventEmail;


    private ServerUtils serverUtils;

    /**
     * The initialize method
     * @param sceneInputWrapper Wrapper for the inputs because of high number of parameters
     * @param serverUtils The server utilities
     */
    public void initialize(SceneInputWrapper sceneInputWrapper, ServerUtils serverUtils){
        this.configManager = new ConfigManager(CONFIG_FILE_PATH);

        this.primaryStage = sceneInputWrapper.primaryStage();

        this.startPageCtrl = sceneInputWrapper.startPage().getKey();
        this.eventOverviewCtrl = sceneInputWrapper.eventOverview().getKey();
        this.paymentPageCtrl = sceneInputWrapper.paymentPage().getKey();
        this.eventCtrl = sceneInputWrapper.eventPage().getKey();
        this.eventCreationCtrl = sceneInputWrapper.eventCreationPage().getKey();
        this.eventItemCtrl= sceneInputWrapper.eventItemPage().getKey();
        this.newParticipantCtrl = sceneInputWrapper.newParticipant().getKey();
        this.participantCtrl = sceneInputWrapper.participantPage().getKey();
        this.newExpenseCtrl = sceneInputWrapper.newExpensePage().getKey();


        this.statsCtrl = sceneInputWrapper.stats().getKey();

        this.eventEmailCtrl = sceneInputWrapper.eventEmailPage().getKey();


        this.startPage = new Scene(sceneInputWrapper.startPage().getValue());
        this.eventOverview = new Scene(sceneInputWrapper.eventOverview().getValue());
        this.paymentPage = new Scene(sceneInputWrapper.paymentPage().getValue());
        this.eventPage = new Scene(sceneInputWrapper.eventPage().getValue());
        this.eventCreationPage = new Scene(sceneInputWrapper.eventCreationPage().getValue());
        this.eventItemPage=new Scene(sceneInputWrapper.eventItemPage().getValue());
        this.statistics = new Scene(sceneInputWrapper.stats().getValue());

        this.newExpensePage = new Scene(sceneInputWrapper.newExpensePage().getValue());



        this.eventOverview.getStylesheets().add(
                Objects.requireNonNull(this.getClass().getClassLoader()
                        .getResource(Path.of("stylesheets", "eventOverview.css").toString()))
                        .toExternalForm());

        this.startPage.getStylesheets().add(
                Objects.requireNonNull(this.getClass().getClassLoader()
                                .getResource(Path.of("stylesheets", "startPage.css").toString()))
                        .toExternalForm());

        this.newParticipant = new Scene(sceneInputWrapper.newParticipant().getValue());
        this.participantItem = new Scene(sceneInputWrapper.participantItemPage().getValue());
        this.participantEdit = new Scene(sceneInputWrapper.participantPage().getValue());

        this.eventEmail = new Scene(sceneInputWrapper.eventEmailPage().getValue());
        showStart();

        sceneInputWrapper.primaryStage().show();
        updateLanguagesOfScenes();
    }


    /**
     * Updates the languages of all scenes (except admin)
     */
    protected void updateLanguagesOfScenes() {
        // Gets the locale from recent session from config.properties
        Locale.setDefault(MultiLanguages.getLocaleFromConfig());
        lang = ResourceBundle.getBundle("languages.lang");

        // Update the language of each scene
        eventCtrl.updateLanguage();
        eventOverviewCtrl.updateLanguage();
        eventCreationCtrl.updateLanguage();
        paymentPageCtrl.updateLanguage();
        startPageCtrl.updateLanguage();
        eventEmailCtrl.updateLanguage();
    }

    /**
     * Shows the start scene
     */
    public void showStart() {
        primaryStage.setTitle("Start Page");
        primaryStage.setScene(startPage);

        startPage.setOnKeyPressed(e -> {
            try {
                startPageCtrl.keyPressed(e);
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

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
        newExpenseCtrl.init(eventCtrl.getEventDetailsDto());
        primaryStage.setScene(newExpensePage);
    }

    /**
     * Calls init method that initializes the edition page and displays it
     */
    public void showEditExpense(){
        primaryStage.setTitle("Edit Expense");
        newExpenseCtrl.initEdit(eventCtrl.getEventDetailsDto(), newExpenseCtrl.getExpenseDetails());
        primaryStage.setScene(newExpensePage);
    }


    /**
     * Creates the event details page and sets it as the scene
     * @param id the id of the event
     */
    public void showEventDetails(long id) {
        eventCtrl.init(id);
        primaryStage.setTitle("Page");
        primaryStage.setScene(eventPage);
    }

    /**
     * Shows the add new participant scene
     */
    public void showNewParticipant() {
        primaryStage.setTitle("newParticipant page");
        primaryStage.setScene(newParticipant);
    }

    /**
     * Creates the participant edit page and sets it as the scene
     * @param parID id of the participant
     * @param eventId id of the scene
     */
    public void showParticipantEdit(long parID, long eventId) {
        participantCtrl.init(parID, eventId);
        primaryStage.setTitle("editParticipant page");
        primaryStage.setScene(participantEdit);
    }


    /**
     * Shows the statistics page
     */
    public void showStats(){
        primaryStage.setTitle("stats page");
        primaryStage.setScene(statistics);
    }



     /** Shows the scene to send emails with the invite code
     * @param inviteCode the invite code of the event to send
     */
    public void showEventEmail(String inviteCode) {
        primaryStage.setTitle("Send Email");
        primaryStage.setScene(eventEmail);
        eventEmailCtrl.refresh(inviteCode);
    }

    /**
     * Show the most recent selected event
     */
    public void showEvent() {
        primaryStage.setTitle("Event");
        primaryStage.setScene(eventPage);
    }

}