package server.service;

import commons.exceptions.PasswordExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import server.config.BeanConfig;

import java.security.SecureRandom;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)

class PasswordServiceTest {

//    @Mock
//    SecureRandom random;

    PasswordService passwordService;

    @Autowired
    SecureRandom random;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        passwordService = new PasswordService(random);
    }


    @Test
    void generatePasswordIsNotEmpty() {
        assertEquals(Optional.empty(), passwordService.getPassword());
        passwordService.generatePassword();
        assertTrue(passwordService.getPassword().isPresent());
        assertEquals(16, passwordService.getPassword().get().length());
    }

    @Test
    void validatePasswordExpired() {
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