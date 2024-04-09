package server.service;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.ParticipantEntity;
import dto.BankAccountCreationDto;
import dto.ParticipantCreationDto;
import dto.view.BankAccountDto;
import dto.view.ParticipantNameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import server.exception.ObjectNotFoundException;
import server.repository.BankAccountRepository;
import server.repository.ParticipantRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParticipantServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private BankAccountService bankAccountService;

    private ParticipantService participantService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        ModelMapper mapper=new ModelMapper();
        this.participantService=new ParticipantService(mapper,participantRepository, bankAccountService);
    }


    // Test for existsById method
    @Test
    public void testExistsById_ExistingId_ReturnsTrue() {
        // Arrange
        long existingId = 1L;
        when(participantRepository.existsById(existingId)).thenReturn(true);

        // Act
        boolean result = participantService.existsById(existingId);

        // Assert
        assertTrue(result);
        verify(participantRepository, times(1)).existsById(existingId);
    }

    // Test for existsById method when ID does not exist
    @Test
    public void testExistsById_NonExistingId_ReturnsFalse() {
        // Arrange
        long nonExistingId = 100L;
        when(participantRepository.existsById(nonExistingId)).thenReturn(false);

        // Act
        boolean result = participantService.existsById(nonExistingId);

        // Assert
        assertFalse(result);
        verify(participantRepository, times(1)).existsById(nonExistingId);
    }

    // Test for findById method
    @Test
    public void testFindById_ExistingId_ReturnsParticipantEntity() {
        // Arrange
        long existingId = 1L;
        ParticipantEntity mockParticipantEntity = new ParticipantEntity();
        when(participantRepository.findById(existingId)).thenReturn(Optional.of(mockParticipantEntity));

        // Act
        ParticipantEntity result = participantService.findById(existingId);

        // Assert
        assertNotNull(result);
        assertSame(mockParticipantEntity, result);
        verify(participantRepository, times(1)).findById(existingId);
    }

    // Test for findById method when ID does not exist
    @Test
    public void testFindById_NonExistingId_ThrowsException() {
        // Arrange
        long nonExistingId = 100L;
        when(participantRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> participantService.findById(nonExistingId));
        verify(participantRepository, times(1)).findById(nonExistingId);
    }

    // Test for createBankAccount method
    @Test
    public void testCreateBankAccount_ValidData_CreatesBankAccountSuccessfully() {
        // Arrange
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        BankAccountCreationDto bankAccountCreationDto = new BankAccountCreationDto();
        bankAccountEntity.setHolder("nullnull");
        Long userId = 1L;
        ParticipantEntity mockParticipantEntity = new ParticipantEntity();
        BankAccountEntity mockBankAccountEntity = new BankAccountEntity();
        when(participantRepository.findById(userId)).thenReturn(Optional.of(mockParticipantEntity));
        when(bankAccountService.createBankAccount(bankAccountEntity)).thenReturn(mockBankAccountEntity);
//        when(bankAccountRepository.save(newEntity)).thenReturn(mockBankAccountEntity);
        when(modelMapper.map(mockBankAccountEntity, BankAccountDto.class)).thenReturn(new BankAccountDto());

        // Act
        BankAccountDto result = participantService.createBankAccount(bankAccountCreationDto, userId);

        // Assert
        assertNotNull(result);
        verify(participantRepository, times(1)).findById(userId);
        verify(bankAccountService, times(1)).createBankAccount(bankAccountEntity);
        verify(participantRepository, times(1)).save(mockParticipantEntity);
    }

    // Test for createBankAccount method when participant ID does not exist
    @Test
    public void testCreateBankAccount_NonExistingParticipantId_ThrowsException() {
        // Arrange
        BankAccountCreationDto bankAccountCreationDto = new BankAccountCreationDto();
        Long nonExistingUserId = 100L;
        when(participantRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> participantService.createBankAccount(bankAccountCreationDto, nonExistingUserId));
        verify(participantRepository, times(1)).findById(nonExistingUserId);
        verifyNoMoreInteractions(participantRepository);
    }

    // Test for getBankAccount method
    @Test
    public void testGetBankAccount_ExistingUserId_ReturnsBankAccountDto() {
        // Arrange
        Long userId = 1L;
        ParticipantEntity mockParticipantEntity = new ParticipantEntity();
        BankAccountEntity mockBankAccountEntity = new BankAccountEntity();
        mockParticipantEntity.setBankAccount(mockBankAccountEntity);
        when(participantRepository.findById(userId)).thenReturn(Optional.of(mockParticipantEntity));
        when(modelMapper.map(mockBankAccountEntity, BankAccountDto.class)).thenReturn(new BankAccountDto());

        // Act
        BankAccountDto result = participantService.getBankAccount(userId);

        // Assert
        assertNotNull(result);
        verify(participantRepository, times(1)).findById(userId);
    }

    // Test for getBankAccount method when participant does not have bank account
    @Test
    public void testGetBankAccount_ParticipantDoesNotHaveBankAccount_ReturnsEmptyBankAccountDto() {
        // Arrange
        Long userId = 1L;
        ParticipantEntity mockParticipantEntity = new ParticipantEntity();
        when(participantRepository.findById(userId)).thenReturn(Optional.of(mockParticipantEntity));

        // Act
        BankAccountDto result = participantService.getBankAccount(userId);

        // Assert
        assertNotNull(result);
        assertNull(result.getIban());
        assertNull(result.getBic());
        verify(participantRepository, times(1)).findById(userId);
        verifyNoInteractions(modelMapper);
    }

    // Test for getBankAccount method when participant does not exist
    @Test
    public void testGetBankAccount_ParticipantDoesNotExist_ThrowsException() {
        // Arrange
        Long nonExistingUserId = 100L;
        when(participantRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> participantService.getBankAccount(nonExistingUserId));
        verify(participantRepository, times(1)).findById(nonExistingUserId);
        verifyNoInteractions(modelMapper);
    }

    // Test for editBankAccount method
    @Test
    public void testEditBankAccount_ValidData_EditsBankAccountSuccessfully() {
        // Arrange
        BankAccountCreationDto bankAccountCreationDto = new BankAccountCreationDto();
        Long userId = 1L;
        ParticipantEntity mockParticipantEntity = new ParticipantEntity();
        BankAccountEntity mockBankAccountEntity = new BankAccountEntity();
        mockParticipantEntity.setBankAccount(mockBankAccountEntity);
        when(participantRepository.findById(userId)).thenReturn(Optional.of(mockParticipantEntity));
        when(bankAccountService.editBankAccount(mockBankAccountEntity, bankAccountCreationDto)).thenReturn(mockBankAccountEntity);
        when(modelMapper.map(mockBankAccountEntity, BankAccountDto.class)).thenReturn(new BankAccountDto());

        // Act
        BankAccountDto result = participantService.editBankAccount(bankAccountCreationDto, userId);

        // Assert
        assertNotNull(result);
        verify(participantRepository, times(1)).findById(userId);
        verify(bankAccountService, times(1)).editBankAccount(mockBankAccountEntity, bankAccountCreationDto);
        verify(participantRepository, times(1)).save(mockParticipantEntity);
    }

    // Test for editBankAccount method when participant ID does not exist
    @Test
    public void testEditBankAccount_NonExistingParticipantId_ThrowsException() {
        // Arrange
        BankAccountCreationDto bankAccountCreationDto = new BankAccountCreationDto();
        Long nonExistingUserId = 100L;
        when(participantRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> participantService.editBankAccount(bankAccountCreationDto, nonExistingUserId));
        verify(participantRepository, times(1)).findById(nonExistingUserId);
        verifyNoInteractions(bankAccountService);
        verifyNoMoreInteractions(participantRepository);
    }

    @Test
    public void testCreateParticipant_ValidData_CreatesParticipantSuccessfully() {
        // Arrange
        ParticipantCreationDto participantCreationDto = new ParticipantCreationDto();
        EventEntity mockEventEntity = new EventEntity();
        ParticipantEntity mockParticipantEntity = new ParticipantEntity();
        when(participantRepository.save(any())).thenReturn(mockParticipantEntity);
        when(modelMapper.map(mockParticipantEntity, ParticipantNameDto.class)).thenReturn(new ParticipantNameDto());

        // Act
        ParticipantNameDto result = participantService.createParticipant(participantCreationDto, mockEventEntity);

        // Assert
        assertNotNull(result);
        verify(participantRepository, times(1)).save(any());
    }

//    @Mock
//    private ParticipantRepository userRepository;
//
//    @Mock
//    private EventService eventService;
//
//    private ParticipantService userService;
//
//    @Mock
//    private BankAccountService bankAccountService;
//
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//        ModelMapper mapper=new ModelMapper();
//        this.userService=new ParticipantService(mapper,userRepository, bankAccountService);
//    }
//
//    @Test
//    void existsById_ShouldExist(){
//        long id=1L;
//        when(userRepository.existsById(id)).thenReturn(true);
//
//        assertTrue(this.userService.existsById(id));
//    }
//
//    @Test
//    void existsById_ShouldNotExist(){
//        long id=1L;
//        when(userRepository.existsById(id)).thenReturn(false);
//
//        assertFalse(this.userService.existsById(id));
//    }
//
//    @Test
//    void findById_ReturnsUser(){
//        // Arrange
//        long id = 1L;
//        ParticipantEntity user = new ParticipantEntity(id, "vwv", "vwer", "vwer@fewr",
//                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
//        when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//        // Act
//        ParticipantEntity result = userService.findById(id);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(user.getId(), result.getId());
//        assertEquals(user.getFirstName(), result.getFirstName());
//        assertEquals(user.getLastName(), result.getLastName());
//        assertEquals(user.getEmail(), result.getEmail());
//        assertEquals(user.getEvents(), result.getEvents());
//        assertEquals(user.getBankAccount(), result.getBankAccount());
//    }
//
//    @Test
//    void getUserEvents(){
//        long id = 1L;
//        HashSet<EventEntity> events=new HashSet<>();
//        for (long i = 0L; i < 5; i++) {
//            events.add(new EventEntity(i, "invCode"+i, "title"+i,
//                    new HashSet<>(), new HashSet<>(), new Date(), new Date()));
//        }
//        ParticipantEntity user = new ParticipantEntity(id, "vwv", "vwer", "vwer@fewr",
//                events, new BankAccountEntity("12345", "vwve", "1324"));
//
//        when(this.userRepository.getEventByUserId(id)).thenReturn(events);
//
//        List<EventOverviewDto> modifiedEvents= events.stream()
//                .map(e-> new EventOverviewDto()
//                        .setId(e.getId())
//                        .setTitle(e.getTitle())
//                        .setInviteCode(e.getInviteCode()))
//                .collect(Collectors.toList());
//
//        assertEquals(modifiedEvents, this.userService.getUserEvents(id));
//    }
//
//
//
//    @Test
//    void createUser_ReturnsUserNameDto() {
//        // Arrange
//        ParticipantCreationDto userCreationDto = new ParticipantCreationDto("John", "Doe", "john@example.com");
//        ParticipantEntity savedUserEntity = new ParticipantEntity(1L, "John", "Doe", "john@example.com",
//                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
//
//        when(userRepository.save(any(ParticipantEntity.class))).thenReturn(savedUserEntity);
//
//        // Act
//        ParticipantNameDto createdUser = userService.createUser(userCreationDto);
//
//        // Assert
//        assertNotNull(createdUser);
//        assertEquals(savedUserEntity.getFirstName(), createdUser.getFirstName());
//        assertEquals(savedUserEntity.getLastName(), createdUser.getLastName());
//    }
//
//    @Test
//    void createEvent_ReturnsCreatedEventTitleDto() {
//        // Arrange
//        String eventTitle = "New Event";
//        EventEntity eventEntity = new EventEntity(1L, "", eventTitle,
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//        ParticipantEntity user= new ParticipantEntity(1L, "first","last", "email@gmai.com",
//                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
//        eventEntity.setTitle(eventTitle);
//
//        when(eventService.createEvent(any())).thenReturn(eventEntity);
//        when(eventService.findEntityByInviteCode(eventEntity.getInviteCode())).thenReturn(eventEntity);
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//
//        // Act
//        EventTitleDto createdEvent = userService.createEvent(eventTitle, 1L);
//
//        // Assert
//        assertNotNull(createdEvent);
//        assertEquals(eventEntity.getId(), createdEvent.getId());
//        assertEquals(eventEntity.getTitle(), createdEvent.getTitle());
//    }
//
//    @Test
//    void leave_WhenEventAndUserExist_LeavesEvent() {
//        // Arrange
//        long eventId = 1L;
//        long userId = 2L;
//        EventEntity eventEntity = new EventEntity(eventId, "inviteCode", "Test Event",
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//        ParticipantEntity userEntity = new ParticipantEntity(userId, "John", "Doe", "john@example.com",
//                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
//
//        when(eventService.findEntityById(eventId)).thenReturn(eventEntity);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
//
//        // Act
//        boolean left = userService.leave(eventId, userId);
//
//        // Assert
//        assertTrue(left);
//        assertFalse(eventEntity.getParticipants().contains(userEntity));
//        assertFalse(userEntity.getEvents().contains(eventEntity));
//        verify(userRepository, times(1)).saveAndFlush(userEntity);
//    }
//
//    @Test
//    void leave_WhenEventDoesNotExist_ThrowsObjectNotFoundException() {
//        // Arrange
//        long eventId = 1L;
//        long userId = 2L;
//        when(eventService.findEntityById(eventId)).thenThrow(ObjectNotFoundException.class);
//
//        // Act and Assert
//        assertThrows(ObjectNotFoundException.class, () -> userService.leave(eventId, userId));
//
//        // Verify
//        verify(userRepository, never()).saveAndFlush(any());
//    }
//
//    @Test
//    void leave_WhenUserDoesNotExist_ThrowsIllegalArgumentException() {
//        // Arrange
//        long eventId = 1L;
//        long userId = 2L;
//        EventEntity eventEntity = new EventEntity(eventId, "inviteCode", "Test Event",
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//
//        when(eventService.findEntityById(eventId)).thenReturn(eventEntity);
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(IllegalArgumentException.class, () -> userService.leave(eventId, userId));
//
//        // Verify
//        verify(userRepository, never()).saveAndFlush(any());
//    }
//
//    @Test
//    void join_WhenUserAndEventExist_JoinsEvent() {
//        // Arrange
//        long userId = 1L;
//        String inviteCode = "abc123";
//        ParticipantEntity userEntity = new ParticipantEntity(userId, "John", "Doe", "john@example.com",
//                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
//        EventEntity eventEntity = new EventEntity(1L, inviteCode, "Test Event",
//                new HashSet<>(), new HashSet<>(), new Date(), new Date());
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
//        when(eventService.findEntityByInviteCode(inviteCode)).thenReturn(eventEntity);
//
//        // Act
//        boolean joined = userService.join(inviteCode, userId);
//
//        // Assert
//        assertTrue(joined);
//        assertTrue(eventEntity.getParticipants().contains(userEntity));
//        assertTrue(userEntity.getEvents().contains(eventEntity));
//        verify(userRepository, times(1)).save(userEntity);
//    }
//
//    @Test
//    void join_WhenUserDoesNotExist_ThrowsObjectNotFoundException() {
//        // Arrange
//        long userId = 1L;
//        String inviteCode = "abc123";
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(ObjectNotFoundException.class, () -> userService.join(inviteCode, userId));
//
//        // Verify
//        verify(eventService, never()).findEntityByInviteCode(anyString());
//    }
//
//    @Test
//    void join_WhenEventDoesNotExist_ThrowsObjectNotFoundException() {
//        // Arrange
//        long userId = 1L;
//        String inviteCode = "abc123";
//        ParticipantEntity userEntity = new ParticipantEntity(userId, "John", "Doe", "john@example.com",
//                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
//        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
//        when(eventService.findEntityByInviteCode(inviteCode)).thenThrow(ObjectNotFoundException.class);
//
//        // Act and Assert
//        assertThrows(ObjectNotFoundException.class, () -> userService.join(inviteCode, userId));
//
//        // Verify
//        verify(userRepository, never()).save(any());
//    }
//
//    @Test
//    void findIdByEmail() {
//        String email = "email@mail.com";
//        when(this.userRepository.getUserIdByUserEmail(email)).thenReturn(1L);
//        assertEquals(1L, userService.findIdByEmail(email));
//    }
}
