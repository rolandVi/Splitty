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
    @Test
    public void testDeleteParticipant_ParticipantExists_DeletesSuccessfully() {
        // Arrange
        Long participantId = 1L;

        // Act
        participantService.deleteParticipant(participantId);

        // Assert
        verify(participantRepository, times(1)).deleteById(participantId);
    }

    @Test
    public void testEditParticipant_ValidData_EditsParticipantSuccessfully() {
        // Arrange
        Long userId = 1L;
        ParticipantNameDto participantNameDto = new ParticipantNameDto(1L, "John", "Doe", "johndoe@example.com");
        ParticipantEntity mockParticipantEntity = new ParticipantEntity();
        when(participantRepository.findById(userId)).thenReturn(Optional.of(mockParticipantEntity));
        when(participantRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(), eq(ParticipantNameDto.class))).thenReturn(participantNameDto);

        // Act
        ParticipantNameDto result = participantService.editParticipant(participantNameDto, userId);

        // Assert
        assertNotNull(result);
        assertEquals(participantNameDto.getFirstName(), result.getFirstName());
        assertEquals(participantNameDto.getLastName(), result.getLastName());
        assertEquals(participantNameDto.getEmail(), result.getEmail());
        verify(participantRepository, times(1)).findById(userId);
        verify(participantRepository, times(1)).save(mockParticipantEntity);
    }
}