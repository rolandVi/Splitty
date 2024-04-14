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
}
