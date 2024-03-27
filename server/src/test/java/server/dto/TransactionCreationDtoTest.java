package server.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionCreationDtoTest {

    private TransactionCreationDto transaction1;
    private TransactionCreationDto transaction2;

    @BeforeEach
    void setUp() {
        transaction1 = new TransactionCreationDto(1L, 2L, 3L);
        transaction2 = new TransactionCreationDto(1L, 2L, 3L);
    }

    @Test
    void getSenderId() {
        assertEquals(1L, transaction1.getSenderId());
    }

    @Test
    void setSenderId() {
        transaction1.setSenderId(10L);
        assertEquals(10L, transaction1.getSenderId());
    }

    @Test
    void getReceiverId() {
        assertEquals(2L, transaction1.getReceiverId());
    }

    @Test
    void setReceiverId() {
        transaction1.setReceiverId(20L);
        assertEquals(20L, transaction1.getReceiverId());
    }

    @Test
    void getExpenseId() {
        assertEquals(3L, transaction1.getExpenseId());
    }

    @Test
    void setExpenseId() {
        transaction1.setExpenseId(30L);
        assertEquals(30L, transaction1.getExpenseId());
    }

    @Test
    void testEquals() {
        // Test if two transactions with the same IDs are equal
        assertEquals(transaction1, transaction2);

        // Test if two transactions with different IDs are not equal
        transaction2.setSenderId(100L);
        assertNotEquals(transaction1, transaction2);
    }

    @Test
    void testHashCode() {
        // Test hash code consistency
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }
}
