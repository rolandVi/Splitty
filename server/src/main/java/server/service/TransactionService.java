package server.service;

import com.sun.jdi.ObjectCollectedException;
import commons.ExpenseEntity;
import commons.TransactionEntity;
import commons.UserEntity;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.dto.TransactionCreationDto;
import server.dto.view.TransactionDetailsDto;
import server.repository.TransactionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    private final ExpenseService expenseService;

    /**
     * Constructor
     *
     * @param transactionRepository the transaction repository
     * @param modelMapper           a model mapper instance
     * @param userService           the user service
     * @param expenseService        the expense service
     */
    public TransactionService(TransactionRepository transactionRepository,
                              ModelMapper modelMapper, UserService userService,
                              ExpenseService expenseService) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.expenseService = expenseService;
    }

    /**
     * Creates a transaction
     *
     * @param transaction the transaction details
     */
    public void executeTransaction(TransactionCreationDto transaction) {
        TransactionEntity newTransaction = new TransactionEntity();
        ExpenseEntity expense = this.expenseService.
                findExpenseEntityById(transaction.getExpenseId());
        UserEntity receiver = this.userService.findById(transaction.getReceiverId());
        UserEntity sender = this.userService.findById(transaction.getSenderId());
        double money = this.expenseService.payDebt(expense, receiver, sender);
        newTransaction.setMoney(money)
                .setExpense(expense)
                .setReceiver(receiver)
                .setSender(sender);
        this.transactionRepository.save(newTransaction);
    }

    /**
     * Deletes a transaction
     *
     * @param id the transaction id
     */
    @Transactional
    public void revertTransaction(Long id) {
        TransactionEntity transactionEntity = this.transactionRepository.findById(id)
                .orElseThrow(ObjectCollectedException::new);
        this.expenseService.resetDebt(transactionEntity.getExpense(),
                transactionEntity.getReceiver(),
                transactionEntity.getSender());

        this.transactionRepository.delete(transactionEntity);
    }

    /**
     *
     * @param senderId the sender id
     * @return a list of transactions with the same sender
     */
    public List<TransactionDetailsDto> findSentTransactions(Long senderId) {
        return this.transactionRepository.findTransactionEntitiesBySenderId(senderId)
                .stream()
                .map(t -> this.modelMapper.map(t, TransactionDetailsDto.class))
                .sorted(Comparator.comparing(TransactionDetailsDto::getDate))
                .distinct()
                .collect(Collectors.toList());

    }

    /**
     *
     * @param receiverId the receiver id
     * @return the transactions with the same receiver
     */
    public List<TransactionDetailsDto> findReceivedTransactions(Long receiverId) {
        return this.transactionRepository.findTransactionEntitiesByReceiverId(receiverId)
                .stream()
                .map(t -> this.modelMapper.map(t, TransactionDetailsDto.class))
                .sorted(Comparator.comparing(TransactionDetailsDto::getDate))
                .distinct()
                .collect(Collectors.toList());
    }
}
