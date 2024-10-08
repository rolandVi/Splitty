package server.service;

import commons.ExpenseEntity;
import commons.ParticipantEntity;
import dto.ExpenseCreationDto;
import dto.view.ExpenseDetailsDto;
import dto.view.ParticipantNameDto;
import dto.view.TagDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.exception.ObjectNotFoundException;
import server.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final ParticipantService participantService;
    private final EventService eventService;
    private final TagService tagService;

    /**
     * Constructor Injection
     *
     * @param expenseRepository the ExpenseEntity repository
     * @param modelMapper       the ModelMapper injected by Spring
     * @param userService       the user service
     * @param eventService      the event service
     * @param tagService
     */
    public ExpenseService(ExpenseRepository expenseRepository,
                          ModelMapper modelMapper,
                          ParticipantService userService,
                          EventService eventService, TagService tagService) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
        this.participantService = userService;
        this.eventService = eventService;

        this.tagService = tagService;
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
                .orElseThrow(()-> new ObjectNotFoundException("No such expense found")),
                ExpenseDetailsDto.class);
    }

    /**
     * returns ExpenseEntity by id
     * @param id the id of the expense
     * @return the corresponding expense
     */
    public ExpenseEntity getEntityById(long id) {
        return this.expenseRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("No such expense found"));
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
            throw new ObjectNotFoundException("No such expense found");
        }

        ExpenseEntity expenseEntity = getEntityById(expense.getId());

        expenseEntity.setMoney(expense.getMoney());
        expenseEntity.setAuthor(participantService.findById(expense.getAuthor().getId()));
        expenseEntity.setTitle(expense.getTitle());
        expenseEntity.setTag(tagService.findById(expense.getTag().getId()));
        expenseEntity.setDebtors(new HashSet<>());
        for (ParticipantNameDto u  : expense.getDebtors()){
            expenseEntity.addDebtor(participantService.findById(u.getId()));
        }
        expenseEntity.setDate(expense.getDate());

        expenseEntity =  expenseRepository.save(expenseEntity);

        ParticipantNameDto author = new ParticipantNameDto(expenseEntity.getAuthor().getId(),
                expenseEntity.getAuthor().getFirstName(),
                expenseEntity.getAuthor().getLastName(),
                expenseEntity.getAuthor().getEmail());

        Set<ParticipantNameDto> debtors = new HashSet<>();
        for (ParticipantEntity u : expenseEntity.getDebtors()){
            debtors.add(new ParticipantNameDto(u.getId(), u.getFirstName(),
                    u.getLastName(), u.getEmail()));
        }


        ExpenseDetailsDto details = new ExpenseDetailsDto(expenseEntity.getId(),
                expenseEntity.getMoney(),
                author,
                expenseEntity.getTitle(),
                debtors,
                expenseEntity.getDate(),
                new TagDto(expenseEntity.getTag().getId(), expenseEntity.getTag().getTagType(),
                        expenseEntity.getTag().getHexValue()));

        return details;
    }

    /**
     * Create a new expense entity
     * @param expenseDto The expense DTO containing expense details
     * @return The created expense DTO
     */
    @Transactional
    public ExpenseEntity createExpense(ExpenseCreationDto expenseDto) {
        //Create new expenseEntity
        ExpenseEntity expenseEntity = new ExpenseEntity();
        //Set the debtors
        expenseEntity.setDebtors(new HashSet<>());
        for (ParticipantNameDto u  : expenseDto.getDebtors()){
            expenseEntity.addDebtor(participantService.findById(u.getId()));
        }
        //Set the money
        expenseEntity.setMoney(expenseDto.getMoney());
        //Set author
        expenseEntity.setAuthor(participantService.findById(expenseDto.getAuthorId()));
        //Set title
        expenseEntity.setTitle(expenseDto.getTitle());
        //Set date
        expenseEntity.setDate(expenseDto.getDate());
        //Set parent event
        expenseEntity.setEvent(eventService.findEntityById(expenseDto.getEventId()));

        if (expenseDto.getTag() != null) {
            expenseEntity.setTag(tagService.findById(expenseDto.getTag().getId()));
        }

        expenseEntity = expenseRepository.save(expenseEntity);
        eventService.addExpense(expenseEntity);
        return expenseEntity;
    }

    /**
     * Removing an expense by its id
     * @param id the id of the expense we want to delete
     */
    @Transactional
    public void deleteExpense(long id){
        if (!existsById(id)) {
            throw new ObjectNotFoundException("No such expense found");
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
     * Finds the expense entity by id
     * @param id the id of the entity
     * @return the expense entity
     */
    public ExpenseEntity findExpenseEntityById(Long id){
        return this.expenseRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("No such expense found"));
    }


    /**
     * Pays the debt of a specific debtor
     * @param expense the expense entity
     * @param receiver the receiver of the money
     * @param sender the sender of the money
     * @return the money paid
     */
    @Transactional
    public double payDebt(ExpenseEntity expense,
                          ParticipantEntity receiver,
                          ParticipantEntity sender) {
        double owedMoney=roundToNDecimals(expense.getMoney()/expense.getDebtors().size(), 2);
        expense.setMoney(expense.getMoney()-owedMoney);
        expense.getDebtors().remove(sender);
        this.expenseRepository.save(expense);
        return owedMoney;
    }


    /**
     * Resets a transaction and includes the sender again
     * @param expense the expense
     * @param receiver the receiver
     * @param sender the sender
     */
    @Transactional
    public void resetDebt(ExpenseEntity expense,
                          ParticipantEntity receiver,
                          ParticipantEntity sender) {
        double owedMoney=roundToNDecimals(expense.getMoney()/expense.getDebtors().size(), 2);
        expense.setMoney(expense.getMoney()+owedMoney);
        expense.getDebtors().add(sender);
        this.expenseRepository.save(expense);
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

    /**
     * Rounds the value to n decimals
     * @param value the value to round
     * @param n the numbers after the decimal point
     * @return the rounded number
     */
    protected double roundToNDecimals(double value, int n){
        if (n < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(n, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
