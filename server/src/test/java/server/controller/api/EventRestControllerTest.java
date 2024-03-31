package server.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import dto.view.EventDetailsDto;
import dto.view.EventOverviewDto;
import dto.view.EventTitleDto;
import dto.view.UserNameDto;
import server.service.EventService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventRestControllerTest {

    @Mock
    private EventService eventService;

    private EventRestController eventRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventRestController = new EventRestController(eventService);
    }

    @Test
    void testGetById() {
        // Arrange
        long eventId = 1L;
        EventDetailsDto expectedDto = new EventDetailsDto(/* Create your expected DTO */);
        when(eventService.getById(eventId)).thenReturn(expectedDto);

        // Act
        ResponseEntity<EventDetailsDto> response = eventRestController.getById(eventId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(eventService, times(1)).getById(eventId);
    }

    @Test
    void testRemoveById() {
        // Arrange
        long validId = 1L;
        when(eventService.existsById(validId)).thenReturn(true);

        // Act
        ResponseEntity<Void> responseValidId = eventRestController.removeById(validId);

        // Assert
        assertEquals(HttpStatus.OK, responseValidId.getStatusCode());
        verify(eventService, times(1)).removeById(validId);

        // Arrange for invalid id
        long invalidId = -1L;
        when(eventService.existsById(invalidId)).thenReturn(false);

        // Act
        ResponseEntity<Void> responseInvalidId = eventRestController.removeById(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseInvalidId.getStatusCode());
        verify(eventService, never()).removeById(invalidId);
    }

    @Test
    void testUpdateEventTitleById() {
        // Arrange
        long eventId = 1L;
        EventTitleDto requestBody = new EventTitleDto("New Title");
        EventTitleDto expectedDto = new EventTitleDto(/* Create your expected DTO */);
        when(eventService.updateById(eventId, requestBody)).thenReturn(expectedDto);

        // Act
        ResponseEntity<EventTitleDto> response = eventRestController.updateEventTitleById(eventId, requestBody);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(eventService, times(1)).updateById(eventId, requestBody);
    }

    @Test
    void getAllEvents() {
        // Arrange
        List<EventOverviewDto> expectedEvents = new ArrayList<>();
        expectedEvents.add(new EventOverviewDto(/* Create your expected EventOverviewDto */));
        expectedEvents.add(new EventOverviewDto(/* Create another expected EventOverviewDto */));
        when(eventService.getAllEvents()).thenReturn(expectedEvents);

        // Act
        ResponseEntity<List<EventOverviewDto>> response = eventRestController.getAllEvents();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEvents, response.getBody());
        verify(eventService, times(1)).getAllEvents();
    }


    @Test
    void testGetEventParticipants() {
        long eventId = 1L; // Sample event ID
        List<UserNameDto> userNameDtoList = new ArrayList<>(); // Initialize list of user name DTOs
        when(eventService.getEventParticipants(eventId)).thenReturn(userNameDtoList); // Mock eventService

        ResponseEntity<List<UserNameDto>> responseEntity = eventRestController.getEventParticipants(eventId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userNameDtoList, responseEntity.getBody());
        verify(eventService, times(1)).getEventParticipants(eventId); // Verify eventService method called
    }
}
