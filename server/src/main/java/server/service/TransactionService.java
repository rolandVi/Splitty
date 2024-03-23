package server.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.dto.TransactionDetailsDto;
import server.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    /**
     * Constructor
     *
     * @param transactionRepository the transaction repository
     * @param modelMapper a model mapper instance
     */
    public TransactionService(TransactionRepository transactionRepository,
                              ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a transaction
     * @param transaction the transaction details
     */
    public void executeTransaction(TransactionDetailsDto transaction) {

    }

    /**
     * Deletes a transaction
     * @param transaction the transaction details
     */
    @Transactional
    public void revertTransaction(TransactionDetailsDto transaction) {
    }
}
