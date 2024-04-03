package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EventEntityTest {
    private EventEntity event;
    private ParticipantEntity user;
    private ExpenseEntity expense;

    @BeforeEach
    public void initEvent() {

        Set<ExpenseEntity> expenses = new HashSet<>();
        Set<ParticipantEntity> users = new HashSet<>();

        user = new ParticipantEntity(1L, "FirstName", "LastName", "email@gmail.com",
                new EventEntity(), new BankAccountEntity());

        expense = new ExpenseEntity(11L, 420.69D, user, new HashSet<>(), "Title",
                new Date(2024 -1900, Calendar.JANUARY, 24), event);

        event = new EventEntity(1L, "test_invite_code",
                "title", expenses, users, new Date(), new Date());
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
        Set<ExpenseEntity> expectedExpenses = new HashSet<>();

        assertEquals(expectedExpenses, this.event.getExpenses());
    }

    @Test
    public void testUsersGetter() {
        Set<ParticipantEntity> expectedUsers = new HashSet<>();

        assertEquals(expectedUsers, this.event.getParticipants());
    }

    @Test
    public void testEqualsWithTheSameObject() {
        assertTrue(this.event.equals(this.event));
    }

    @Test
    public void testEqualsWithEqualObject(){
        Set<ExpenseEntity> expenses = new HashSet<>();
        Set<ParticipantEntity> users = new HashSet<>();

        EventEntity eventEntity = new EventEntity(1L, "test_invite_code",
                "title", expenses, users, new Date(), new Date());

        assertEquals(this.event, eventEntity);
    }

    @Test
    public void testEqualsWhenNotEqual(){
        Set<ExpenseEntity> expenses = new HashSet<>();
        Set<ParticipantEntity> users = new HashSet<>();

        EventEntity eventEntity = new EventEntity(2L, "test_invite_code",
                "title", expenses, users, new Date(), new Date());

        assertNotEquals(this.event, eventEntity);
    }

    @Test
    public void testSameHash(){
        Set<ExpenseEntity> expenses = new HashSet<>();
        Set<ParticipantEntity> users = new HashSet<>();

        EventEntity eventEntity = new EventEntity(1L, "test_invite_code",
                "title", expenses, users, new Date(), new Date());

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

    @Test
    public void testSetCreationDate() {
        Date newCreationDate = new Date();
        event.setCreationDate(newCreationDate);
        assertEquals(newCreationDate, event.getCreationDate());
    }

    @Test
    public void testSetLastModifiedDate() {
        Date newLastModifiedDate = new Date();
        event.setLastModifiedDate(newLastModifiedDate);
        assertEquals(newLastModifiedDate, event.getLastModifiedDate());
    }

    @Test
    public void testGetCreationDate() {
        assertNotNull(event.getCreationDate());
    }

    @Test
    public void testGetLastModifiedDate() {
        assertNotNull(event.getLastModifiedDate());
    }

    @Test
    public void testUpdateLastModifiedDate() throws InterruptedException {
        Date initialLastModifiedDate = event.getLastModifiedDate();

        Thread.sleep(1);

        event.updateLastModifiedDate();
        assertNotEquals(initialLastModifiedDate, event.getLastModifiedDate());
    }

}
