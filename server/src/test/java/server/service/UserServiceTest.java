package server.service;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import server.controller.exception.ObjectNotFoundException;
import server.dto.UserCreationDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.EventTitleDto;
import server.dto.view.UserNameDto;
import server.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventService eventService;

    private UserService userService;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        ModelMapper mapper=new ModelMapper();
        this.userService=new UserService(mapper,userRepository, eventService);
    }

    @Test
    void existsById_ShouldExist(){
        long id=1L;
        when(userRepository.existsById(id)).thenReturn(true);

        assertTrue(this.userService.existsById(id));
    }

    @Test
    void existsById_ShouldNotExist(){
        long id=1L;
        when(userRepository.existsById(id)).thenReturn(false);

        assertFalse(this.userService.existsById(id));
    }

    @Test
    void findById_ReturnsUser(){
        // Arrange
        long id = 1L;
        UserEntity user = new UserEntity(id, "vwv", "vwer", "vwer@fewr",
                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getEvents(), result.getEvents());
        assertEquals(user.getBankAccount(), result.getBankAccount());
    }

    @Test
    void getUserEvents(){
        long id = 1L;
        HashSet<EventEntity> events=new HashSet<>();
        for (long i = 0L; i < 5; i++) {
            events.add(new EventEntity(i, "invCode"+i, "title"+i, new HashSet<>(), new HashSet<>()));
        }
        UserEntity user = new UserEntity(id, "vwv", "vwer", "vwer@fewr",
                events, new BankAccountEntity("12345", "vwve", "1324"));

        when(this.userRepository.getEventsByUserId(id)).thenReturn(events);

        List<EventOverviewDto> modifiedEvents= events.stream()
                .map(e-> new EventOverviewDto()
                        .setId(e.getId())
                        .setTitle(e.getTitle())
                        .setInviteCode(e.getInviteCode()))
                .collect(Collectors.toList());

        assertEquals(modifiedEvents, this.userService.getUserEvents(id));
    }



    @Test
    void createUser_ReturnsUserNameDto() {
        // Arrange
        UserCreationDto userCreationDto = new UserCreationDto("John", "Doe", "john@example.com");
        UserEntity savedUserEntity = new UserEntity(1L, "John", "Doe", "john@example.com",
                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);

        // Act
        UserNameDto createdUser = userService.createUser(userCreationDto);

        // Assert
        assertNotNull(createdUser);
        assertEquals(savedUserEntity.getFirstName(), createdUser.getFirstName());
        assertEquals(savedUserEntity.getLastName(), createdUser.getLastName());
    }

    @Test
    void createEvent_ReturnsCreatedEventTitleDto() {
        // Arrange
        String eventTitle = "New Event";
        EventEntity eventEntity = new EventEntity(1L, "", eventTitle,
                new HashSet<>(), new HashSet<>());
        UserEntity user= new UserEntity(1L, "first","last", "email@gmai.com",
                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
        eventEntity.setTitle(eventTitle);

        when(eventService.createEvent(any())).thenReturn(eventEntity);
        when(eventService.findEntityByInviteCode(eventEntity.getInviteCode())).thenReturn(eventEntity);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        EventTitleDto createdEvent = userService.createEvent(eventTitle, 1L);

        // Assert
        assertNotNull(createdEvent);
        assertEquals(eventEntity.getId(), createdEvent.getId());
        assertEquals(eventEntity.getTitle(), createdEvent.getTitle());
    }

    @Test
    void leave_WhenEventAndUserExist_LeavesEvent() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        EventEntity eventEntity = new EventEntity(eventId, "inviteCode", "Test Event",
                new HashSet<>(), new HashSet<>());
        UserEntity userEntity = new UserEntity(userId, "John", "Doe", "john@example.com",
                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));

        when(eventService.findEntityById(eventId)).thenReturn(eventEntity);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        // Act
        boolean left = userService.leave(eventId, userId);

        // Assert
        assertTrue(left);
        assertFalse(eventEntity.getParticipants().contains(userEntity));
        assertFalse(userEntity.getEvents().contains(eventEntity));
        verify(userRepository, times(1)).saveAndFlush(userEntity);
    }

    @Test
    void leave_WhenEventDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        when(eventService.findEntityById(eventId)).thenThrow(ObjectNotFoundException.class);

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.leave(eventId, userId));

        // Verify
        verify(userRepository, never()).saveAndFlush(any());
    }

    @Test
    void leave_WhenUserDoesNotExist_ThrowsIllegalArgumentException() {
        // Arrange
        long eventId = 1L;
        long userId = 2L;
        EventEntity eventEntity = new EventEntity(eventId, "inviteCode", "Test Event",
                new HashSet<>(), new HashSet<>());

        when(eventService.findEntityById(eventId)).thenReturn(eventEntity);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> userService.leave(eventId, userId));

        // Verify
        verify(userRepository, never()).saveAndFlush(any());
    }

    @Test
    void join_WhenUserAndEventExist_JoinsEvent() {
        // Arrange
        long userId = 1L;
        String inviteCode = "abc123";
        UserEntity userEntity = new UserEntity(userId, "John", "Doe", "john@example.com",
                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
        EventEntity eventEntity = new EventEntity(1L, inviteCode, "Test Event",
                new HashSet<>(), new HashSet<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(eventService.findEntityByInviteCode(inviteCode)).thenReturn(eventEntity);

        // Act
        boolean joined = userService.join(inviteCode, userId);

        // Assert
        assertTrue(joined);
        assertTrue(eventEntity.getParticipants().contains(userEntity));
        assertTrue(userEntity.getEvents().contains(eventEntity));
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void join_WhenUserDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long userId = 1L;
        String inviteCode = "abc123";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.join(inviteCode, userId));

        // Verify
        verify(eventService, never()).findEntityByInviteCode(anyString());
    }

    @Test
    void join_WhenEventDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long userId = 1L;
        String inviteCode = "abc123";
        UserEntity userEntity = new UserEntity(userId, "John", "Doe", "john@example.com",
                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(eventService.findEntityByInviteCode(inviteCode)).thenThrow(ObjectNotFoundException.class);

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.join(inviteCode, userId));

        // Verify
        verify(userRepository, never()).save(any());
    }
}
