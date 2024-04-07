package server.service;

import commons.EventEntity;
import commons.ExpenseEntity;
import commons.ParticipantEntity;
import dto.CreatorToTitleDto;
import dto.ParticipantCreationDto;
import dto.view.EventDetailsDto;
import dto.view.EventTitleDto;
import dto.view.ParticipantNameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import server.exception.ObjectNotFoundException;
import server.repository.EventRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    private ModelMapper modelMapper;

    @Mock
    private ParticipantService participantService;

    @Mock
    private ExpenseService expenseService;

    private EventService eventService;

    @BeforeEach
    public void setup(){
        this.modelMapper=new ModelMapper();
        this.eventService=new EventService(eventRepository, modelMapper, participantService, expenseService);
    }

    // Test for removeById method
    @Test
    public void testRemoveById_ValidId_RemovesSuccessfully() {
        // Arrange
        long eventId = 1L;

        // Act
        eventService.removeById(eventId);

        // Assert
        verify(eventRepository).deleteById(eventId);
    }

    // Test for saveEventByTitle method
    @Test
    public void testSaveEventByTitle_ValidDto_SavesSuccessfully() {
        // Arrange
        EventTitleDto titleDto = new EventTitleDto();
        titleDto.setTitle("Test Event");
        EventEntity savedEntity = new EventEntity();
        when(eventRepository.save(any())).thenReturn(savedEntity);

        // Act
        EventEntity result = eventService.saveEventByTitle(titleDto);

        // Assert
        assertNotNull(result);
    }


    // Test for removeExpense method
    @Test
    public void testRemoveExpense_ValidInput_RemovesExpenseFromEvent() {
        // Arrange
        Long eventId = 1L;
        EventEntity eventEntity = new EventEntity();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));
        when(eventRepository.save(any())).thenReturn(eventEntity);

        ExpenseEntity expenseEntity = new ExpenseEntity();

        // Act
        eventService.removeExpense(eventId, expenseEntity);

        // Assert
        verify(eventRepository).save(eventEntity);
    }

    @Test
    public void testSaveEvent_ValidDto_SavesSuccessfully() {
        // Arrange
        EventDetailsDto eventDetailsDto = new EventDetailsDto();
        eventDetailsDto.setTitle("Test Event");
        eventDetailsDto.setInviteCode("INVITECODE");
        EventEntity savedEntity = new EventEntity();
        when(eventRepository.save(any())).thenReturn(savedEntity);

        // Act
        EventDetailsDto result = eventService.saveEvent(eventDetailsDto);

        // Assert
        assertNotNull(result);
        assertEquals(savedEntity.getId(), result.getId());
        assertEquals(savedEntity.getTitle(), result.getTitle());
        assertEquals(savedEntity.getInviteCode(), result.getInviteCode());
    }

    @Test
    public void testUpdateById_ValidInput_UpdatesSuccessfully() {
        // Arrange
        long eventId = 1L;
        EventTitleDto titleDto = new EventTitleDto();
        titleDto.setTitle("Updated Event Title");
        EventEntity eventEntity = new EventEntity();
        eventEntity.setTitle(titleDto.getTitle());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));

        // Act
        EventTitleDto result = eventService.updateById(eventId, titleDto);

        // Assert
        assertNotNull(result);
        assertEquals(titleDto.getTitle(), result.getTitle());
        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, times(1)).updateEventTitleById(eventId, titleDto.getTitle());
    }

    @Test
    public void testGetEventParticipants_ReturnsListOfParticipants() {
        // Arrange
        long eventId = 1L;
        List<ParticipantEntity> participants = Collections.singletonList(new ParticipantEntity());
        when(eventRepository.findEventParticipants(eventId)).thenReturn(new HashSet<>(participants));

        // Act
        List<ParticipantNameDto> result = eventService.getEventParticipants(eventId);

        // Assert
        assertEquals(participants.size(), result.size());
        verify(eventRepository).findEventParticipants(eventId);
    }

    @Test
    public void testCreateEvent_ValidDto_CreatesEventSuccessfully() {
        // Arrange
        CreatorToTitleDto creatorToTitleDto = new CreatorToTitleDto();
        creatorToTitleDto.setTitle("Test Event");
        EventEntity savedEntity = new EventEntity();
        when(eventRepository.save(any())).thenReturn(savedEntity);
        when(eventRepository.findById(any())).thenReturn(Optional.of(savedEntity));

        // Act
        EventDetailsDto result = eventService.createEvent(creatorToTitleDto);

        // Assert
        assertNotNull(result);
        assertEquals(savedEntity.getId(), result.getId());
        assertEquals(savedEntity.getTitle(), result.getTitle());
    }

    // Test for getAllEvents method
    @Test
    public void testGetAllEvents_ReturnsAllEvents() {
        // Arrange
        when(eventRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        eventService.getAllEvents();

        // Assert
        verify(eventRepository).findAll();
    }

    // Test for findEntityById method when entity exists
    @Test
    public void testFindEntityById_EntityExists_ReturnsEntity() {
        // Arrange
        long eventId = 1L;
        EventEntity eventEntity = new EventEntity();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));

        // Act
        EventEntity result = eventService.findEntityById(eventId);

        // Assert
        assertNotNull(result);
        assertEquals(eventEntity, result);
    }

    // Test for findEntityById method when entity does not exist
    @Test
    public void testFindEntityById_EntityDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> eventService.findEntityById(eventId));
    }

    // Test for findEntityByInviteCode method when entity exists
    @Test
    public void testFindEntityByInviteCode_EntityExists_ReturnsEntity() {
        // Arrange
        String inviteCode = "INVITECODE";
        EventEntity eventEntity = new EventEntity();
        when(eventRepository.findEventEntityByInviteCode(inviteCode)).thenReturn(Optional.of(eventEntity));

        // Act
        EventEntity result = eventService.findEntityByInviteCode(inviteCode);

        // Assert
        assertNotNull(result);
        assertEquals(eventEntity, result);
    }

    // Test for findEntityByInviteCode method when entity does not exist
    @Test
    public void testFindEntityByInviteCode_EntityDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        String inviteCode = "INVITECODE";
        when(eventRepository.findEventEntityByInviteCode(inviteCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> eventService.findEntityByInviteCode(inviteCode));
    }

    // Test for deleteParticipant method
    @Test
    public void testDeleteParticipant_CallsDeleteParticipantInParticipantService() {
        // Arrange
        Long eventId = 1L;
        Long participantId = 2L;

        // Act
        eventService.deleteParticipant(eventId, participantId);

        // Assert
        verify(participantService).deleteParticipant(participantId);
    }

    // Test for getByInviteCode method when entity exists
    @Test
    public void testGetByInviteCode_EntityExists_ReturnsDto() {
        // Arrange
        String inviteCode = "INVITECODE";
        EventEntity eventEntity = new EventEntity();
        EventDetailsDto expectedDto = new EventDetailsDto();
        when(eventRepository.findEventEntityByInviteCode(inviteCode)).thenReturn(Optional.of(eventEntity));

        // Act
        EventDetailsDto result = eventService.getByInviteCode(inviteCode);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }


    // Test for addParticipant method
    @Test
    public void testAddParticipant_ValidInput_AddsParticipant() {
        // Arrange
        Long eventId = 1L;
        ParticipantCreationDto participantDto = new ParticipantCreationDto();
        participantDto.setFirstName("John");
        participantDto.setLastName("Doe");
        participantDto.setEmail("john@example.com");

        EventEntity eventEntity = new EventEntity();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));
        when(participantService.createParticipant(any(), any())).thenReturn(new ParticipantNameDto());

        // Act
        ParticipantNameDto result = eventService.addParticipant(eventId, participantDto);

        // Assert
        assertNotNull(result);
        verify(participantService).createParticipant(participantDto, eventEntity);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        eventService = new EventService(eventRepository, modelMapper, participantService, expenseService);
    }
//
//    @Test
//    void existsById_WhenEventExists_ReturnsTrue() {
//        // Arrange
//        long eventId = 1L;
//        when(eventRepository.existsById(eventId)).thenReturn(true);
//
//        // Act
//        boolean result = eventService.existsById(eventId);
//
//        // Assert
//        assertTrue(result);
//    }
//
//    @Test
//    void existsById_WhenEventDoesNotExist_ReturnsFalse() {
//        // Arrange
//        long eventId = 1L;
//        when(eventRepository.existsById(eventId)).thenReturn(false);
//
//        // Act
//        boolean result = eventService.existsById(eventId);
//
//        // Assert
//        assertFalse(result);
//    }
//
//    @Test
//    void getById_WhenEventExists_ReturnsEventDetailsDto() {
//        // Arrange
//        long eventId = 1L;
//        EventEntity eventEntity = new EventEntity(eventId, "", "Test Event",
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity));
//
//        // Act
//        EventDetailsDto result = eventService.getById(eventId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(eventEntity.getId(), result.getId());
//        assertEquals(eventEntity.getTitle(), result.getTitle());
//    }
//
//    @Test
//    void getById_WhenEventDoesNotExist_ThrowsObjectNotFoundException() {
//        // Arrange
//        long eventId = 1L;
//        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(ObjectNotFoundException.class, () -> eventService.getById(eventId));
//    }
//
//    @Test
//    void removeById_WhenEventExists_RemovesEvent() {
//        // Arrange
//        long eventId = 1L;
//
//        // Act
//        eventService.removeById(eventId);
//
//        // Assert
//        verify(eventRepository, times(1)).deleteById(eventId);
//    }
//
//    @Test
//    void saveEventByTitle_ReturnsSavedEventEntity() {
//        // Arrange
//        String eventTitle = "New Event";
//        EventTitleDto eventTitleDto = new EventTitleDto(eventTitle);
//        EventEntity eventEntity = new EventEntity(1L, "", "Test Event",
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//        eventEntity.setTitle(eventTitle);
//
//        when(eventRepository.save(any())).thenReturn(eventEntity);
//
//        // Act
//        EventEntity savedEvent = eventService.saveEventByTitle(eventTitleDto);
//
//        // Assert
//        assertNotNull(savedEvent);
//        assertEquals(eventEntity.getId(), savedEvent.getId());
//        assertEquals(eventEntity.getTitle(), savedEvent.getTitle());
//    }
//
//
//    @Test
//    void updateById_ReturnsUpdatedEventTitleDto() {
//        // Arrange
//        long id = 1L;
//        String newTitle = "Updated Event Title";
//        EventTitleDto updatedTitleDto = new EventTitleDto(newTitle);
//        EventEntity eventEntity = new EventEntity(id, "inviteCode", "Original Title",
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//        EventTitleDto expectedDto = new EventTitleDto(newTitle);
//        eventEntity.setTitle(newTitle);
//        when(eventRepository.findById(id)).thenReturn(Optional.of(eventEntity));
//
//        // Act
//        EventTitleDto resultDto = eventService.updateById(id, updatedTitleDto);
//
//        // Assert
//        assertNotNull(resultDto);
//        assertEquals(expectedDto.getTitle(), resultDto.getTitle());
//    }
//
//    @Test
//    void updateById_ThrowsObjectNotFoundException() {
//        // Arrange
//        long id = 1L;
//        when(eventRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(ObjectNotFoundException.class, () -> eventService.updateById(id, new EventTitleDto("New Title")));
//    }
//
//    @Test
//    void saveEventByTitle_ReturnsCreatedEventEntity() {
//        // Arrange
//        String title = "New Event Title";
//        EventTitleDto eventTitleDto = new EventTitleDto(title);
//        EventEntity expectedEntity = new EventEntity(1L, "inviteCode", title,
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//        when(eventRepository.save(any())).thenReturn(expectedEntity);
//
//        // Act
//        EventEntity resultEntity = eventService.saveEventByTitle(eventTitleDto);
//
//        // Assert
//        assertNotNull(resultEntity);
//        assertEquals(expectedEntity.getTitle(), resultEntity.getTitle());
//    }
//
//    @Test
//    void createEvent_ReturnsCreatedEventEntity() {
//        // Arrange
//        String title = "New Event Title";
//        EventEntity expectedEntity = new EventEntity(1L, "inviteCode", title,
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//        when(eventRepository.save(any(EventEntity.class))).thenReturn(expectedEntity);
//
//        // Act
//        EventDetailsDto resultEntity = eventService.createEvent(new CreatorToTitleDto().setTitle("").setFirstName("")
//                .setLastName("")
//                .setEmail(""));
//
//        // Assert
//        assertNotNull(resultEntity);
//        assertEquals(expectedEntity.getTitle(), resultEntity.getTitle());
//    }
//
//    @Test
//    void getAllEvents_ReturnsListOfEventOverviewDto() {
//        // Arrange
//        List<EventEntity> eventEntities = Arrays.asList(
//                new EventEntity(1L, "inviteCode1", "Event 1", new HashSet<>(), new HashSet<>(), new Date(), new Date()),
//                new EventEntity(2L, "inviteCode2", "Event 2", new HashSet<>(), new HashSet<>(), new Date(), new Date())
//        );
//        List<EventOverviewDto> expectedDtos = Arrays.asList(
//                new EventOverviewDto(1L, "Event 1", "inviteCode1", new Date(), new Date()),
//                new EventOverviewDto(2L, "Event 2", "inviteCode2", new Date(), new Date())
//        );
//        when(eventRepository.findAll()).thenReturn(eventEntities);
//
//        // Act
//        List<EventOverviewDto> resultDtos = eventService.getAllEvents();
//
//        // Assert
//        assertNotNull(resultDtos);
//        assertEquals(expectedDtos.size(), resultDtos.size());
//        for (int i = 0; i < expectedDtos.size(); i++) {
//            EventOverviewDto expectedDto = expectedDtos.get(i);
//            EventOverviewDto resultDto = resultDtos.get(i);
//            assertEquals(expectedDto.getId(), resultDto.getId());
//            assertEquals(expectedDto.getTitle(), resultDto.getTitle());
//            assertEquals(expectedDto.getInviteCode(), resultDto.getInviteCode());
//        }
//    }
//
//    @Test
//    void getEventParticipants_ReturnsListOfUserNameDto() {
//        // Arrange
//        long eventId = 1L;
//        List<ParticipantEntity> userEntities = Arrays.asList(
//                new ParticipantEntity(1L, "John", "Doe", "john@example.com", new EventEntity(), new BankAccountEntity()),
//                new ParticipantEntity(2L, "Alice", "Smith", "alice@example.com", new EventEntity(), new BankAccountEntity())
//        );
//        List<ParticipantNameDto> expectedDtos = Arrays.asList(
//                new ParticipantNameDto(1L, "John", "Doe", ""),
//                new ParticipantNameDto(2L, "Alice", "Smith", "")
//        );
//        when(eventRepository.findEventParticipants(eventId)).thenReturn(new HashSet<>(userEntities));
//
//        // Act
//        List<ParticipantNameDto> resultDtos = eventService.getEventParticipants(eventId);
//
//        // Assert
//        assertNotNull(resultDtos);
//        assertEquals(expectedDtos.size(), resultDtos.size());
//        for (ParticipantNameDto expectedDto : expectedDtos) {
//            assertTrue(resultDtos.contains(expectedDto));
//        }
//    }
//
//    @Test
//    void findEntityById_ReturnsEventEntity() {
//        // Arrange
//        long eventId = 1L;
//        EventEntity expectedEntity = new EventEntity(eventId, "inviteCode", "Test Event",
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//        when(eventRepository.findById(eventId)).thenReturn(Optional.of(expectedEntity));
//
//        // Act
//        EventEntity resultEntity = eventService.findEntityById(eventId);
//
//        // Assert
//        assertNotNull(resultEntity);
//        assertEquals(expectedEntity.getTitle(), resultEntity.getTitle());
//    }
//
//    @Test
//    void findEntityById_ThrowsObjectNotFoundException() {
//        // Arrange
//        long eventId = 1L;
//        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(ObjectNotFoundException.class, () -> eventService.findEntityById(eventId));
//    }

}
