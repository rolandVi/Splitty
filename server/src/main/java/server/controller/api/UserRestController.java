package server.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;


    public UserRestController(UserService userService) {
        this.userService = userService;
    }
}
