package server.service;

import commons.EventEntity;
import commons.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import server.controller.exception.ObjectNotFoundException;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventTitleDto;
import server.repository.EventRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserService userService;

    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();
        eventService = new EventService(eventRepository, modelMapper, userService);
    }

    @Test
    void existsById_WhenEventExists_ReturnsTrue() {
        // Arrange
        long eventId = 1L;
        when(eventRepository.existsById(eventId)).thenReturn(true);

        // Act
        boolean result = eventService.existsById(eventId);

        // Assert
        assertTrue(result);
    }

    @Test
    void existsById_WhenEventDoesNotExist_ReturnsFalse() {
        // Arrange
        long eventId = 1L;
        when(eventRepository.existsById(eventId)).thenReturn(false);

        // Act
        boolean result = eventService.existsById(eventId);

        // Assert
        assertFalse(result);
    }

    @Test
    void getById_WhenEventExists_ReturnsEventDetailsDto() {
        // Arrange
        long eventId = 1L;
        EventEntity eventEntity = new EventEntity(eventId, "", "Test Event",
                new HashSet<>(), new HashSet<>());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));

        // Act
        EventDetailsDto result = eventService.getById(eventId);

        // Assert
        assertNotNull(result);
        assertEquals(eventEntity.getId(), result.getId());
        assertEquals(eventEntity.getTitle(), result.getTitle());
    }

    @Test
    void getById_WhenEventDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> eventService.getById(eventId));
    }

    @Test
    void removeById_WhenEventExists_RemovesEvent() {
        // Arrange
        long eventId = 1L;

        // Act
        eventService.removeById(eventId);

        // Assert
        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    void updateById_WhenEventExists_ReturnsUpdatedEventTitleDto() {
        // Arrange
        long eventId = 1L;
        String newTitle = "Updated Event Title";
        EventTitleDto updatedTitleDto = new EventTitleDto(newTitle);
        EventEntity eventEntity = new EventEntity(eventId, "", "Test Event",
                new HashSet<>(), new HashSet<>());

        when(eventRepository.getEventTitleById(eventId)).thenReturn("Test Event");

        // Act
        EventTitleDto result = eventService.updateById(eventId, updatedTitleDto);

        // Assert
        assertNotNull(result);
        assertEquals(eventEntity.getTitle(), result.getTitle());
    }

    @Test
    void updateById_WhenEventDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long eventId = 1L;
        String newTitle = "Updated Event Title";
        EventTitleDto updatedTitleDto = new EventTitleDto(newTitle);

        when(eventRepository.getEventTitleById(eventId)).thenReturn(null);

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> eventService.updateById(eventId, updatedTitleDto));
    }

    @Test
    void saveEventByTitle_ReturnsSavedEventEntity() {
        // Arrange
        String eventTitle = "New Event";
        EventTitleDto eventTitleDto = new EventTitleDto(eventTitle);
        EventEntity eventEntity = new EventEntity(1L, "", "Test Event",
                new HashSet<>(), new HashSet<>());
        eventEntity.setTitle(eventTitle);

        when(eventRepository.save(any())).thenReturn(eventEntity);

        // Act
        EventEntity savedEvent = eventService.saveEventByTitle(eventTitleDto);

        // Assert
        assertNotNull(savedEvent);
        assertEquals(eventEntity.getId(), savedEvent.getId());
        assertEquals(eventEntity.getTitle(), savedEvent.getTitle());
    }

    @Test
    void createEvent_ReturnsCreatedEventTitleDto() {
        // Arrange
        String eventTitle = "New Event";
        EventEntity eventEntity = new EventEntity(1L, "", "Test Event",
                new HashSet<>(), new HashSet<>());
        eventEntity.setTitle(eventTitle);

        when(eventRepository.save(any())).thenReturn(eventEntity);

        // Act
        EventTitleDto createdEvent = eventService.createEvent(eventTitle);

        // Assert
        assertNotNull(createdEvent);
        assertEquals(eventEntity.getId(), createdEvent.getId());
        assertEquals(eventEntity.getTitle(), createdEvent.getTitle());
    }

    @Test
    void addParticipant_WhenEventAndUserExist_AddsParticipant() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        EventEntity eventEntity = new EventEntity(eventId, "", "Test Event",
                new HashSet<>(), new HashSet<>());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));
        when(userService.findById(userId)).thenReturn(new UserEntity());

        // Act
        boolean added = eventService.addParticipant(eventId, userId);

        // Assert
        assertTrue(added);
        assertTrue(eventEntity.getParticipants().contains(new UserEntity()));
        verify(eventRepository, times(1)).save(eventEntity);
    }

    @Test
    void addParticipant_WhenEventDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> eventService.addParticipant(eventId, userId));

        verify(eventRepository, never()).save(any());
    }

    @Test
    void addParticipant_WhenUserIsDifferent_ThrowsObjectNotFoundException() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        EventEntity eventEntity = new EventEntity(eventId, "", "Test Event",
                new HashSet<>(), new HashSet<>());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));
        when(userService.findById(userId)).thenThrow(ObjectNotFoundException.class);

        // Act
        assertThrows(ObjectNotFoundException.class, () -> eventService.addParticipant(eventId, userId));

        // Assert
        verify(eventRepository, never()).save(any());
    }

    @Test
    void deleteParticipant_WhenEventAndUserExist_DeletesParticipant() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        Set<UserEntity> participants = new HashSet<>();
        participants.add(new UserEntity());
        EventEntity eventEntity = new EventEntity(eventId, "",
                "Test Event", new HashSet<>(), participants);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));
        when(userService.findById(userId)).thenReturn(new UserEntity());

        // Act
        boolean deleted = eventService.deleteParticipant(eventId, userId);

        // Assert
        assertTrue(deleted);
        assertFalse(eventEntity.getParticipants().contains(new UserEntity()));
        verify(eventRepository, times(1)).save(eventEntity);
    }

    @Test
    void deleteParticipant_WhenEventDoesNotExist_ThrowsIllegalArgumentException() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // Act
        assertThrows(IllegalArgumentException.class, () -> eventService.deleteParticipant(eventId, userId));

        // Assert
        verify(eventRepository, never()).save(any());
    }

    @Test
    void deleteParticipant_WhenUserIsDifferent_ReturnsFalse() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        EventEntity eventEntity = new EventEntity(eventId,
                "", "Test Event", new HashSet<>(), new HashSet<>());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));
        when(userService.findById(userId)).thenThrow(ObjectNotFoundException.class);

        // Act
        assertThrows(ObjectNotFoundException.class, () -> eventService.deleteParticipant(eventId, userId));

        // Assert
        verify(eventRepository, never()).save(any());
    }
}
