package client.scenes;

import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;

public record SceneInputWrapper(Stage primaryStage, Pair<StartPageCtrl, Parent> startPage,
                                Pair<EventOverviewCtrl, Parent> eventOverview,
                                Pair<PaymentPageCtrl, Parent> paymentPage,
                                Pair<EventCtrl, Parent> eventPage,
                                Pair<EventCreationCtrl, Parent> eventCreationPage,
                                Pair<EventItemCtrl, Parent> eventItemPage,
                                Pair<NewParticipantCtrl, Parent> newParticipant,
                                Pair<ParticipantItemCtrl, Parent> participantItemPage,
                                Pair<ParticipantCtrl, Parent> participantPage,
                                Pair<NewExpenseCtrl, Parent> newExpensePage,
                                Pair<EnrollEventCtrl, Parent> enrollEventPage,
                                Pair<AddBankInfoCtrl, Parent> bankInfoPage) {
}