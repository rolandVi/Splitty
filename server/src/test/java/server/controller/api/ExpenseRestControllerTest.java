package server.controller.api;

import commons.EventEntity;
import commons.ExpenseEntity;
import commons.ParticipantEntity;
import commons.TagEntity;
import dto.ExpenseCreationDto;
import dto.view.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import dto.view.ExpenseDetailsDto;
import dto.view.ParticipantNameDto;
import server.exception.ObjectNotFoundException;
import server.service.EventService;
import server.service.ExpenseService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ExpenseRestControllerTest {

    @Mock
    private ExpenseService expenseService;
    @Mock
    private EventService eventService;

    private ExpenseRestController expenseRestController;

    private ExpenseDetailsDto detailsDto;
    private ExpenseCreationDto creationDto;
    private Double expectedTotalSum;
    private List<ExpenseDetailsDto> expectedExpenses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        expenseRestController = new ExpenseRestController(expenseService, eventService);

        detailsDto = new ExpenseDetailsDto(
                1L,
                100.0,
                new ParticipantNameDto(),
                "Expense Title",
                new HashSet<>(Collections.singletonList(new ParticipantNameDto())),
                new Date(), new TagDto()
        );

        creationDto = new ExpenseCreationDto(
                "title",
                150.0,
                1L,
                new HashSet<>(),
                1L,
                new Date(),
                new TagDto()
        );

        expectedTotalSum = 100.0;
        expectedExpenses = new ArrayList<>();
    }


    @Test
    void testGetExpenseById() {
        // Arrange
        long expenseId = 1L;
        when(expenseService.getById(expenseId)).thenReturn(detailsDto);

        // Act
        ResponseEntity<ExpenseDetailsDto> response = expenseRestController.getExpenseById(expenseId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(detailsDto, response.getBody());
        verify(expenseService, times(1)).getById(expenseId);
    }

    @Test
    void testGetExpenseByIdException() {
        // Arrange
        long expenseId = 1L;

        // Act
        ResponseEntity<ExpenseDetailsDto> response = expenseRestController.getExpenseById(expenseId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(expenseService, times(1)).getById(expenseId);
    }

    @Test
    void testCreateExpense() {
        // Arrange
        when(expenseService.createExpense(any())).thenReturn(new ExpenseEntity(1L, 100.0,
                new ParticipantEntity(),
                new HashSet<>(Set.of(new ParticipantEntity(1L, "", "", "",
                        null, null))),
                "Expense Title",
                new Date(),
                new EventEntity(),
                new TagEntity()));

        // Act
        ExpenseDetailsDto createdExpense = expenseRestController.createExpense(creationDto);

        // Assert
        assertNotNull(createdExpense);
        verify(expenseService, times(1)).createExpense(creationDto);
    }

    @Test
    void testUpdateExpense() {
        // Arrange
        when(expenseService.updateExpense(any())).thenReturn(detailsDto);
        when(expenseService.existsById(anyLong())).thenReturn(true);

        // Act
        ResponseEntity<ExpenseDetailsDto> response = expenseRestController.updateExpense(detailsDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(detailsDto, response.getBody());
        verify(expenseService, times(1)).updateExpense(detailsDto);
    }

    @Test
    void testUpdateExpenseException() {
        // Act
        ResponseEntity<ExpenseDetailsDto> response = expenseRestController.updateExpense(detailsDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testRemoveById() {
        // Arrange
        long expenseId = 1L;
        long eventId = 1L;
        when(expenseService.existsById(anyLong())).thenReturn(true);
        // Act
        ResponseEntity<Void> response = expenseRestController.removeById(expenseId, eventId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService, times(1)).removeExpense(eventId, expenseService.getEntityById(expenseId));
        verify(expenseService, times(1)).deleteExpense(expenseId);
    }

    @Test
    void testRemoveByIdBadRequest() {
        // Arrange
        long expenseId = 1L;
        long eventId = 1L;
        // Act
        ResponseEntity<Void> response = expenseRestController.removeById(expenseId, eventId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testRemoveByIdNotFound() {
        // Arrange
        long expenseId = 1L;
        long eventId = 1L;
        when(expenseService.existsById(anyLong())).thenReturn(true);
        when(expenseService.getEntityById(anyLong())).thenThrow(ObjectNotFoundException.class);
        // Act
        ResponseEntity<Void> response = expenseRestController.removeById(expenseId, eventId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
        when(expenseService.getById(expenseId)).thenReturn(detailsDto);

        // Act
        ResponseEntity<ExpenseDetailsDto> response = expenseRestController.getExpenseById(expenseId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(detailsDto, response.getBody());
        verify(expenseService, times(1)).getById(expenseId);
    }

}
