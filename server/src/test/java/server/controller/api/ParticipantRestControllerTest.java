package server.controller.api;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.ParticipantEntity;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
import dto.view.ParticipantNameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.service.ParticipantService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class ParticipantRestControllerTest {

    @Mock
    private ParticipantService participantService;

    private ParticipantRestController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
         controller= new ParticipantRestController(participantService);
    }

    // Test for userExists endpoint
    @Test
    public void testUserExists_UserExists_ReturnsOk() {
        // Arrange
        ParticipantNameDto user = new ParticipantNameDto();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        when(participantService.existsById(anyLong())).thenReturn(true);
        when(participantService.findById(anyLong())).thenReturn(new ParticipantEntity(1L, "John", "Doe",
                "", new EventEntity(), new BankAccountEntity()));

        // Act
        ParticipantRestController controller = new ParticipantRestController(participantService);
        ResponseEntity<Void> response = controller.userExists(user);

        // Assert
        verify(participantService).existsById(1L);
        verify(participantService).findById(1L);
        assertSame(HttpStatus.OK, response.getStatusCode());
    }

    // Test for createBankAccount endpoint
    @Test
    public void testCreateBankAccount_ValidInput_ReturnsOk() {
        // Arrange
        BankAccountCreationDto bankAccountCreationDto = new BankAccountCreationDto();
        when(participantService.createBankAccount(any(), anyLong())).thenReturn(new BankAccountDto());

        // Act
        ParticipantRestController controller = new ParticipantRestController(participantService);
        ResponseEntity<BankAccountDto> response = controller.createBankAccount(bankAccountCreationDto, 1L);

        // Assert
        verify(participantService).createBankAccount(eq(bankAccountCreationDto), eq(1L));
        assertSame(HttpStatus.OK, response.getStatusCode());
    }

    // Test for findBankDetails endpoint
    @Test
    public void testFindBankDetails_UserExists_ReturnsOk() {
        // Arrange
        when(participantService.getBankAccount(anyLong())).thenReturn(new BankAccountDto());

        // Act
        ParticipantRestController controller = new ParticipantRestController(participantService);
        ResponseEntity<BankAccountDto> response = controller.findBankDetails(1L);

        // Assert
        verify(participantService).getBankAccount(eq(1L));
        assertSame(HttpStatus.OK, response.getStatusCode());
    }

    // Test for editBankAccount endpoint
    @Test
    public void testEditBankAccount_ValidInput_ReturnsOk() {
        // Arrange
        BankAccountCreationDto bankAccountCreationDto = new BankAccountCreationDto();
        when(participantService.editBankAccount(any(), anyLong())).thenReturn(new BankAccountDto());

        // Act
        ParticipantRestController controller = new ParticipantRestController(participantService);
        ResponseEntity<BankAccountDto> response = controller.editBankAccount(1L, bankAccountCreationDto);

        // Assert
        verify(participantService).editBankAccount(eq(bankAccountCreationDto), eq(1L));
        assertSame(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUserExists_UserNotFound_ReturnsNotFound() {
        // Arrange
        ParticipantNameDto user = new ParticipantNameDto();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        when(participantService.existsById(anyLong())).thenReturn(false);

        // Act
        ParticipantRestController controller = new ParticipantRestController(participantService);
        ResponseEntity<Void> response = controller.userExists(user);

        // Assert
        verify(participantService).existsById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
