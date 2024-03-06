package server.controller.api;

import commons.UserEntity;
import commons.dto.view.UserNameDto;
import jakarta.validation.Valid;
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
     * Create a new user
     *
     * @param userDto the user data
     * @return ResponseEntity with the created user data
     */
    @PostMapping("/")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserNameDto userDto) {
        UserEntity userEntity = userService.saveUserByID(userDto);
        return ResponseEntity.ok(userEntity);
    }

}
