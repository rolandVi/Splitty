package server.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.controller.api.BankAccountRestController;
import server.dto.BankAccountCreationDto;
import server.dto.view.BankAccountDto;
import server.service.BankAccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BankAccountRestControllerTest {

    @Mock
    private BankAccountService bankAccountService;

    private BankAccountRestController bankAccountRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bankAccountRestController = new BankAccountRestController(bankAccountService);
    }

    @Test
    void createBankAccount() {
        // Arrange
        BankAccountCreationDto requestBody = new BankAccountCreationDto(/* Create your test DTO */);
        BankAccountDto expectedDto = new BankAccountDto(/* Create your expected DTO */);
        when(bankAccountService.createBankAccount(requestBody)).thenReturn(expectedDto);

        // Act
        ResponseEntity<BankAccountDto> response = bankAccountRestController.createBankAccount(requestBody);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(bankAccountService, times(1)).createBankAccount(requestBody);
    }
}
