package client.scenes.admin;

import client.utils.ServerUtils;
import dto.view.EventOverviewDto;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AdminOverviewPageCtrlTest {

    private AdminOverviewPageCtrl adminOverviewPageCtrl;
    private AdminMainCtrl adminMainCtrl;
    private ServerUtils serverUtils;

    @BeforeEach
    void setUp() {
        // Create a mock AdminMainCtrl and ServerUtils
        adminMainCtrl = mock(AdminMainCtrl.class);
        serverUtils = mock(ServerUtils.class);

        // Initialize the AdminOverviewPageCtrl with the mock objects
        adminOverviewPageCtrl = new AdminOverviewPageCtrl(adminMainCtrl, serverUtils, "");
    }

    @Test
    void stop() {
        // When
        adminOverviewPageCtrl.stop();

        // Then
        verify(serverUtils, times(1)).stop();
        // You're verifying that the stop method of serverUtils is called once
    }

    @Test
    void showRestore() {
        // Given
        // Mock the adminMainCtrl behavior to showRestore()
        doNothing().when(adminMainCtrl).showRestore();

        // When
        adminOverviewPageCtrl.showRestore();

        // Then
        // Verify that the adminMainCtrl's showRestore() method is called
        verify(adminMainCtrl, times(1)).showRestore();
    }
}
