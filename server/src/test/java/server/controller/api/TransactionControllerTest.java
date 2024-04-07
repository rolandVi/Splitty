package server.controller.api;

import dto.TransactionCreationDto;
import dto.view.TransactionDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.service.TransactionService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    private TransactionController transactionController;
    private TransactionCreationDto transactionDto;
    private List<TransactionDetailsDto> mockTransactions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionController = new TransactionController(transactionService);

        // Initialize TransactionDetailsDto
        transactionDto = new TransactionCreationDto(1L, 2L, 3L);

        // Initialize mockTransactions
        mockTransactions = Arrays.asList(
                new TransactionDetailsDto()
                        .setExpenseTitle("Expense 1")
                        .setReceiverFirstName("Receiver 1")
                        .setReceiverLastName("Receiver 1 Last Name")
                        .setMoney(100.0)
                        .setDate("2022-01-01"),
                new TransactionDetailsDto()
                        .setExpenseTitle("Expense 2")
                        .setReceiverFirstName("Receiver 2")
                        .setReceiverLastName("Receiver 2 Last Name")
                        .setMoney(150.0)
                        .setDate("2022-02-01")
        );
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
        Long id = 1L;
        // Mock service method
        doNothing().when(transactionService).revertTransaction(id);

        // Call controller method
        ResponseEntity<Void> responseEntity = transactionController.revertTransaction(id);

        // Verify service method was called
        verify(transactionService, times(1)).revertTransaction(id);

        // Check response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getSentTransactions() {
        Long senderId = 1L;


        // Mock service method
        when(transactionService.findSentTransactions(senderId)).thenReturn(mockTransactions);

        // Call controller method
        ResponseEntity<List<TransactionDetailsDto>> responseEntity = transactionController.getSentTransactions(senderId);

        // Verify service method was called
        verify(transactionService, times(1)).findSentTransactions(senderId);

        // Check response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Check response body
        List<TransactionDetailsDto> responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(mockTransactions.size(), responseBody.size());
        assertEquals(mockTransactions.get(0).getExpenseTitle(), responseBody.get(0).getExpenseTitle());
        assertEquals(mockTransactions.get(1).getMoney(), responseBody.get(1).getMoney());
        // Add assertions for other fields as needed
    }

    @Test
    void getReceivedTransactions() {
        Long receiverId = 2L;


        // Mock service method
        when(transactionService.findReceivedTransactions(receiverId)).thenReturn(mockTransactions);

        // Call controller method
        ResponseEntity<List<TransactionDetailsDto>> responseEntity = transactionController.getReceivedTransactions(receiverId);

        // Verify service method was called
        verify(transactionService, times(1)).findReceivedTransactions(receiverId);

        // Check response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Check response body
        List<TransactionDetailsDto> responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(mockTransactions.size(), responseBody.size());
        assertEquals(mockTransactions.get(0).getExpenseTitle(), responseBody.get(0).getExpenseTitle());
        assertEquals(mockTransactions.get(1).getMoney(), responseBody.get(1).getMoney());
        // Add assertions for other fields as needed
    }
}
