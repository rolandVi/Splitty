package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventEntityTest {
    private EventEntity event;
    private UserEntity user;
    private ExpenseEntity expense;

    @BeforeEach
    public void initEvent() {

        List<ExpenseEntity> expenses = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>();

        user = new UserEntity(1L, "FirstName", "LastName", "email@gmail.com",
                "Some password", true);

        expense = new ExpenseEntity(11L, 420.69D, user, new ArrayList<>(), "Title",
                new Date(2024 -1900, Calendar.JANUARY, 24));

        event = new EventEntity(1L, "test_invite_code",
                "test_password", "title", expenses, users);
    }

    @Test
    public void testIdGetter() {
        assertEquals(1L, this.event.getId());
    }

    @Test
    public void testInviteCodeGetter() {
        assertEquals("test_invite_code", this.event.getInviteCode());
    }

    @Test
    public void testPasswordGetter() {
        assertEquals("test_password", this.event.getPassword());
    }

    @Test
    public void testTitleGetter() {
        assertEquals("title", this.event.getTitle());
    }

    @Test
    public void testExpensesGetter() {
        List<ExpenseEntity> expectedExpenses = new ArrayList<>();

        assertEquals(expectedExpenses, this.event.getExpenses());
    }

    @Test
    public void testUsersGetter() {
        List<UserEntity> expectedUsers = new ArrayList<>();

        assertEquals(expectedUsers, this.event.getParticipants());
    }

    @Test
    public void testEqualsWithTheSameObject() {
        assertTrue(this.event.equals(this.event));
    }

    @Test
    public void testEqualsWithEqualObject(){
        List<ExpenseEntity> expenses = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>();

        EventEntity eventEntity = new EventEntity(1L, "test_invite_code",
                "test_password", "title", expenses, users);

        assertEquals(this.event, eventEntity);
    }

    @Test
    public void testEqualsWhenNotEqual(){
        List<ExpenseEntity> expenses = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>();

        EventEntity eventEntity = new EventEntity(2L, "test_invite_code",
                "test_password", "title", expenses, users);

        assertNotEquals(this.event, eventEntity);
    }

    @Test
    public void testSameHash(){
        List<ExpenseEntity> expenses = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>();

        EventEntity eventEntity = new EventEntity(1L, "test_invite_code",
                "test_password", "title", expenses, users);

        assertEquals(this.event.hashCode(), eventEntity.hashCode());
    }

    @Test
    public void testSetTitle() {
        String newTitle = "new title";
        event.setTitle(newTitle);
        assertEquals(newTitle, event.getTitle());
    }

    @Test
    public void testSetPassword() {
        String newPassword = "new password";
        event.setPassword(newPassword);
        assertEquals(newPassword, event.getPassword());
    }

    @Test
    public void testAddAndRemoveExpense() {
        event.addExpense(expense);

        assertEquals(1, event.getExpenses().size());

        event.removeExpense(expense);

        assertEquals(0, event.getExpenses().size());
    }

    @Test
    public void testAddAndRemoveParticipant() {
        event.addParticipant(user);

        assertEquals(1, event.getParticipants().size());

        event.removeParticipant(user);

        assertEquals(0, event.getParticipants().size());
    }

}
