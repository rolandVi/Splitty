package server.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
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
}
