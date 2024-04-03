package server.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.service.BankAccountService;

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
