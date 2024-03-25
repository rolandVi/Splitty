package server.service;

import commons.ExpenseEntity;
import commons.TransactionEntity;
import commons.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import server.controller.exception.ObjectNotFoundException;
import server.dto.TransactionDetailsDto;
import server.repository.TransactionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        // Initialize TransactionService with mocked dependencies
        transactionService = new TransactionService(
                mock(TransactionRepository.class),
                mock(ModelMapper.class),
                mock(UserService.class),
                mock(ExpenseService.class)
        );
    }

    @Test
    void executeTransaction_Successful() {
        // Mock TransactionDetailsDto
        TransactionDetailsDto transactionDto = new TransactionDetailsDto(1L, 2L, 3L);

        // Mock necessary methods for execution
        when(transactionService.expenseService.findExpenseEntityById(3L)).thenReturn(mock(ExpenseEntity.class));
        when(transactionService.userService.findById(1L)).thenReturn(mock(UserEntity.class));
        when(transactionService.userService.findById(2L)).thenReturn(mock(UserEntity.class));
        when(transactionService.expenseService.payDebt(any(), any(), any())).thenReturn(10.0);

        // Execute transaction
        assertDoesNotThrow(() -> transactionService.executeTransaction(transactionDto));
    }

    @Test
    void executeTransaction_InvalidExpenseId() {
        TransactionDetailsDto transactionDto = new TransactionDetailsDto(1L, 2L, 999L);

        // Mock necessary methods for execution
        when(transactionService.expenseService.findExpenseEntityById(999L)).thenThrow(ObjectNotFoundException.class);

        // Execute transaction
        assertThrows(ObjectNotFoundException.class, () -> transactionService.executeTransaction(transactionDto));
    }

    @Test
    void revertTransaction_Successful() {
        // Mock TransactionDetailsDto
        TransactionDetailsDto transactionDto = new TransactionDetailsDto(1L, 2L, 3L);

        // Mock necessary methods for reversion
        when(transactionService.transactionRepository.findById(any())).thenReturn(Optional.of(mock(TransactionEntity.class)));

        // Revert transaction
        assertDoesNotThrow(() -> transactionService.revertTransaction(transactionDto));
    }
}
