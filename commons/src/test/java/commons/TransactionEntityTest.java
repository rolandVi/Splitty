package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionEntityTest {

    @Test
    void getId() {
        TransactionEntity t = new TransactionEntity(1234L, 19.99, null, null);
        assertEquals(1234L, t.getId());
    }

    @Test
    void getMoney() {
        TransactionEntity t = new TransactionEntity(1234L, 19.99, null, null);
        assertEquals(19.99, t.getMoney());
    }

    @Test
    void getSender() {
        UserEntity sender = new UserEntity(1L, "Sender", "LastName", "email@gmail.com", "Some password", true);
        UserEntity receiver = new UserEntity(2L, "Receiver", "LastName", "email@gmail.com", "Some password", true);
        TransactionEntity t = new TransactionEntity(1234L, 19.99, sender, receiver);
        assertEquals(sender, t.getSender());
    }

    @Test
    void getReceiver() {
        UserEntity sender = new UserEntity(1L, "Sender", "LastName", "email@gmail.com", "Some password", true);
        UserEntity receiver = new UserEntity(2L, "Receiver", "LastName", "email@gmail.com", "Some password", true);
        TransactionEntity t = new TransactionEntity(1234L, 19.99, sender, receiver);
        assertEquals(receiver, t.getReceiver());
    }
}