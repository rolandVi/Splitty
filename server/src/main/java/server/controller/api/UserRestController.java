package server.controller.api;

import commons.dto.UserCreationDto;
import commons.dto.view.EventOverviewDto;
import commons.dto.view.UserNameDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

}
