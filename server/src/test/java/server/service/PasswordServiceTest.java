package server.service;

import commons.exceptions.PasswordExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceTest {

    PasswordService passwordService;

    @BeforeEach
    public void setup(){
        passwordService = new PasswordService();
    }


    @Test
    void generatePassword() {
        assertEquals(Optional.empty(), passwordService.getPassword());
        passwordService.generatePassword();
        assertNotEquals(Optional.empty(), passwordService.getPassword());
    }

    @Test
    void validatePasswordEmpty() {
        assertThrows(PasswordExpiredException.class, () ->{
           passwordService.validatePassword("123");
        });
    }
    @Test
    void validatePasswordWrong() {
        passwordService.generatePassword();
        assertFalse(passwordService.validatePassword("123"));
    }
    @Test
    void validatePasswordCorrect() {
        passwordService.generatePassword();
        assertTrue(passwordService.validatePassword(passwordService.getPassword().get()));
    }
}