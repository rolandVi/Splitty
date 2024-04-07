package dto.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals("Expense 1", transaction1.getExpenseTitle());
    }

    @Test
    void setExpenseTitle() {
        transaction1.setExpenseTitle("New Expense");
        Assertions.assertEquals("New Expense", transaction1.getExpenseTitle());
    }

    @Test
    void getReceiverFirstName() {
        Assertions.assertEquals("John", transaction1.getReceiverFirstName());
    }

    @Test
    void setReceiverFirstName() {
        transaction1.setReceiverFirstName("Jane");
        Assertions.assertEquals("Jane", transaction1.getReceiverFirstName());
    }

    @Test
    void getReceiverLastName() {
        Assertions.assertEquals("Doe", transaction1.getReceiverLastName());
    }

    @Test
    void setReceiverLastName() {
        transaction1.setReceiverLastName("Smith");
        Assertions.assertEquals("Smith", transaction1.getReceiverLastName());
    }

    @Test
    void getMoney() {
        Assertions.assertEquals(100.0, transaction1.getMoney());
    }

    @Test
    void setMoney() {
        transaction1.setMoney(200.0);
        Assertions.assertEquals(200.0, transaction1.getMoney());
    }

    @Test
    void getDate() {
        Assertions.assertEquals("2024-03-27", transaction1.getDate());
    }

    @Test
    void setDate() {
        transaction1.setDate("2024-04-01");
        Assertions.assertEquals("2024-04-01", transaction1.getDate());
    }

    @Test
    void testEquals() {
        // Test if two transactions with the same details are equal
        Assertions.assertEquals(transaction1, transaction2);

        // Test if two transactions with different details are not equal
        transaction2.setMoney(200.0);
        Assertions.assertNotEquals(transaction1, transaction2);
    }

    @Test
    void testHashCode() {
        // Test hash code consistency
        Assertions.assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }
}
