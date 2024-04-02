package server.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import dto.ParticipantCreationDto;
import dto.view.EventOverviewDto;
import dto.view.ParticipantNameDto;
import org.springframework.validation.BindingResult;
import server.service.ParticipantService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRestControllerTest {

    @Mock
    private ParticipantService userService;

    @Mock
    private BindingResult result;

    private ParticipantRestController userRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRestController = new ParticipantRestController(userService);
    }

    @Test
    void createUser() {
        // Arrange
        ParticipantCreationDto requestBody = new ParticipantCreationDto(/* Create your test DTO */);
        ParticipantNameDto expectedDto = new ParticipantNameDto(/* Create your expected DTO */);
        when(userService.createUser(requestBody)).thenReturn(expectedDto);

        // Act
        ResponseEntity<ParticipantNameDto> response = userRestController.createUser(requestBody, result);

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
    void testCreateUser() {
        ParticipantCreationDto userCreationDto = new ParticipantCreationDto(); // Initialize user DTO with necessary fields
        when(userService.createUser(userCreationDto)).thenReturn(new ParticipantNameDto()); // Mock userService

        ResponseEntity<ParticipantNameDto> responseEntity = userRestController.createUser(userCreationDto, result);

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
