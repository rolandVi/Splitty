package server.controller.api;

import commons.UserEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.CreatorToTitleDto;
import dto.UserCreationDto;
import dto.view.EventOverviewDto;
import dto.view.EventTitleDto;
import dto.view.UserNameDto;
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
     * @return the newly created user and id
     */
    @PostMapping("/")
    public ResponseEntity<UserNameDto> createUser(@Valid @RequestBody UserCreationDto user) {
        if (this.userService.emailExists(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
     * Endpoint ot check if user credentials are valid
     * @param user the user credentials
     * @return status code ok if they are valid
     */
    @PostMapping("/check")
    public ResponseEntity<Void> checkUserCredentialsValidity(
            @Valid @RequestBody UserCreationDto user){
        return ResponseEntity.ok().build();
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
}
