package server.controller.api;

import dto.exceptions.PasswordExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.service.PasswordService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PasswordRestControllerTest {

    @Mock
    PasswordService passwordService;

    private PasswordRestController passwordRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordRestController = new PasswordRestController(passwordService);
    }

    @Test
    void validatePasswordCorrect() {
        String password = "12345";
        when(passwordService.validatePassword(password)).thenReturn(true);

        ResponseEntity<String> response = passwordRestController.validatePassword(password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password is valid.", response.getBody());
        verify(passwordService, times(1)).validatePassword(password);
    }

    @Test
    void validatePasswordInvalid(){
        String password = "12345";
        when(passwordService.validatePassword(password)).thenReturn(false);

        ResponseEntity<String> response = passwordRestController.validatePassword(password);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Password is invalid.", response.getBody());
        verify(passwordService, times(1)).validatePassword(password);
    }

    @Test
    void validatePasswordExpired(){
        String password = "12345";
        when(passwordService.validatePassword(password)).thenThrow(new PasswordExpiredException("Last password has expired. Generate a new one."));

        ResponseEntity<String> response = passwordRestController.validatePassword(password);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Last password has expired. Generate a new one.", response.getBody());
        verify(passwordService, times(1)).validatePassword(password);
    }

    @Test
    void generatePassword() {
        passwordRestController.generatePassword();
        verify(passwordService, times(1)).generatePassword();
    }
}