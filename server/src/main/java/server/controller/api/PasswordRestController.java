package server.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.controller.exception.PasswordExpiredException;
import server.service.PasswordService;

import server.service.PasswordService;

@RestController
@RequestMapping("/api/password")
public class PasswordRestController {

    private final PasswordService passwordService;

    /**
     * Constructor injection
     * @param passwordService the service for the password
     */
    public PasswordRestController(PasswordService passwordService){
        this.passwordService=passwordService;
    }

    /**
     * Validates the password and send back the information
     * @param p - password to validate
     * @return - whether the password is valid
     */
    @PostMapping("/validatePassword")
    public ResponseEntity<Boolean> validatePassword(@RequestBody String p){
        try {
            if (passwordService.validatePassword(p)){
                return ResponseEntity.ok(true);
            }else {
                return ResponseEntity.ok(false);
            }
        }catch (PasswordExpiredException e){
            return ResponseEntity.badRequest().build();
        }

    }

    /**
     * Prompts the passwordService instance to generate a new password
     */
    @PostMapping("/generatePassword")
    public void generatePassword(){
        passwordService.generatePassword();
    }

}
