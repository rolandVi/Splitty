package server.service;

import commons.EventEntity;
import commons.ExpenseEntity;
import commons.ParticipantEntity;
import dto.ExpenseCreationDto;
import dto.view.ExpenseDetailsDto;
import dto.view.ParticipantNameDto;
import jakarta.mail.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import server.exception.ObjectNotFoundException;
import server.repository.ExpenseRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    private ExpenseService expenseService;

    @Mock
    private EventService eventService;
    @Mock
    private ParticipantService userService;

    private ExpenseEntity expectedDto;
    private List<ExpenseEntity> expectedExpenses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();

        expenseService = new ExpenseService(expenseRepository, modelMapper, userService, eventService);

        expectedDto = new ExpenseEntity(1L, 100.0, new ParticipantEntity(),
                new HashSet<>(), "Expense Title", new Date(), null);

        ExpenseEntity requestDto = new ExpenseEntity(2L, 200.0, new ParticipantEntity(),
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
        ExpenseCreationDto expenseDto = new ExpenseCreationDto("Test", 10.0, 1L, new HashSet<>(Set.of(new ParticipantNameDto())), 1L, new Date());
        ExpenseEntity expenseEntity = new ExpenseEntity();
        when(expenseRepository.save(any())).thenReturn(expenseEntity);

        // Act
        ExpenseEntity createdExpense = expenseService.createExpense(expenseDto);

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

    @Test
    void getEntityById_WhenEntityExists_ReturnsEntity() {
        // Arrange
        long entityId = 1L;
        when(expenseRepository.findById(entityId)).thenReturn(Optional.of(expectedDto));

        // Act
        ExpenseEntity result = expenseService.getEntityById(entityId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void getEntityById_WhenEntityDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long entityId = 1L;
        when(expenseRepository.findById(entityId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> expenseService.getEntityById(entityId));
    }

    @Test
    void updateExpense_WhenExpenseExists_ReturnsUpdatedExpenseDto() {
        // Arrange
        ExpenseDetailsDto expenseDto = new ExpenseDetailsDto();
        expenseDto.setId(1L);
        expenseDto.setAuthor(new ParticipantNameDto(1L, "", "", ""));
        expenseDto.setDebtors(new HashSet<>(Set.of(new ParticipantNameDto())));
        when(expenseService.existsById(expenseDto.getId())).thenReturn(true);
        ExpenseEntity entity = new ExpenseEntity(1L, 200.0,
                new ParticipantEntity(1L, "", "", "", null, null),
                new HashSet<>(Set.of(new ParticipantEntity())), "Another Expense Title", new Date(), null);
        when(expenseRepository.findById(expenseDto.getId())).thenReturn(Optional.of(entity));
        when(expenseRepository.save(any())).thenReturn(entity);
        when(userService.findById(any())).thenReturn(new ParticipantEntity());
        when(userService.findById(1L)).thenReturn(
                new ParticipantEntity(1L, "", "", "", null, null));

        // Act
        ExpenseDetailsDto result = expenseService.updateExpense(expenseDto);

        // Assert
        assertNotNull(result);
        assertEquals(expenseDto.getId(), result.getId());
    }

    @Test
    void findExpenseEntityById_WhenExpenseExists_ReturnsExpenseEntity() {
        // Arrange
        long expenseId = 1L;
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expectedDto));

        // Act
        ExpenseEntity result = expenseService.findExpenseEntityById(expenseId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void findExpenseEntityById_WhenExpenseDoesNotExist_ThrowsObjectNotFoundException() {
        // Arrange
        long expenseId = 1L;
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> expenseService.findExpenseEntityById(expenseId));
    }

    @Test
    void payDebt_WhenExpenseExists_PaysDebt() {
        // Arrange
        long expenseId = 1L;
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setMoney(100.111);
        expenseEntity.setDebtors(new HashSet<>(Set.of(new ParticipantEntity(),
                new ParticipantEntity(1L, "", "", "", null, null))));
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expenseEntity));

        // Act
        double res = expenseService.payDebt(expenseEntity, new ParticipantEntity(), new ParticipantEntity());

        // Assert
        verify(expenseRepository, times(1)).save(expenseEntity);
    }

    @Test
    void resetDebt_WhenExpenseExists_ResetsDebt() {
        // Arrange
        long expenseId = 1L;
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setMoney(100.111);
        expenseEntity.setDebtors(new HashSet<>(Set.of(new ParticipantEntity(),
                new ParticipantEntity(1L, "", "", "", null, null))));
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expenseEntity));

        // Act
        expenseService.resetDebt(expenseEntity, new ParticipantEntity(), new ParticipantEntity());

        // Assert
        verify(expenseRepository, times(1)).save(expenseEntity);
    }

    @Test
    void roundToNDecimalsTest() {
        // Arrange
        double number = 12.3456789;
        int decimals = 2;

        // Act
        double result = expenseService.roundToNDecimals(number, decimals);

        // Assert
        assertEquals(12.35, result);
    }

}
