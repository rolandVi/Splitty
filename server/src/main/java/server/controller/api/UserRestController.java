package server.controller.api;

import commons.UserEntity;
import dto.BankAccountCreationDto;
import dto.view.BankAccountDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import dto.CreatorToTitleDto;
import dto.UserCreationDto;
import dto.view.EventOverviewDto;
import dto.view.EventTitleDto;
import dto.view.UserNameDto;
import server.exception.FieldValidationException;
import server.service.UserService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;


    /**
     *Constructor injection
     * @param userService the user service to inject
     */
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * creates a user with the given parameters
     * @param user user
     * @param result the validation result
     * @return the newly created user and id
     */
    @PostMapping("/")
    public ResponseEntity<UserNameDto> createUser(@Valid @RequestBody UserCreationDto user,
                                                  BindingResult result) {
        if (result.hasErrors()) {
            throw new FieldValidationException("Invalid user credentials");
        }
        return ResponseEntity.ok(this.userService.createUser(user));
    }

    /**
     * Get teh events of a user
     * @param id the id of the user
     * @return the events of this user
     */
    @GetMapping("/{id}/events")
    public ResponseEntity<List<EventOverviewDto>> getUserEvents(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(this.userService.getUserEvents(id));
    }

    /**
     * Get the ID of a user
     * @param email the email of the user
     * @return the ID of this user
     */
    @GetMapping("/{email}")
    public ResponseEntity<Long> getUserIDByEmail(@PathVariable(name = "email") String email){
        email = URLDecoder.decode(email, StandardCharsets.UTF_8);
        return ResponseEntity.ok(this.userService.findIdByEmail(email));
    }

    /**
     * Creates an event with the given title
     * @param creatorToTitleDto the title of the new event along with the id of the creator
     * @return the newly created event title and id
     */
    @PostMapping("/events")
    public ResponseEntity<EventTitleDto> createEvent(
            @Valid @RequestBody CreatorToTitleDto creatorToTitleDto){
        return ResponseEntity.ok(this.userService.createEvent(creatorToTitleDto.getTitle(),
                creatorToTitleDto.getId()));
    }

    /**
     * Adding a participant to an event
     * @param inviteCode the event invite code
     * @param userId the user id
     * @return whether the operation was successful
     */
    @PostMapping("/events/{invite}")
    public ResponseEntity<Void> joinEvent(@PathVariable(name = "invite") String inviteCode,
                                               @RequestBody long userId){
        this.userService.join(inviteCode, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete a participant
     * @param eventId the event id
     * @param userId the participant id
     * @return whether the request was successful
     */
    @DeleteMapping("/{userId}/events/{eventId}")
    public ResponseEntity<Void> leaveEvent(@PathVariable(name = "eventId") long eventId,
                                                  @PathVariable(name = "userId") long userId) {
        this.userService.leave(eventId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Check for user existence
     * @param user User to check existence for
     * @return whether the user exists
     */
    @PostMapping("/exists")
    public ResponseEntity<Void> userExists(@RequestBody UserNameDto user){
        if (!userService.existsById(user.getId())){
            return ResponseEntity.notFound().build();
        }else {
            UserEntity userEntity = userService.findById(user.getId());
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
        return ResponseEntity.ok(this.userService
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
        return ResponseEntity.ok(this.userService
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
        return ResponseEntity.ok(this.userService
                .editBankAccount(bankAccountCreationDto, userId));
    }
}
