package server.service;

import commons.ExpenseEntity;
import commons.TransactionEntity;
import commons.UserEntity;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.dto.TransactionDetailsDto;
import server.repository.TransactionRepository;

@Service
public class TransactionService {

    final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    final UserService userService;

    final ExpenseService expenseService;

    /**
     * Constructor
     *
     * @param transactionRepository the transaction repository
     * @param modelMapper           a model mapper instance
     * @param userService
     * @param expenseService
     */
    public TransactionService(TransactionRepository transactionRepository,
                              ModelMapper modelMapper, UserService userService, ExpenseService expenseService) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.expenseService = expenseService;
    }

    /**
     * Creates a transaction
     * @param transaction the transaction details
     */
    public void executeTransaction(TransactionDetailsDto transaction) {
        TransactionEntity newTransaction=new TransactionEntity();
        ExpenseEntity expense=this.expenseService.findExpenseEntityById(transaction.getExpenseId());
        UserEntity receiver=this.userService.findById(transaction.getReceiverId());
        UserEntity sender=this.userService.findById(transaction.getSenderId());
        double money=this.expenseService.payDebt(expense, receiver, sender);
        newTransaction.setMoney(money)
                .setExpense(expense)
                .setReceiver(receiver)
                .setSender(sender);
        this.transactionRepository.save(newTransaction);
    }

    /**
     * Deletes a transaction
     * @param transaction the transaction details
     */
    @Transactional
    public void revertTransaction(TransactionDetailsDto transaction) {
    }
}
