package server.controller.api;

import commons.ExpenseEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.ExpenseCreationDto;
import server.dto.view.ExpenseDetailsDto;
import server.service.EventService;
import server.service.ExpenseService;

import java.util.List;

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
    public ResponseEntity<ExpenseEntity> createExpense
    (@Valid @RequestBody ExpenseCreationDto expense) {
        //for some reason, here expense has author id 0/null
        System.out.println("Expense: "+expense);
        System.out.println("Expense title: " + expense.getTitle());
        System.out.println("Expense money:"+expense.getMoney());
        System.out.println("Expense eventId: " + expense.getEventId());
        System.out.println("Expense date: " + expense.getDate());
        System.out.println("Debtors' ids: " + expense.getDebtorsIds());
        System.out.println("Expense authorId: "+expense.getAuthorId());
        ExpenseEntity createdExpense = expenseService.createExpense(expense);
        System.out.println("createdExpense in expenseController: " + createdExpense);
        return ResponseEntity.ok(createdExpense);
    }

    /**
     * Update an existing expense
     * @param id the ID of the expense to update
     * @param expense the updated expense details
     * @return ResponseEntity containing the updated expense details
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDetailsDto> updateExpense
    (@PathVariable(name = "id") long id, @Valid @RequestBody ExpenseDetailsDto expense) {
        if (!checkIdValidity(id)){
            return ResponseEntity.badRequest().build();
        }
        expense.setId(id);
        ExpenseDetailsDto updatedExpense = expenseService.updateExpense(expense);
        return ResponseEntity.ok(updatedExpense);
    }

    /**
     * Delete an expense with the given id
     * @param id the id of the expense to access
     * @return ResponseEntity with badRequest status if invalid id was presented
     *         or ok status if it was deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable(name = "id") long id){
        if (!checkIdValidity(id)){
            return ResponseEntity.badRequest().build();
        }
        this.expenseService.removeById(id);
        return ResponseEntity.ok().build();
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
