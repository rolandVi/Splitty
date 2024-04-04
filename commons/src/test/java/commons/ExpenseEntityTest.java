package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExpenseEntityTest {
    private ExpenseEntity expense;

    @BeforeEach
    public void setup(){
        ParticipantEntity user = new ParticipantEntity(1L, "FirstName", "LastName", "email@gmail.com",
                new EventEntity(), new BankAccountEntity());
        this.expense = new ExpenseEntity(11L, 420.69D, user, new HashSet<>(), "Title",
                new Date(2024 -1900, Calendar.JANUARY, 24), null);
    }

    @Test
    void defaultConstructor() {
        new ExpenseEntity();
    }

    @Test
    void getId() {
        assertEquals(11, this.expense.getId());
    }

    @Test
    void getMoney() {
        assertEquals(420.69, this.expense.getMoney());
    }

    @Test
    void getAuthor() {
        assertEquals(new ParticipantEntity(1L, "FirstName", "LastName", "email@gmail.com",
                new EventEntity(), new BankAccountEntity()), this.expense.getAuthor());
    }

    @Test
    void getDebtors() {
        assertEquals(new HashSet<>(), this.expense.getDebtors());
    }

    @Test
    void getTitle() {
        assertEquals("Title", this.expense.getTitle());
    }

    @Test
    void getDate() {
        assertEquals(new Date(2024 -1900, Calendar.JANUARY, 24), this.expense.getDate());
    }

    @Test
    void setMoney() {
        this.expense.setMoney(28.5);
        assertEquals(28.5, this.expense.getMoney());
    }

    @Test
    void setTitle() {
        this.expense.setTitle("New title");
        assertEquals("New title", this.expense.getTitle());
    }

    @Test
    void setDate() {
        this.expense.setDate(new Date(1, Calendar.JANUARY, 24));
        assertEquals(new Date(1, Calendar.JANUARY, 24), this.expense.getDate());
    }

    @Test
    void addDebtor() {
        this.expense.addDebtor(new ParticipantEntity(1L, "FirstName", "LastName",
                "email@gmail.com", new EventEntity(), new BankAccountEntity()));
        assertEquals(new HashSet<>(List.of(new ParticipantEntity(1L, "FirstName", "LastName",
                "email@gmail.com", new EventEntity(), new BankAccountEntity()))), this.expense.getDebtors());
    }

    @Test
    void testEquals() {
        ParticipantEntity user = new ParticipantEntity(1L, "FirstName", "LastName", "email@gmail.com",
                new EventEntity(), new BankAccountEntity());
        assertEquals(this.expense, new ExpenseEntity(11L, 420.69D, user, new HashSet<>(), "Title",
                new Date(2024 -1900, Calendar.JANUARY, 24), null));
    }

    @Test
    void testHashCode() {
        assertEquals(this.expense.hashCode(), this.expense.hashCode());
    }

    @Test
    void testEmptyConstructor() {
        ExpenseEntity emptyExpense = new ExpenseEntity();
        assertNotNull(emptyExpense);
    }
}