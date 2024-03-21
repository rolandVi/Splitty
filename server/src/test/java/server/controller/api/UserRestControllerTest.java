package server.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.dto.UserCreationDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.UserNameDto;
import server.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRestControllerTest {

    @Mock
    private UserService userService;

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
        ResponseEntity<UserNameDto> response = userRestController.createUser(requestBody);

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
}
