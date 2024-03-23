package server.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.TransactionDetailsDto;
import server.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Constructor
     * @param transactionService the transaction service for business logic
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Endpoint for creating transactions
     * @param transaction the transaction details
     * @return whether the transaction was successful
     */
    @PostMapping("")
    public ResponseEntity<Void> executeTransaction(
            @Valid @RequestBody TransactionDetailsDto transaction){
        transactionService.executeTransaction(transaction);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for deleting transactions
     * @param transaction the transaction details
     * @return whether the deletion was successful
     */
    @DeleteMapping("")
    public ResponseEntity<Void> revertTransaction(
            @Valid @RequestBody TransactionDetailsDto transaction){
        transactionService.revertTransaction(transaction);
        return ResponseEntity.ok().build();
    }
}
