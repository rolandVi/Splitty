package server.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.UserCreationDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.UserNameDto;
import server.service.UserService;

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
     * Endpoint ot check if user credentials are valid
     * @param user the user credentials
     * @return status code ok if they are valid
     */
    @PostMapping("/check")
    public ResponseEntity<Void> checkUserCredentialsValidity(
            @Valid @RequestBody UserCreationDto user){
        return ResponseEntity.ok().build();
    }

}
