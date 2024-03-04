package server.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import commons.dto.view.EventDetailsDto;
import commons.dto.view.EventTitleDto;
import server.service.EventService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void testCreateEvent() {
        // Arrange
        String title = "New Event";
        EventTitleDto expectedDto = new EventTitleDto(/* Create your expected DTO */);
        when(eventService.createEvent(title)).thenReturn(expectedDto);

        // Act
        ResponseEntity<EventTitleDto> response = eventRestController.createEvent(title);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(eventService, times(1)).createEvent(title);
    }

    @Test
    void testAddParticipant() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        when(eventService.addParticipant(eventId, userId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = eventRestController.addParticipant(eventId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService, times(1)).addParticipant(eventId, userId);
    }

    @Test
    void testDeleteParticipant() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        when(eventService.deleteParticipant(eventId, userId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = eventRestController.deleteParticipant(eventId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService, times(1)).deleteParticipant(eventId, userId);
    }
}
