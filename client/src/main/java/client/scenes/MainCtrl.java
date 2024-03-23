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

import java.util.Locale;
import java.util.ResourceBundle;
import client.sceneUtils.LanguageComboBoxUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;


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
    protected ResourceBundle lang;

    private Scene eventItemPage;

    private EventItemCtrl eventItemCtrl;

    private Scene newParticipant;
    private NewParticipantCtrl newParticipantCtrl;
    private Scene participantItem;
    private Scene participantEdit;
    private ParticipantCtrl participantCtrl;

    private EnrollEventCtrl enrollEventCtrl;

    private Scene enrollPage;

    private AddBankInfoCtrl bankInfoCtrl;
    private Scene addBankInfo;

    private Scene newExpensePage;
    private NewExpenseCtrl newExpenseCtrl;

    /**
     * The initialize method
     * @param sceneInputWrapper Wrapper for the inputs because of high number of parameters
     */
    public void initialize(SceneInputWrapper sceneInputWrapper){
        this.primaryStage = sceneInputWrapper.primaryStage();

        this.startPageCtrl = sceneInputWrapper.startPage().getKey();
        this.eventOverviewCtrl = sceneInputWrapper.eventOverview().getKey();
        this.paymentPageCtrl = sceneInputWrapper.paymentPage().getKey();
        this.eventCtrl = sceneInputWrapper.eventPage().getKey();
        this.eventCreationCtrl = sceneInputWrapper.eventCreationPage().getKey();
        this.eventItemCtrl= sceneInputWrapper.eventItemPage().getKey();
        this.newParticipantCtrl = sceneInputWrapper.newParticipant().getKey();
        this.participantCtrl = sceneInputWrapper.participantPage().getKey();
        this.bankInfoCtrl = sceneInputWrapper.bankInfoPage().getKey();
        this.newExpenseCtrl = sceneInputWrapper.newExpensePage().getKey();

        this.startPage = new Scene(sceneInputWrapper.startPage().getValue());
        this.eventOverview = new Scene(sceneInputWrapper.eventOverview().getValue());

        this.paymentPage = new Scene(sceneInputWrapper.paymentPage().getValue());

        this.eventPage = new Scene(sceneInputWrapper.eventPage().getValue());
        this.eventCreationPage = new Scene(sceneInputWrapper.eventCreationPage().getValue());

        this.eventItemPage=new Scene(sceneInputWrapper.eventItemPage().getValue());

        this.newExpensePage = new Scene(sceneInputWrapper.newExpensePage().getValue());

        this.eventOverview.getStylesheets().add(
                Objects.requireNonNull(this.getClass().getClassLoader()
                        .getResource(Path.of("stylesheets", "eventOverview.css").toString()))
                        .toExternalForm());

        this.newParticipant = new Scene(sceneInputWrapper.newParticipant().getValue());
        this.participantItem = new Scene(sceneInputWrapper.participantItemPage().getValue());
        this.participantEdit = new Scene(sceneInputWrapper.participantPage().getValue());
        this.addBankInfo = new Scene(sceneInputWrapper.bankInfoPage().getValue());
        this.enrollPage=new Scene(sceneInputWrapper.enrollEventPage().getValue());

        showStart();
        sceneInputWrapper.primaryStage().show();
        updateLanguagesOfScenes();
    }

    /**
     * Updates the languages of all scenes (except admin)
     */
    protected void updateLanguagesOfScenes() {
        Locale.setDefault(LanguageComboBoxUtil.getLocaleFromConfig());
        lang = ResourceBundle.getBundle("languages.lang");
        eventCtrl.updateLanguage();
        eventOverviewCtrl.updateLanguage();
        eventCreationCtrl.updateLanguage();
        paymentPageCtrl.updateLanguage();
        startPageCtrl.updateLanguage();
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
        primaryStage.setScene(newExpensePage);
    }


    /**
     * Creates the event details page and sets it as the scene
     * @param id the id of the event
     */
    public void showEventDetails(long id) {
        eventCtrl.init(id);
        primaryStage.setTitle("Page");
        eventCtrl.loadExpenseList();
        eventCtrl.loadParticipants();
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
     * Shows the enroll page
     */
    public void showEnrollPage(){
        primaryStage.setTitle("Enroll");
        primaryStage.setScene(enrollPage);
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
     * Shows the addBankInfo page such that a user may add bank credentials to their accounts
     */
    public void showAddNewBank() {
        primaryStage.setTitle("addNewBank page");
        primaryStage.setScene(addBankInfo);
    }
}