package server.controller.api;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.dto.TransactionDetailsDto;
import server.service.TransactionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    private TransactionController transactionController;
    private TransactionDetailsDto transactionDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionController = new TransactionController(transactionService);

        // Initialize TransactionDetailsDto
        transactionDto = new TransactionDetailsDto(1L, 2L, 3L);
    }

    @Test
    void executeTransaction_Successful() {
        // Mock service method
        doNothing().when(transactionService).executeTransaction(transactionDto);

        // Call controller method
        ResponseEntity<Void> responseEntity = transactionController.executeTransaction(transactionDto);

        // Verify service method was called
        verify(transactionService, times(1)).executeTransaction(transactionDto);

        // Check response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    

    @Test
    void revertTransaction_Successful() {
        // Mock service method
        doNothing().when(transactionService).revertTransaction(transactionDto);

        // Call controller method
        ResponseEntity<Void> responseEntity = transactionController.revertTransaction(transactionDto);

        // Verify service method was called
        verify(transactionService, times(1)).revertTransaction(transactionDto);

        // Check response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
