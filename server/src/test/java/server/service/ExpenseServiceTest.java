package server.service;

import commons.ExpenseEntity;
import commons.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import server.controller.exception.ObjectNotFoundException;
import server.dto.ExpenseCreationDto;
import server.dto.view.ExpenseDetailsDto;
import server.repository.ExpenseRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    private ExpenseService expenseService;

    private ExpenseEntity expectedDto;
    private List<ExpenseEntity> expectedExpenses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();

        expenseService = new ExpenseService(expenseRepository, modelMapper);

        expectedDto = new ExpenseEntity(1L, 100.0, new UserEntity(),
                new HashSet<>(), "Expense Title", new Date(), null);

        ExpenseEntity requestDto = new ExpenseEntity(2L, 200.0, new UserEntity(),
                new HashSet<>(), "Another Expense Title", new Date(), null);

        expectedExpenses = new ArrayList<>(Arrays.asList(expectedDto, requestDto));
    }

    @Test
    void existsById_WhenExpenseExists_ReturnsTrue() {
        // Arrange
        long expenseId = 1L;
        when(expenseRepository.existsById(expenseId)).thenReturn(true);

        // Act
        boolean result = expenseService.existsById(expenseId);

        // Assert
        assertTrue(result);
    }

    @Test
    void existsById_WhenExpenseDoesNotExist_ReturnsFalse() {
        // Arrange
        long expenseId = 1L;
        when(expenseRepository.existsById(expenseId)).thenReturn(false);

        // Act
        boolean result = expenseService.existsById(expenseId);

        // Assert
        assertFalse(result);
    }

    @Test
    void getById_WhenExpenseExists_ReturnsExpenseDetailsDto() {
        // Arrange
        long expenseId = 1L;
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expectedDto));

        // Act
        ExpenseDetailsDto result = expenseService.getById(expenseId);

        // Assert
        assertNotNull(result);
        assertEquals(mapExpenseEntityToDto(expectedDto), result);
    }




    @Test
    void getById_WhenExpenseDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long expenseId = 1L;
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> expenseService.getById(expenseId));
    }

    @Test
    void getAllExpenses_ReturnsListOfExpenseDetailsDto() {
        // Arrange
        when(expenseRepository.findAll()).thenReturn(expectedExpenses);

        // Act
        List<ExpenseDetailsDto> result = expenseService.getAllExpenses();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedExpenses.size(), result.size());
    }

    @Test
    void getTotalSumOfExpenses_ReturnsTotalSum() {
        // Arrange
        Double expectedTotalSum = 100.0;
        when(expenseRepository.getTotalSumOfExpenses()).thenReturn(expectedTotalSum);

        // Act
        Double result = expenseService.getTotalSumOfExpenses();

        // Assert
        assertNotNull(result);
        assertEquals(expectedTotalSum, result);
    }


    @Test
    void getExpensesForUser_ReturnsListOfExpenseDetailsDto() {
        // Arrange
        Long userId = 1L;
        when(expenseRepository.findExpensesByUserId(userId)).thenReturn(expectedExpenses);

        // Act
        List<ExpenseDetailsDto> result = expenseService.getExpensesForUser(userId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedExpenses.size(), result.size());
    }

    @Test
    void getExpenseDetails_WhenExpenseExists_ReturnsExpenseDetailsDto() {
        // Arrange
        long expenseId = 1L;
        ExpenseEntity expectedDto = new ExpenseEntity();
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expectedDto));

        // Act
        ExpenseDetailsDto result = expenseService.getById(expenseId);


        // Assert
        assertNotNull(result);
        assertEquals(mapExpenseEntityToDto(expectedDto), result);
    }

    @Test
    void getExpenseDetails_WhenExpenseDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long expenseId = 1L;
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> expenseService.getById(expenseId));
    }

    @Test
    void removeById_WhenExpenseExists_RemovesExpense() {
        // Arrange
        long expenseId = 1L;

        // Act
        expenseService.removeById(expenseId);

        // Assert
        verify(expenseRepository, times(1)).deleteById(expenseId);
    }

    @Test
    void updateExpense_WhenExpenseExists_ReturnsUpdatedExpenseDto() {
        // Arrange
        ExpenseDetailsDto expenseDto = new ExpenseDetailsDto();
        expenseDto.setId(1L);
        when(expenseRepository.existsById(expenseDto.getId())).thenReturn(true);

        // Act
        ExpenseDetailsDto result = expenseService.updateExpense(expenseDto);

        // Assert
        assertNotNull(result);
        assertEquals(expenseDto.getId(), result.getId());
    }

    @Test
    void updateExpense_WhenExpenseDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        ExpenseDetailsDto expenseDto = new ExpenseDetailsDto();
        expenseDto.setId(1L);
        when(expenseRepository.existsById(expenseDto.getId())).thenReturn(false);

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> expenseService.updateExpense(expenseDto));
    }

    @Test
    void createExpense_ReturnsCreatedExpenseDto() {
        // Arrange
        ExpenseCreationDto expenseDto = new ExpenseCreationDto();
        ExpenseEntity expenseEntity = new ExpenseEntity();
        when(expenseRepository.save(any())).thenReturn(expenseEntity);

        // Act
        ExpenseDetailsDto createdExpense = expenseService.createExpense(expenseDto);

        // Assert
        assertNotNull(createdExpense);
    }

    @Test
    void deleteExpense_WhenExpenseExists_DeletesExpense() {
        // Arrange
        long expenseId = 1L;
        when(expenseRepository.existsById(expenseId)).thenReturn(true);

        // Act
        expenseService.deleteExpense(expenseId);

        // Assert
        verify(expenseRepository, times(1)).deleteById(expenseId);
    }

    @Test
    void deleteExpense_WhenExpenseDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long expenseId = 1L;
        when(expenseRepository.existsById(expenseId)).thenReturn(false);

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> expenseService.deleteExpense(expenseId));
    }


    private ExpenseDetailsDto mapExpenseEntityToDto(ExpenseEntity expenseEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(expenseEntity, ExpenseDetailsDto.class);
    }

}
