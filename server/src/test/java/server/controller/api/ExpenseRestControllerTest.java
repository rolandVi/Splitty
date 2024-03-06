package server.controller.api;

import commons.dto.view.UserNameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import commons.dto.view.ExpenseDetailsDto;
import server.service.ExpenseService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseRestControllerTest {

    @Mock
    private ExpenseService expenseService;

    private ExpenseRestController expenseRestController;

    private ExpenseDetailsDto expectedDto;
    private ExpenseDetailsDto requestDto;
    private Double expectedTotalSum;
    private List<ExpenseDetailsDto> expectedExpenses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        expenseRestController = new ExpenseRestController(expenseService);

        expectedDto = new ExpenseDetailsDto(
                1L,
                100.0,
                new UserNameDto(),
                "Expense Title",
                new HashSet<>(Collections.singletonList(new UserNameDto())),
                new Date()
        );

        requestDto = new ExpenseDetailsDto(
                2L,
                150.0,
                new UserNameDto(),
                "New Expense Title",
                new HashSet<>(Collections.singletonList(new UserNameDto())), // Example debtors
                new Date()
        );

        expectedTotalSum = 100.0;
        expectedExpenses = new ArrayList<>();
    }


    @Test
    void testGetExpenseById() {
        // Arrange
        long expenseId = 1L;
        when(expenseService.getById(expenseId)).thenReturn(expectedDto);

        // Act
        ResponseEntity<ExpenseDetailsDto> response = expenseRestController.getExpenseById(expenseId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(expenseService, times(1)).getById(expenseId);
    }

    @Test
    void testCreateExpense() {
        // Arrange
        when(expenseService.createExpense(requestDto)).thenReturn(expectedDto);

        // Act
        ResponseEntity<ExpenseDetailsDto> response = expenseRestController.createExpense(requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(expenseService, times(1)).createExpense(requestDto);
    }

    @Test
    void testUpdateExpense() {
        // Arrange
        long expenseId = 1L;
        requestDto.setId(expenseId);
        when(expenseService.updateExpense(requestDto)).thenReturn(expectedDto);

        // Act
        ResponseEntity<ExpenseDetailsDto> response = expenseRestController.updateExpense(expenseId, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(expenseService, times(1)).updateExpense(requestDto);
    }

    @Test
    void testRemoveById() {
        // Arrange
        long validId = 1L;
        when(expenseService.existsById(validId)).thenReturn(true);

        // Act
        ResponseEntity<Void> responseValidId = expenseRestController.removeById(validId);

        // Assert
        assertEquals(HttpStatus.OK, responseValidId.getStatusCode());
        verify(expenseService, times(1)).removeById(validId);

        long invalidId = -1L;
        when(expenseService.existsById(invalidId)).thenReturn(false);

        // Act
        ResponseEntity<Void> responseInvalidId = expenseRestController.removeById(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseInvalidId.getStatusCode());
        verify(expenseService, never()).removeById(invalidId);
    }


    @Test
    void testGetAllExpenses() {
        // Arrange
        when(expenseService.getAllExpenses()).thenReturn(expectedExpenses);

        // Act
        ResponseEntity<List<ExpenseDetailsDto>> response = expenseRestController.getAllExpenses();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedExpenses, response.getBody());
        verify(expenseService, times(1)).getAllExpenses();
    }

    @Test
    void testGetTotalSumOfExpenses() {
        // Arrange
        when(expenseService.getTotalSumOfExpenses()).thenReturn(expectedTotalSum);

        // Act
        ResponseEntity<Double> response = expenseRestController.getTotalSumOfExpenses();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTotalSum, response.getBody());
        verify(expenseService, times(1)).getTotalSumOfExpenses();
    }

    @Test
    void testGetExpensesForUser() {
        // Arrange
        Long userId = 1L;
        when(expenseService.getExpensesForUser(userId)).thenReturn(expectedExpenses);

        // Act
        ResponseEntity<List<ExpenseDetailsDto>> response = expenseRestController.getExpensesForUser(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedExpenses, response.getBody());
        verify(expenseService, times(1)).getExpensesForUser(userId);
    }

    @Test
    void testGetExpenseDetails() {
        // Arrange
        Long expenseId = 1L;
        when(expenseService.getExpenseDetails(expenseId)).thenReturn(expectedDto);

        // Act
        ResponseEntity<ExpenseDetailsDto> response = expenseRestController.getExpenseDetails(expenseId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(expenseService, times(1)).getExpenseDetails(expenseId);
    }

}
