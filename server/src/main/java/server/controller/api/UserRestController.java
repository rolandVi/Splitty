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
     * createse a user with the given parameters
     * @param iban iban
     * @param holder email of user
     * @param bic bic
     * @param firstName firstname
     * @param lastName lastname
     * @param email email
     * @return the newly created user and id
     */
    @PostMapping("/")
    public ResponseEntity<UserNameDto> createUser(@Valid @RequestBody String iban, String holder,
                                                  String bic, String firstName, String lastName,
                                                  String email) {

        return ResponseEntity.ok(this.userService.createUser(iban, holder, bic,
                firstName, lastName, email));
    }

}
