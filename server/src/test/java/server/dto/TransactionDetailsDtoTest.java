package server.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDetailsDtoTest {

    private TransactionDetailsDto transactionDto1;
    private TransactionDetailsDto transactionDto2;
    private TransactionDetailsDto transactionDto3;

    @BeforeEach
    void setUp() {
        // Initialize TransactionDetailsDto objects
        transactionDto1 = new TransactionDetailsDto(1L, 2L, 3L);
        transactionDto2 = new TransactionDetailsDto(1L, 2L, 3L);
        transactionDto3 = new TransactionDetailsDto(4L, 5L, 6L);
    }

    @Test
    void getSenderId() {
        assertEquals(1L, transactionDto1.getSenderId());
        assertEquals(1L, transactionDto2.getSenderId());
        assertEquals(4L, transactionDto3.getSenderId());
    }

    @Test
    void setSenderId() {
        transactionDto1.setSenderId(10L);
        assertEquals(10L, transactionDto1.getSenderId());
    }

    @Test
    void getReceiverId() {
        assertEquals(2L, transactionDto1.getReceiverId());
        assertEquals(2L, transactionDto2.getReceiverId());
        assertEquals(5L, transactionDto3.getReceiverId());
    }

    @Test
    void setReceiverId() {
        transactionDto1.setReceiverId(20L);
        assertEquals(20L, transactionDto1.getReceiverId());
    }

    @Test
    void getExpenseId() {
        assertEquals(3L, transactionDto1.getExpenseId());
        assertEquals(3L, transactionDto2.getExpenseId());
        assertEquals(6L, transactionDto3.getExpenseId());
    }

    @Test
    void setExpenseId() {
        transactionDto1.setExpenseId(30L);
        assertEquals(30L, transactionDto1.getExpenseId());
    }

    @Test
    void testEquals() {
        // Same objects should be equal
        assertEquals(transactionDto1, transactionDto1);
        assertEquals(transactionDto1, transactionDto2);

        // Different objects with same values should be equal
        assertEquals(transactionDto1, new TransactionDetailsDto(1L, 2L, 3L));

        // Objects with different values should not be equal
        assertNotEquals(transactionDto1, transactionDto3);
    }

    @Test
    void testHashCode() {
        // Equal objects should have the same hash code
        assertEquals(transactionDto1.hashCode(), transactionDto2.hashCode());

        // Unequal objects should ideally have different hash codes
        assertNotEquals(transactionDto1.hashCode(), transactionDto3.hashCode());
    }
}
