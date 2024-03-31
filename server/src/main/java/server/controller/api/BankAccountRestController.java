package server.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
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
     * creates a new BankAccount with given parameters
     * @param bankAccountEntity bankAccount
     * @return newly created bankAccount + id
     */
    @PostMapping("/")
    public ResponseEntity<BankAccountDto> createBankAccount(
            @Valid @RequestBody BankAccountCreationDto bankAccountEntity) {
        if (this.bankAccountService.ibanExists(bankAccountEntity.getIban())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(this.bankAccountService.createBankAccount(bankAccountEntity));
    }

}
