package server.controller.api;

import commons.ParticipantEntity;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.view.ParticipantNameDto;
import server.service.ParticipantService;

@RestController
@RequestMapping("/api/users")
public class ParticipantRestController {

    private final ParticipantService participantService;


    /**
     *Constructor injection
     * @param userService the user service to inject
     */
    public ParticipantRestController(ParticipantService userService) {
        this.participantService = userService;
    }

    /**
     * Check for user existence
     * @param user User to check existence for
     * @return whether the user exists
     */
    @PostMapping("/exists")
    public ResponseEntity<Void> userExists(@RequestBody ParticipantNameDto user){
        if (!participantService.existsById(user.getId())){
            return ResponseEntity.notFound().build();
        }else {
            ParticipantEntity userEntity = participantService.findById(user.getId());
            if (user.getFirstName().equals(userEntity.getFirstName())
                    && user.getLastName().equals(userEntity.getLastName())){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    /**
     * Endpoint for creating a bank account
     * @param bankAccountCreationDto the bank account info
     * @param userId the user id
     * @return the newly created bank account information
     */
    @PostMapping("/{userId}/account")
    public ResponseEntity<BankAccountDto> createBankAccount(
            @Valid @RequestBody BankAccountCreationDto bankAccountCreationDto,
            @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(this.participantService
                .createBankAccount(bankAccountCreationDto, userId));
    }

    /**
     * Retrieves the bank details of a user
     * @param id the user id
     * @return the bank details
     */
    @GetMapping("/{userId}/account")
    public ResponseEntity<BankAccountDto> findBankDetails(
            @PathVariable(name = "userId") Long id){
        return ResponseEntity.ok(this.participantService
                .getBankAccount(id));
    }

    /**
     * Edits bank details
     * @param userId the user id
     * @param bankAccountCreationDto the new bank info
     * @return the new bank account
     */
    @PatchMapping("/{userId}/account")
    public ResponseEntity<BankAccountDto> editBankAccount(
            @PathVariable(name = "userId") Long userId,
            @Valid @RequestBody BankAccountCreationDto bankAccountCreationDto){
        return ResponseEntity.ok(this.participantService
                .editBankAccount(bankAccountCreationDto, userId));
    }
}
