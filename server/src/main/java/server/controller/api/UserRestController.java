package server.controller.api;

import commons.UserEntity;
import commons.dto.view.UserNameDto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.UserService;

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
    public ResponseEntity<UserNameDto> createUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(this.userService.createUser(
                user.getFirstName(), user.getLastName(), user.getEmail()));
    }

}
