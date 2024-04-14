package client.scenes.admin;

import client.utils.ServerUtils;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminRestoreCtrlTest extends ApplicationTest{

    private AdminRestoreCtrl adminRestoreCtrl;
    private ServerUtils serverUtils;
    private AdminMainCtrl adminMainCtrl;

    @BeforeEach
    void setUp() {
        serverUtils = mock(ServerUtils.class);
        adminMainCtrl = mock(AdminMainCtrl.class);

        adminRestoreCtrl = new AdminRestoreCtrl(adminMainCtrl, serverUtils);
        adminRestoreCtrl.restoreBtn = new Button();
        adminRestoreCtrl.textField = new TextArea();
        adminRestoreCtrl.restoreMessage = new Text();
        adminRestoreCtrl.rootPane = new AnchorPane();
    }

    @Test
    void returnOverview() {
        adminRestoreCtrl.returnOverview();
        assertEquals(0, adminRestoreCtrl.restoreMessage.getOpacity());
    }

    @Test
    void restoreData() {
        String jsonData = "Sample JSON data";
        adminRestoreCtrl.textField.setText(jsonData);
        adminRestoreCtrl.restoreData();

        assertEquals(1, adminRestoreCtrl.restoreMessage.getOpacity());
        // Verify that restoreData method calls serverUtils.restoreData with the correct JSON data
        verify(serverUtils, times(1)).restoreData(jsonData);
    }

    @Test
    void keyPressed_Enter() throws IOException, InterruptedException {
        KeyEvent enterEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
        adminRestoreCtrl.textField.setText("Sample JSON data");
        adminRestoreCtrl.keyPressed(enterEvent);
        assertEquals(1, adminRestoreCtrl.restoreMessage.getOpacity());
    }

    @Test
    void keyPressed_Escape() throws IOException, InterruptedException {
        KeyEvent escapeEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
        adminRestoreCtrl.keyPressed(escapeEvent);
        assertEquals(0, adminRestoreCtrl.restoreMessage.getOpacity());
    }

    @Test
    void initialize() {
        // Simulate scene being shown for the first time
        adminRestoreCtrl.initialize();
        assertEquals(0, adminRestoreCtrl.restoreMessage.getOpacity());

        // Simulate scene being hidden after text field is not empty
        adminRestoreCtrl.textField.setText("Some text");
        adminRestoreCtrl.initialize();
        assertEquals("Some text", adminRestoreCtrl.textField.getText());
    }
}
