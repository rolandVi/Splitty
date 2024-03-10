package server.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.PasswordService;
import commons.exceptions.PasswordExpiredException;

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
    public ResponseEntity<String> validatePassword(@RequestBody String p){
        try {
            if (passwordService.validatePassword(p)){
                return ResponseEntity.ok("Password is valid.");
            }else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Password is invalid.");
            }
        }catch (PasswordExpiredException e){
            return ResponseEntity.badRequest().body(e.getMessage());
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
