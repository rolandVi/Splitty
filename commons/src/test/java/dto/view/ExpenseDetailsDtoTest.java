package dto.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

class ExpenseDetailsDtoTest {

    @Test
    void testEmptyConstructor(){
        new ExpenseDetailsDto();
    }

    @Test
    void getId() {
        Long id = 123L;
        ExpenseDetailsDto expense = new ExpenseDetailsDto(id, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        Assertions.assertEquals(id, expense.getId());
    }

    @Test
    void setId() {
        Long id = 123L;
        ExpenseDetailsDto expense = new ExpenseDetailsDto(null, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        expense.setId(id);

        Assertions.assertEquals(id, expense.getId());
    }

    @Test
    void getMoney() {
        Double money = 100.0;
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, money,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        Assertions.assertEquals(money, expense.getMoney());
    }

    @Test
    void setMoney() {
        Double money = 100.0;
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, null,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        expense.setMoney(money);

        Assertions.assertEquals(money, expense.getMoney());
    }

    @Test
    void getAuthor() {
        ParticipantNameDto author = new ParticipantNameDto(1L, "John", "Doe", "");
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                author,
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        Assertions.assertEquals(author, expense.getAuthor());
    }

    @Test
    void setAuthor() {
        ParticipantNameDto author = new ParticipantNameDto(1L, "John", "Doe", "");
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                null,
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        expense.setAuthor(author);

        Assertions.assertEquals(author, expense.getAuthor());
    }

    @Test
    void getTitle() {
        String title = "Expense 1";
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                title,
                new HashSet<>(),
                new Date(), null);

        Assertions.assertEquals(title, expense.getTitle());
    }

    @Test
    void setTitle() {
        String title = "Expense 1";
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                null,
                new HashSet<>(),
                new Date(), null);

        expense.setTitle(title);

        Assertions.assertEquals(title, expense.getTitle());
    }

    @Test
    void getDebtors() {
        Set<ParticipantNameDto> debtors = new HashSet<>();
        debtors.add(new ParticipantNameDto(1L, "John", "Doe", ""));
        debtors.add(new ParticipantNameDto(2L, "Jane", "Smith", ""));

        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                debtors,
                new Date(), null);

        Assertions.assertEquals(debtors, expense.getDebtors());
    }

    @Test
    void setDebtors() {
        Set<ParticipantNameDto> debtors = new HashSet<>();
        debtors.add(new ParticipantNameDto(1L, "John", "Doe", ""));
        debtors.add(new ParticipantNameDto(2L, "Jane", "Smith", ""));

        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                null,
                new Date(), null);

        expense.setDebtors(debtors);

        Assertions.assertEquals(debtors, expense.getDebtors());
    }

    @Test
    void getDate() {
        Date date = new Date();
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                date, null);

        Assertions.assertEquals(date, expense.getDate());
    }

    @Test
    void setDate() {
        Date date = new Date();
        ExpenseDetailsDto expense = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                null, null);

        expense.setDate(date);

        Assertions.assertEquals(date, expense.getDate());
    }

    @Test
    void testEquals() {
        ExpenseDetailsDto expense1 = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        ExpenseDetailsDto expense2 = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        Assertions.assertEquals(expense1, expense2);
    }

    @Test
    void testHashCode() {
        ExpenseDetailsDto expense1 = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        ExpenseDetailsDto expense2 = new ExpenseDetailsDto(123L, 100.0,
                new ParticipantNameDto(1L, "John", "Doe", ""),
                "Expense 1",
                new HashSet<>(),
                new Date(), null);

        Assertions.assertEquals(expense1.hashCode(), expense2.hashCode());
    }
}
