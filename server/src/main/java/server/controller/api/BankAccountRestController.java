package server.controller.api;

import commons.dto.BankAccountCreationDto;
import commons.dto.view.BankAccountDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<BankAccountDto> createBankAccount(
            @Valid @RequestBody BankAccountCreationDto bankAccountEntity) {
        return ResponseEntity.ok(this.bankAccountService.createBankAccount(bankAccountEntity));
    }

}
