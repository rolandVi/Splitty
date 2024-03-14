package server.service;

import commons.ExpenseEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.controller.exception.ObjectNotFoundException;
import server.dto.view.ExpenseDetailsDto;
import server.repository.ExpenseRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    /**
     * Constructor Injection
     *
     * @param expenseRepository the ExpenseEntity repository
     * @param modelMapper      the ModelMapper injected by Spring
     */
    public ExpenseService(ExpenseRepository expenseRepository,
                          ModelMapper modelMapper) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Checks if an ExpenseEntity with such id exists
     * @param id the id of the requested ExpenseEntity
     * @return true if it exists and false - if it doesn't
     */
    public boolean existsById(long id) {
        return this.expenseRepository.existsById(id);
    }

    /**
     * Getting an ExpenseEntity by id
     * @param id the id of the requested ExpenseEntity
     * @return the requested ExpenseEntity
     */
    public ExpenseDetailsDto getById(long id) {
        return this.modelMapper.map(this.expenseRepository.findById(id)
                .orElseThrow(ObjectNotFoundException::new), ExpenseDetailsDto.class);
    }

    /**
     * Removing an expense by its id
     * @param id the id of the expense we want to delete
     */
    @Transactional
    public void removeById(long id){
        this.expenseRepository.deleteById(id);
    }

    /**
     * Updating an existing expense
     * @param expense the updated expense
     * @return the updated expense
     */
    public ExpenseDetailsDto updateExpense(@Valid ExpenseDetailsDto expense) {
        if (!this.existsById(expense.getId())) {
            throw new ObjectNotFoundException();
        }
        return modelMapper.map(expense, ExpenseDetailsDto.class);
    }

    /**
     * Create a new expense entity
     * @param expenseDto The expense DTO containing expense details
     * @return The created expense DTO
     */
    @Transactional
    public ExpenseDetailsDto createExpense(@Valid ExpenseDetailsDto expenseDto) {
        ExpenseEntity expenseEntity = modelMapper.map(expenseDto, ExpenseEntity.class);
        ExpenseEntity savedExpense = expenseRepository.save(expenseEntity);
        return modelMapper.map(savedExpense, ExpenseDetailsDto.class);
    }

    /**
     * Removing an expense by its id
     * @param id the id of the expense we want to delete
     */
    @Transactional
    public void deleteExpense(long id){
        if (!existsById(id)) {
            throw new ObjectNotFoundException();
        }
        this.expenseRepository.deleteById(id);
    }


    /**
     * Retrieves all expenses from the database.
     * @return List of ExpenseDetailsDto containing details of all expenses.
     */
    public List<ExpenseDetailsDto> getAllExpenses() {
        List<ExpenseEntity> expenses = expenseRepository.findAll();
        return mapToExpenseDetailsDtoList(expenses);
    }

    /**
     * Calculates the total sum of all expenses.
     * @return Total sum of expenses.
     */
    public Double getTotalSumOfExpenses() {
        return expenseRepository.getTotalSumOfExpenses();
    }

    /**
     * Retrieves expenses for a specific user from the database.
     * @param userId The ID of the user.
     * @return List of ExpenseDetailsDto containing details of expenses for the user.
     */
    public List<ExpenseDetailsDto> getExpensesForUser(Long userId) {
        List<ExpenseEntity> userExpenses = expenseRepository.findExpensesByUserId(userId);
        return mapToExpenseDetailsDtoList(userExpenses);
    }




    /**
     * Maps a list of ExpenseEntity objects to a list of ExpenseDetailsDto objects.
     *
     * @param expenses List of ExpenseEntity objects to map.
     * @return List of ExpenseDetailsDto objects.
     */
    private List<ExpenseDetailsDto> mapToExpenseDetailsDtoList(List<ExpenseEntity> expenses) {
        return expenses.stream()
                .map(this::mapToExpenseDetailsDto)
                .collect(Collectors.toList());
    }

    /**
     * Maps an ExpenseEntity object to an ExpenseDetailsDto object.
     *
     * @param expense ExpenseEntity object to map.
     * @return ExpenseDetailsDto object.
     */
    private ExpenseDetailsDto mapToExpenseDetailsDto(ExpenseEntity expense) {
        ExpenseDetailsDto dto = new ExpenseDetailsDto();
        dto.setId(expense.getId());
        dto.setMoney(expense.getMoney());
        dto.setTitle(expense.getTitle());
        dto.setDate(expense.getDate());
        return dto;
    }
}
