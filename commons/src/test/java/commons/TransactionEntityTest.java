package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionEntityTest {
    private UserEntity sender;
    private UserEntity receiver;
    private TransactionEntity transaction;
    @BeforeEach
    void setup() {
        this.sender = new UserEntity(1L, "Sender", "LastName",
                "email@gmail.com", "Some password", true);
        this.receiver = new UserEntity(2L, "Receiver", "LastName",
                "email@gmail.com", "Some password", true);
        this.transaction = new TransactionEntity(1234L, 19.99, sender, receiver);
    }

    @Test
    void getId() {
        assertEquals(1234L, transaction.getId());
    }

    @Test
    void getMoney() {
        assertEquals(19.99, transaction.getMoney());
    }

    @Test
    void getSender() {
        assertEquals(sender, transaction.getSender());
    }

    @Test
    void getReceiver() {
        assertEquals(receiver, transaction.getReceiver());
    }

    @Test
    void testEquals() {
        TransactionEntity transaction2 = new TransactionEntity(1234L, 19.99, sender, receiver);
        assertEquals(transaction, transaction2);
    }

    @Test
    void testHashCode() {
        TransactionEntity transaction2 = new TransactionEntity(1234L, 19.99, sender, receiver);
        assertEquals(transaction.hashCode(), transaction2.hashCode());
    }
}