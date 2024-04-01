package server.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import dto.UserCreationDto;
import dto.view.EventOverviewDto;
import dto.view.UserNameDto;
import org.springframework.validation.BindingResult;
import server.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRestControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult result;

    private UserRestController userRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRestController = new UserRestController(userService);
    }

    @Test
    void createUser() {
        // Arrange
        UserCreationDto requestBody = new UserCreationDto(/* Create your test DTO */);
        UserNameDto expectedDto = new UserNameDto(/* Create your expected DTO */);
        when(userService.createUser(requestBody)).thenReturn(expectedDto);

        // Act
        ResponseEntity<UserNameDto> response = userRestController.createUser(requestBody, result);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(userService, times(1)).createUser(requestBody);
    }

    @Test
    void getUserEvents() {
        // Arrange
        long userId = 1L;
        List<EventOverviewDto> expectedEvents = new ArrayList<>();
        expectedEvents.add(new EventOverviewDto(/* Create your expected EventOverviewDto */));
        expectedEvents.add(new EventOverviewDto(/* Create another expected EventOverviewDto */));
        when(userService.getUserEvents(userId)).thenReturn(expectedEvents);

        // Act
        ResponseEntity<List<EventOverviewDto>> response = userRestController.getUserEvents(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEvents, response.getBody());
        verify(userService, times(1)).getUserEvents(userId);
    }

    @Test
    void checkUserCredentialsValidity() {
        // Arrange
        UserCreationDto requestBody = new UserCreationDto(/* Create your test DTO */);

        // Act
        ResponseEntity<Void> response = userRestController.checkUserCredentialsValidity(requestBody);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verifyNoInteractions(userService);
    }

    @Test
    void testCreateUser() {
        UserCreationDto userCreationDto = new UserCreationDto(); // Initialize user DTO with necessary fields
        when(userService.createUser(userCreationDto)).thenReturn(new UserNameDto()); // Mock userService

        ResponseEntity<UserNameDto> responseEntity = userRestController.createUser(userCreationDto, result);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).createUser(userCreationDto); // Verify userService method called
    }

    @Test
    void testJoinEvent() {
        String inviteCode = "sampleInviteCode"; // Sample invite code
        long userId = 1L; // Sample user ID

        ResponseEntity<Void> responseEntity = userRestController.joinEvent(inviteCode, userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).join(inviteCode, userId); // Verify userService method called
    }

    @Test
    void testLeaveEvent() {
        long eventId = 1L; // Sample event ID
        long userId = 1L; // Sample user ID

        ResponseEntity<Void> responseEntity = userRestController.leaveEvent(eventId, userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).leave(eventId, userId); // Verify userService method called
    }

    @Test
    void getUserIDByEmail() {
        // Arrange
        String email = "email@mail.com";
        Long expectedID = 1L;
        when(userService.findIdByEmail(email)).thenReturn(expectedID);

        // Act
        ResponseEntity<Long> response = userRestController.getUserIDByEmail(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedID, response.getBody());
        verify(userService, times(1)).findIdByEmail(email);
    }
}
