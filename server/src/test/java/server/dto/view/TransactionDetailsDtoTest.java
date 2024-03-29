package server.dto.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDetailsDtoTest {

    private TransactionDetailsDto transaction1;
    private TransactionDetailsDto transaction2;

    @BeforeEach
    void setUp() {
        transaction1 = new TransactionDetailsDto("Expense 1", "John", "Doe", 100.0, "2024-03-27");
        transaction2 = new TransactionDetailsDto("Expense 1", "John", "Doe", 100.0, "2024-03-27");
    }

    @Test
    void getExpenseTitle() {
        assertEquals("Expense 1", transaction1.getExpenseTitle());
    }

    @Test
    void setExpenseTitle() {
        transaction1.setExpenseTitle("New Expense");
        assertEquals("New Expense", transaction1.getExpenseTitle());
    }

    @Test
    void getReceiverFirstName() {
        assertEquals("John", transaction1.getReceiverFirstName());
    }

    @Test
    void setReceiverFirstName() {
        transaction1.setReceiverFirstName("Jane");
        assertEquals("Jane", transaction1.getReceiverFirstName());
    }

    @Test
    void getReceiverLastName() {
        assertEquals("Doe", transaction1.getReceiverLastName());
    }

    @Test
    void setReceiverLastName() {
        transaction1.setReceiverLastName("Smith");
        assertEquals("Smith", transaction1.getReceiverLastName());
    }

    @Test
    void getMoney() {
        assertEquals(100.0, transaction1.getMoney());
    }

    @Test
    void setMoney() {
        transaction1.setMoney(200.0);
        assertEquals(200.0, transaction1.getMoney());
    }

    @Test
    void getDate() {
        assertEquals("2024-03-27", transaction1.getDate());
    }

    @Test
    void setDate() {
        transaction1.setDate("2024-04-01");
        assertEquals("2024-04-01", transaction1.getDate());
    }

    @Test
    void testEquals() {
        // Test if two transactions with the same details are equal
        assertEquals(transaction1, transaction2);

        // Test if two transactions with different details are not equal
        transaction2.setMoney(200.0);
        assertNotEquals(transaction1, transaction2);
    }

    @Test
    void testHashCode() {
        // Test hash code consistency
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }
}
