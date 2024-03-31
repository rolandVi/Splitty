package server.repository;

import commons.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    /**
     * returns the transactions with the same sender
     * @param senderId the sender id
     * @return the transactions
     */
    Set<TransactionEntity> findTransactionEntitiesBySenderId(Long senderId);

    /**
     * returns the transactions with the same receiver
     * @param receiverId the receiver id
     * @return the transactions
     */
    Set<TransactionEntity> findTransactionEntitiesByReceiverId(Long receiverId);
}
