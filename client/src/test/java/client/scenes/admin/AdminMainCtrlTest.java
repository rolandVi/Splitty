package client.scenes.admin;

import client.ConfigManager;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class) // Allows the use of JavaFX classes in the tests
class AdminMainCtrlTest {

    private AdminMainCtrl adminMainCtrl;

    @BeforeEach
    void setUp() {
        adminMainCtrl = new AdminMainCtrl();
    }

    @Test
    void getLocalFromConfig() {
        AdminMainCtrl adminMainCtrlSpy = spy(AdminMainCtrl.class);
        adminMainCtrlSpy.config = mock(ConfigManager.class);
        when(adminMainCtrlSpy.config.getProperty("language")).thenReturn("en");
        when(adminMainCtrlSpy.config.getProperty("country")).thenReturn("US");

        Locale locale = adminMainCtrlSpy.getLocalFromConfig();

        assertEquals(Locale.US, locale);
    }

    @Test
    void showOverviews() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            Pair<AdminOverviewPageCtrl, Parent> overviewPage = new Pair<>(mock(AdminOverviewPageCtrl.class), new Parent() {});

            adminMainCtrl.showAdminOverview();

            assertEquals("Admin Overview", stage.getTitle());
            assertEquals(overviewPage.getValue(), stage.getScene().getRoot());
        });

        Platform.runLater(() -> {
            Stage stage = new Stage();
            Pair<AdminRestoreCtrl, Parent> restorePage = new Pair<>(mock(AdminRestoreCtrl.class), new Parent() {});

            adminMainCtrl.showRestore();

            assertEquals("Admin Restore", stage.getTitle());
            assertEquals(restorePage.getValue(), stage.getScene().getRoot());
        });

        Platform.runLater(() -> {
            Stage stage = new Stage();
            Pair<AdminLoginPageCtrl, Parent> loginPage = new Pair<>(mock(AdminLoginPageCtrl.class), new Parent() {});

            adminMainCtrl.showLogin();

            assertEquals("Admin Login Page", stage.getTitle());
            assertEquals(loginPage.getValue(), stage.getScene().getRoot());
        });
    }
}
