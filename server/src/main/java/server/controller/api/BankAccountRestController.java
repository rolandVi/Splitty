package server.controller.api;

import commons.BankAccountEntity;
import commons.dto.view.BankAccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.BankAccountService;

@RestController
@RequestMapping("/api/bankaccounts")
public class BankAccountRestController {

    private final BankAccountService bankAccountService;

    /**
     * Constructor injection
     * @param bankAccountService the service for the bankAccountEntity
     */
    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * creates a new BankAccount with given paramers
     * @param bankAccountEntity bankAccount
     * @return newly created bankAccount + id
     */
    @PostMapping("/")
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody
// Todo: make it such that @NotBlank will work and not throw an error when a request is made
        BankAccountEntity bankAccountEntity) {
        return ResponseEntity.ok(this.bankAccountService.createBankAccount(
                bankAccountEntity.getIban(),
                bankAccountEntity.getHolder(), bankAccountEntity.getBic()));
    }
}
