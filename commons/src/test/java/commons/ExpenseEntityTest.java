package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseEntityTest {
    private ExpenseEntity expense;

    @BeforeEach
    public void setup(){
        UserEntity user = new UserEntity(1L, "FirstName", "LastName", "email@gmail.com",
                "Some password", true);
        this.expense = new ExpenseEntity(11L, 420.69D, user, new ArrayList<>(), "Title",
                new Date(2024 -1900, Calendar.JANUARY, 24));
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
        assertEquals(new UserEntity(1L, "FirstName", "LastName", "email@gmail.com",
                "Some password", true), this.expense.getAuthor());
    }

    @Test
    void getDebtors() {
        assertEquals(new ArrayList<>(), this.expense.getDebtors());
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
        this.expense.addDebtor(new UserEntity(1L, "FirstName", "LastName",
                "email@gmail.com", "Some password", true));
        assertEquals(new ArrayList<UserEntity>(List.of(new UserEntity(1L, "FirstName", "LastName",
                "email@gmail.com", "Some password", true))), this.expense.getDebtors());
    }

    @Test
    void testEquals() {
        UserEntity user = new UserEntity(1L, "FirstName", "LastName", "email@gmail.com",
                "Some password", true);
        assertEquals(this.expense, new ExpenseEntity(11L, 420.69D, user, new ArrayList<>(), "Title",
                new Date(2024 -1900, Calendar.JANUARY, 24)));
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