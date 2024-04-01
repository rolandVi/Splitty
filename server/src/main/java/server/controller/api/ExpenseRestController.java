package server.controller.api;

import commons.ExpenseEntity;
import commons.UserEntity;
import dto.view.UserNameDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.view.ExpenseDetailsDto;
import server.exception.ObjectNotFoundException;
import dto.ExpenseCreationDto;
import server.service.EventService;
import server.service.ExpenseService;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {

    private final ExpenseService expenseService;
    private final EventService eventService;

    /**
     * Constructor injection
     * @param expenseService the Service for the Expense
     * @param eventService the event service
     */
    public ExpenseRestController(ExpenseService expenseService,
                                 EventService eventService) {
        this.expenseService = expenseService;
        this.eventService = eventService;
    }

    /**
     * Retrieve an expense by ID
     * @param id the ID of the expense to retrieve
     * @return ResponseEntity containing the expense details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDetailsDto> getExpenseById(@PathVariable(name = "id") long id) {
        ExpenseDetailsDto expense = expenseService.getById(id);
        if (expense != null) {
            return ResponseEntity.ok(expense);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new expense
     * @param expense the expense details to create
     * @return ResponseEntity containing the created expense details
     */
    @PostMapping("/")
    public ResponseEntity<ExpenseDetailsDto> createExpense
    (@Valid @RequestBody ExpenseCreationDto expense) {
        ExpenseEntity createdExpense = expenseService.createExpense(expense);

        UserNameDto author = new UserNameDto(createdExpense.getAuthor().getId(),
                createdExpense.getAuthor().getFirstName(),
                createdExpense.getAuthor().getLastName());

        Set<UserNameDto> debtors = new HashSet<>();
        for (UserEntity u : createdExpense.getDebtors()){
            debtors.add(new UserNameDto(u.getId(), u.getFirstName(), u.getLastName()));
        }

        ExpenseDetailsDto details = new ExpenseDetailsDto(createdExpense.getId(),
                createdExpense.getMoney(),
                author,
                createdExpense.getTitle(),
                debtors,
                createdExpense.getDate());

        return ResponseEntity.ok(details);
    }

    /**
     * Update an existing expense
     * @param expense the updated expense details
     * @return ResponseEntity containing the updated expense details
     */
    @PutMapping("/")
    public ResponseEntity<ExpenseDetailsDto> updateExpense
    (@Valid @RequestBody ExpenseDetailsDto expense) {
        if (!checkIdValidity(expense.getId())){
            return ResponseEntity.badRequest().build();
        }

        ExpenseDetailsDto updatedExpense = expenseService.updateExpense(expense);

        return ResponseEntity.ok(updatedExpense);
    }

    /**
     * Delete an expense with the given id
     * @param id the id of the expense to access
     * @param eventId the id of the parent event
     * @return ResponseEntity with badRequest status if invalid id was presented
     *         or ok status if it was deleted successfully
     */
    @DeleteMapping("/{id}/{eventId}")
    public ResponseEntity<Void> removeById(@PathVariable(name = "id") Long id,
                                           @PathVariable(name = "eventId") Long eventId){
        if (!checkIdValidity(id)){
            return ResponseEntity.badRequest().build();
        }
        try {
            eventService.removeExpense(eventId, expenseService.getEntityById(id));
            expenseService.deleteExpense(id);
            return ResponseEntity.ok().build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all expenses
     * @return ResponseEntity containing the list of all expenses
     */
    @GetMapping("/")
    public ResponseEntity<List<ExpenseDetailsDto>> getAllExpenses() {
        List<ExpenseDetailsDto> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    /**
     * Get the total sum of all expenses
     * @return ResponseEntity containing the total sum of expenses
     */
    @GetMapping("/total")
    public ResponseEntity<Double> getTotalSumOfExpenses() {
        Double totalSum = expenseService.getTotalSumOfExpenses();
        return ResponseEntity.ok(totalSum);
    }

    /**
     * Get expenses for a specific user
     * @param userId the ID of the user
     * @return ResponseEntity containing the list of expenses for the user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseDetailsDto>> getExpensesForUser(@PathVariable Long userId) {
        List<ExpenseDetailsDto> userExpenses = expenseService.getExpensesForUser(userId);
        return ResponseEntity.ok(userExpenses);
    }


    /**
     * Helper method to check if an ID is valid
     * @param id the ID to check
     * @return true if the ID is valid, false otherwise
     */
    private boolean checkIdValidity(long id){
        return id > 0 && expenseService.existsById(id);
    }
}
