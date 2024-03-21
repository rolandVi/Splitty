package server.controller.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

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
        ObjectNotFoundException exception = new ObjectNotFoundException();
        WebRequest request = null; // Mock web request
        ResponseEntity<Object> expectedResponse = ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Could not found such resource");

        // Act
        ResponseEntity<Object> response = notFoundAdvice.handleObjectNotFound(exception, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getHeaders(), response.getHeaders());
    }
}
