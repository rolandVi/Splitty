package server.controller.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dto.TransactionCreationDto;
import dto.view.TransactionDetailsDto;
import server.service.TransactionService;

import java.util.List;

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
            @Valid @RequestBody TransactionCreationDto transaction){
        transactionService.executeTransaction(transaction);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for deleting transactions
     * @param transactionId the transaction id
     * @return whether the deletion was successful
     */
    @DeleteMapping("")
    public ResponseEntity<Void> revertTransaction(
            @NotNull @RequestBody Long transactionId){
        transactionService.revertTransaction(transactionId);
        return ResponseEntity.ok().build();
    }

    /**
     * Get the transactions that a user has sent
     * @param senderId the id of the sender
     * @return the transactions
     */
    @GetMapping("/sent")
    public ResponseEntity<List<TransactionDetailsDto>> getSentTransactions(
            @Valid @RequestBody Long senderId){
        return ResponseEntity.ok(this.transactionService.findSentTransactions(senderId));
    }

    /**
     * Get the transactions that a user has received
     * @param receiverId the id of the receiver
     * @return the transactions
     */
    @GetMapping("/received")
    public ResponseEntity<List<TransactionDetailsDto>> getReceivedTransactions(
            @Valid @RequestBody Long receiverId){
        return ResponseEntity.ok(this.transactionService.findReceivedTransactions(receiverId));
    }
}
