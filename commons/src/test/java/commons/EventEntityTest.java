package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class EventEntityTest {
    private EventEntity event;
    private UserEntity user;
    private ExpenseEntity expense;

    @BeforeEach
    public void initEvent() {

        List<ExpenseEntity> expenses = new ArrayList<>();
        Set<UserEntity> users = new HashSet<>();

        user = new UserEntity(1L, "FirstName", "LastName", "email@gmail.com",
                new HashSet<>(), new BankAccountEntity());

        expense = new ExpenseEntity(11L, 420.69D, user, new ArrayList<>(), "Title",
                new Date(2024 -1900, Calendar.JANUARY, 24));

        event = new EventEntity(1L, "test_invite_code",
                "title", expenses, users);
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
        Set<UserEntity> expectedUsers = new HashSet<>();

        assertEquals(expectedUsers, this.event.getParticipants());
    }

    @Test
    public void testEqualsWithTheSameObject() {
        assertTrue(this.event.equals(this.event));
    }

    @Test
    public void testEqualsWithEqualObject(){
        List<ExpenseEntity> expenses = new ArrayList<>();
        Set<UserEntity> users = new HashSet<>();

        EventEntity eventEntity = new EventEntity(1L, "test_invite_code",
                "title", expenses, users);

        assertEquals(this.event, eventEntity);
    }

    @Test
    public void testEqualsWhenNotEqual(){
        List<ExpenseEntity> expenses = new ArrayList<>();
        Set<UserEntity> users = new HashSet<>();

        EventEntity eventEntity = new EventEntity(2L, "test_invite_code",
                "title", expenses, users);

        assertNotEquals(this.event, eventEntity);
    }

    @Test
    public void testSameHash(){
        List<ExpenseEntity> expenses = new ArrayList<>();
        Set<UserEntity> users = new HashSet<>();

        EventEntity eventEntity = new EventEntity(1L, "test_invite_code",
                "title", expenses, users);

        assertEquals(this.event.hashCode(), eventEntity.hashCode());
    }

    @Test
    public void testSetTitle() {
        String newTitle = "new title";
        event.setTitle(newTitle);
        assertEquals(newTitle, event.getTitle());
    }

    @Test
    public void testSetInviteCode(){
        String newInvite="new invite";
        event.setInviteCode(newInvite);
        assertEquals(newInvite, event.getInviteCode());
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

    @Test
    void testEmptyConstructor() {
        EventEntity emptyEvent = new EventEntity();
        assertNotNull(emptyEvent);
    }

}
