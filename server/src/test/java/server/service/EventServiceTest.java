package server.service;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import server.controller.exception.ObjectNotFoundException;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.EventTitleDto;
import server.dto.view.UserNameDto;
import server.repository.EventRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    private EventService eventService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        eventService = new EventService(eventRepository, modelMapper, userService, expenseService);
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
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
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
    void saveEventByTitle_ReturnsSavedEventEntity() {
        // Arrange
        String eventTitle = "New Event";
        EventTitleDto eventTitleDto = new EventTitleDto(eventTitle);
        EventEntity eventEntity = new EventEntity(1L, "", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
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
    void updateById_ReturnsUpdatedEventTitleDto() {
        // Arrange
        long id = 1L;
        String newTitle = "Updated Event Title";
        EventTitleDto updatedTitleDto = new EventTitleDto(newTitle);
        EventEntity eventEntity = new EventEntity(id, "inviteCode", "Original Title",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
        EventTitleDto expectedDto = new EventTitleDto(newTitle);
        eventEntity.setTitle(newTitle);
        when(eventRepository.findById(id)).thenReturn(Optional.of(eventEntity));

        // Act
        EventTitleDto resultDto = eventService.updateById(id, updatedTitleDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals(expectedDto.getTitle(), resultDto.getTitle());
    }

    @Test
    void updateById_ThrowsObjectNotFoundException() {
        // Arrange
        long id = 1L;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> eventService.updateById(id, new EventTitleDto("New Title")));
    }

    @Test
    void saveEventByTitle_ReturnsCreatedEventEntity() {
        // Arrange
        String title = "New Event Title";
        EventTitleDto eventTitleDto = new EventTitleDto(title);
        EventEntity expectedEntity = new EventEntity(1L, "inviteCode", title,
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
        when(eventRepository.save(any())).thenReturn(expectedEntity);

        // Act
        EventEntity resultEntity = eventService.saveEventByTitle(eventTitleDto);

        // Assert
        assertNotNull(resultEntity);
        assertEquals(expectedEntity.getTitle(), resultEntity.getTitle());
    }

    @Test
    void createEvent_ReturnsCreatedEventEntity() {
        // Arrange
        String title = "New Event Title";
        EventEntity expectedEntity = new EventEntity(1L, "inviteCode", title,
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
        when(eventRepository.save(any(EventEntity.class))).thenReturn(expectedEntity);

        // Act
        EventEntity resultEntity = eventService.createEvent(title);

        // Assert
        assertNotNull(resultEntity);
        assertEquals(expectedEntity.getTitle(), resultEntity.getTitle());
    }

    @Test
    void getAllEvents_ReturnsListOfEventOverviewDto() {
        // Arrange
        List<EventEntity> eventEntities = Arrays.asList(
                new EventEntity(1L, "inviteCode1", "Event 1", new HashSet<>(), new HashSet<>(), new Date(), new Date()),
                new EventEntity(2L, "inviteCode2", "Event 2", new HashSet<>(), new HashSet<>(), new Date(), new Date())
        );
        List<EventOverviewDto> expectedDtos = Arrays.asList(
                new EventOverviewDto(1L, "Event 1", "inviteCode1", new Date(), new Date()),
                new EventOverviewDto(2L, "Event 2", "inviteCode2", new Date(), new Date())
        );
        when(eventRepository.findAll()).thenReturn(eventEntities);

        // Act
        List<EventOverviewDto> resultDtos = eventService.getAllEvents();

        // Assert
        assertNotNull(resultDtos);
        assertEquals(expectedDtos.size(), resultDtos.size());
        for (int i = 0; i < expectedDtos.size(); i++) {
            EventOverviewDto expectedDto = expectedDtos.get(i);
            EventOverviewDto resultDto = resultDtos.get(i);
            assertEquals(expectedDto.getId(), resultDto.getId());
            assertEquals(expectedDto.getTitle(), resultDto.getTitle());
            assertEquals(expectedDto.getInviteCode(), resultDto.getInviteCode());
        }
    }

    @Test
    void getEventParticipants_ReturnsListOfUserNameDto() {
        // Arrange
        long eventId = 1L;
        List<UserEntity> userEntities = Arrays.asList(
                new UserEntity(1L, "John", "Doe", "john@example.com", new HashSet<>(), new BankAccountEntity()),
                new UserEntity(2L, "Alice", "Smith", "alice@example.com", new HashSet<>(), new BankAccountEntity())
        );
        List<UserNameDto> expectedDtos = Arrays.asList(
                new UserNameDto(1L, "John", "Doe"),
                new UserNameDto(2L, "Alice", "Smith")
        );
        when(eventRepository.findEventParticipants(eventId)).thenReturn(new HashSet<>(userEntities));

        // Act
        List<UserNameDto> resultDtos = eventService.getEventParticipants(eventId);

        // Assert
        assertNotNull(resultDtos);
        assertEquals(expectedDtos.size(), resultDtos.size());
        for (UserNameDto expectedDto : expectedDtos) {
            assertTrue(resultDtos.contains(expectedDto));
        }
    }

    @Test
    void findEntityById_ReturnsEventEntity() {
        // Arrange
        long eventId = 1L;
        EventEntity expectedEntity = new EventEntity(eventId, "inviteCode", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(expectedEntity));

        // Act
        EventEntity resultEntity = eventService.findEntityById(eventId);

        // Assert
        assertNotNull(resultEntity);
        assertEquals(expectedEntity.getTitle(), resultEntity.getTitle());
    }

    @Test
    void findEntityById_ThrowsObjectNotFoundException() {
        // Arrange
        long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> eventService.findEntityById(eventId));
    }

}
