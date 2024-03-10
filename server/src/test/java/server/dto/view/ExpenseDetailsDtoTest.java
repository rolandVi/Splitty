package server.dto.view;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseDetailsDtoTest {

    @Test
    void testEmptyConstructor(){
        new ExpenseDetailsDto();
    }

    @Test
    void getId() {
        Long id = 123L;
        ExpenseDetailsDto expense = new ExpenseDetailsDto(id, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                new Date());

        assertEquals(id, expense.getId());
    }

    @Test
    void setId() {
        Long id = 123L;
        ExpenseDetailsDto expense = new ExpenseDetailsDto(null, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                new Date());

        expense.setId(id);

        assertEquals(id, expense.getId());
    }

    @Test
    void getMoney() {
        Double money = 100.0;
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, money,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                new Date());

        assertEquals(money, expense.getMoney());
    }

    @Test
    void setMoney() {
        Double money = 100.0;
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, null,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                new Date());

        expense.setMoney(money);

        assertEquals(money, expense.getMoney());
    }

    @Test
    void getAuthor() {
        UserNameDto author = new UserNameDto(1L, "John", "Doe");
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                author,
                "Expense 1",
                new HashSet<>(),
                new Date());

        assertEquals(author, expense.getAuthor());
    }

    @Test
    void setAuthor() {
        UserNameDto author = new UserNameDto(1L, "John", "Doe");
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                null,
                "Expense 1",
                new HashSet<>(),
                new Date());

        expense.setAuthor(author);

        assertEquals(author, expense.getAuthor());
    }

    @Test
    void getTitle() {
        String title = "Expense 1";
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                title,
                new HashSet<>(),
                new Date());

        assertEquals(title, expense.getTitle());
    }

    @Test
    void setTitle() {
        String title = "Expense 1";
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                null,
                new HashSet<>(),
                new Date());

        expense.setTitle(title);

        assertEquals(title, expense.getTitle());
    }

    @Test
    void getDebtors() {
        Set<UserNameDto> debtors = new HashSet<>();
        debtors.add(new UserNameDto(1L, "John", "Doe"));
        debtors.add(new UserNameDto(2L, "Jane", "Smith"));

        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                debtors,
                new Date());

        assertEquals(debtors, expense.getDebtors());
    }

    @Test
    void setDebtors() {
        Set<UserNameDto> debtors = new HashSet<>();
        debtors.add(new UserNameDto(1L, "John", "Doe"));
        debtors.add(new UserNameDto(2L, "Jane", "Smith"));

        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                null,
                new Date());

        expense.setDebtors(debtors);

        assertEquals(debtors, expense.getDebtors());
    }

    @Test
    void getDate() {
        Date date = new Date();
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                date);

        assertEquals(date, expense.getDate());
    }

    @Test
    void setDate() {
        Date date = new Date();
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                null);

        expense.setDate(date);

        assertEquals(date, expense.getDate());
    }

    @Test
    void testEquals() {
        ExpenseDetailsDto expense1 = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                new Date());

        ExpenseDetailsDto expense2 = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                new Date());

        assertEquals(expense1, expense2);
    }

    @Test
    void testHashCode() {
        ExpenseDetailsDto expense1 = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                new Date());

        ExpenseDetailsDto expense2 = new ExpenseDetailsDto(123L, 100.0,
                new UserNameDto(1L, "John", "Doe"),
                "Expense 1",
                new HashSet<>(),
                new Date());

        assertEquals(expense1.hashCode(), expense2.hashCode());
    }
}
