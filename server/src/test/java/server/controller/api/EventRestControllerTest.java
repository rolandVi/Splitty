package server.controller.api;

import commons.ExpenseEntity;
import dto.CreatorToTitleDto;
import dto.ParticipantCreationDto;
import dto.view.EventDetailsDto;
import dto.view.EventOverviewDto;
import dto.view.EventTitleDto;
import dto.view.ParticipantNameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.async.DeferredResult;
import server.exception.FieldValidationException;
import server.service.EventService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        EventDetailsDto expectedDto = new EventDetailsDto();
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
        EventTitleDto expectedDto = new EventTitleDto();
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
        expectedEvents.add(new EventOverviewDto());
        expectedEvents.add(new EventOverviewDto());
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
        long eventId = 1L;
        List<ParticipantNameDto> userNameDtoList = new ArrayList<>();
        when(eventService.getEventParticipants(eventId)).thenReturn(userNameDtoList);

        ResponseEntity<List<ParticipantNameDto>> responseEntity = eventRestController.getEventParticipants(eventId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userNameDtoList, responseEntity.getBody());
        verify(eventService, times(1)).getEventParticipants(eventId);
    }

    @Test
    void testCreateEventRestore() {
        EventDetailsDto requestDto = new EventDetailsDto();
        EventDetailsDto expectedDto = new EventDetailsDto();
        when(eventService.saveEvent(requestDto)).thenReturn(expectedDto);


        ResponseEntity<EventDetailsDto> response = eventRestController.createEvent(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(eventService, times(1)).saveEvent(requestDto);
    }


    @Test
    void testGetByInviteCode() {
        // Arrange
        String inviteCode = "ABCD1234";
        EventDetailsDto expectedDto = new EventDetailsDto();
        when(eventService.getByInviteCode(inviteCode)).thenReturn(expectedDto);

        // Act
        ResponseEntity<EventDetailsDto> response = eventRestController.getByInviteCode(inviteCode);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(eventService, times(1)).getByInviteCode(inviteCode);
    }

    @Test
    void testDeleteParticipant() {
        // Arrange
        long eventId = 1L;
        long participantId = 1L;

        // Act
        ResponseEntity<Void> response = eventRestController.deleteParticipant(eventId, participantId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService, times(1)).deleteParticipant(eventId, participantId);
    }

    @Test
    void testAddExpense() {
        // Arrange
        long eventId = 1L;
        ExpenseEntity expense = new ExpenseEntity();

        // Act
        ResponseEntity<Void> response = eventRestController.addExpense(eventId, expense);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService, times(1)).addExpense(expense);
    }

    @Test
    void testGetUpdates() {
        // Act
        DeferredResult<ResponseEntity<EventOverviewDto>> response = eventRestController.getUpdates();

        // Assert
        assertNull(response.getResult());
    }

    @Test
    void testAddParticipant() {
        // Arrange
        long eventId = 1L;
        ParticipantCreationDto participant = new ParticipantCreationDto();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        ParticipantNameDto participantNameDto = new ParticipantNameDto();
        participantNameDto.setId(1L);
        participantNameDto.setFirstName("John");
        participantNameDto.setLastName("Doe");
        when(eventService.addParticipant(eventId, participant)).thenReturn(participantNameDto);

        // Act
        ResponseEntity<ParticipantNameDto> response = eventRestController.addParticipant(eventId, participant, result);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(participantNameDto, response.getBody());
        verify(eventService, times(1)).addParticipant(eventId, participant);
    }

    @Test
    void testAddParticipantFieldValidationException() {
        // Arrange
        long eventId = 1L;
        ParticipantCreationDto participant = new ParticipantCreationDto();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        // Act and Assert
        assertThrows(FieldValidationException.class, () -> {
            eventRestController.addParticipant(eventId, participant, result);
        });
    }

    @Test
    void testCreateEvent() {
        // Arrange
        CreatorToTitleDto creatorToTitleDto = new CreatorToTitleDto();
        EventDetailsDto createdEvent = new EventDetailsDto();

        when(eventService.createEvent(creatorToTitleDto)).thenReturn(createdEvent);

        // Act
        ResponseEntity<EventDetailsDto> response = eventRestController.createEvent(creatorToTitleDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdEvent, response.getBody());

        // Verify that createEvent was called with the correct argument
        verify(eventService, times(1)).createEvent(creatorToTitleDto);
    }

}
