package server.controller.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.exception.NotFoundAdvice;
import server.exception.ObjectNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotFoundAdviceTest {

    private NotFoundAdvice notFoundAdvice;

    @BeforeEach
    void setUp() {
        notFoundAdvice = new NotFoundAdvice();
    }

    @Test
    void handleObjectNotFound() {
        // Arrange
        ObjectNotFoundException exception = new ObjectNotFoundException("Message");
        ResponseEntity<Object> expectedResponse = ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());

        // Act
        ResponseEntity<String> response = notFoundAdvice.handleObjectNotFound(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getHeaders(), response.getHeaders());
    }
}
