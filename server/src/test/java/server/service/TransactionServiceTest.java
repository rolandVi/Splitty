package server.service;

import commons.TransactionEntity;
import dto.TransactionCreationDto;
import dto.view.TransactionDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import server.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionService transactionService;
    private TransactionRepository transactionRepository;
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        ParticipantService userService = mock(ParticipantService.class);
        expenseService = mock(ExpenseService.class);

        transactionService = new TransactionService(transactionRepository, modelMapper, userService, expenseService);
    }

    @Test
    void executeTransaction() {
        TransactionCreationDto transactionDto = new TransactionCreationDto(1L, 2L, 3L);

        transactionService.executeTransaction(transactionDto);

        // Verify that expenseService.payDebt was called with the correct parameters
        verify(expenseService, times(1)).payDebt(any(), any(), any());

        // Verify that transactionRepository.save was called
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    void revertTransaction() {

        Long transactionId = 1L;
        when(transactionRepository.findById(transactionId))
                .thenReturn(java.util.Optional.of(new TransactionEntity()));

        transactionService.revertTransaction(transactionId);

        // Verify that transactionRepository.findById was called with the correct parameter
        verify(transactionRepository, times(1)).findById(transactionId);

        // Verify that expenseService.resetDebt was called with the correct parameters
        verify(expenseService, times(1)).resetDebt(any(), any(), any());

        // Verify that transactionRepository.delete was called with the correct parameter
        verify(transactionRepository, times(1)).delete(any());
    }

    @Test
    void findSentTransactions() {
        Long senderId = 1L;
        List<TransactionDetailsDto> expectedTransactions = new ArrayList<>();

        // Mock behavior of transactionRepository.findTransactionEntitiesBySenderId
        when(transactionRepository.findTransactionEntitiesBySenderId(senderId))
                .thenReturn(new HashSet<>());

        List<TransactionDetailsDto> result = transactionService.findSentTransactions(senderId);

        // Verify that transactionRepository.findTransactionEntitiesBySenderId was called with the correct parameter
        verify(transactionRepository, times(1)).findTransactionEntitiesBySenderId(senderId);

        // Verify that the result matches the expected transactions
        assertEquals(expectedTransactions, result);
    }

    @Test
    void findReceivedTransactions() {
        Long receiverId = 1L;
        List<TransactionDetailsDto> expectedTransactions = new ArrayList<>();

        // Mock behavior of transactionRepository.findTransactionEntitiesByReceiverId
        when(transactionRepository.findTransactionEntitiesByReceiverId(receiverId))
                .thenReturn(new HashSet<>());

        List<TransactionDetailsDto> result = transactionService.findReceivedTransactions(receiverId);

        // Verify that transactionRepository.findTransactionEntitiesByReceiverId was called with the correct parameter
        verify(transactionRepository, times(1)).findTransactionEntitiesByReceiverId(receiverId);

        // Verify that the result matches the expected transactions
        assertEquals(expectedTransactions, result);
    }
}
