package server.dto.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EventDetailsDtoTest {

    private EventDetailsDto event;
    @BeforeEach
    void setUp() {
        event = new EventDetailsDto(null, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
    }

    @Test
    void testEmptyConstructor(){
        new EventDetailsDto();
    }

    @Test
    void getId() {
        assertNull(event.getId());
    }

    @Test
    void setId() {
        Long id = 123L;
        event.setId(id);

        assertEquals(id, event.getId());
    }

    @Test
    void getInviteCode() {
        assertEquals("invite123", event.getInviteCode());
    }

    @Test
    void setInviteCode() {
        String inviteCode = "invite123";
        event.setInviteCode(inviteCode);

        assertEquals(inviteCode, event.getInviteCode());
    }

    @Test
    void getTitle() {
        assertEquals("Test Event", event.getTitle());
    }

    @Test
    void setTitle() {
        String title = "Test Event";
        event.setTitle(title);

        assertEquals(title, event.getTitle());
    }

    @Test
    void getExpenses() {
        assertEquals(new HashSet<>(), event.getExpenses());
    }

    @Test
    void setExpenses() {
        Set<ExpenseDetailsDto> expenses = new HashSet<>();
        expenses.add(new ExpenseDetailsDto(1L, 100.0, null, "Expense 1", new HashSet<>(), new Date()));
        expenses.add(new ExpenseDetailsDto(2L, 200.0, null, "Expense 2", new HashSet<>(), new Date()));

        event.setExpenses(expenses);

        assertEquals(expenses, event.getExpenses());
    }

    @Test
    void getParticipants() {
        assertEquals(new HashSet<>(), event.getParticipants());
    }

    @Test
    void setParticipants() {
        Set<UserNameDto> participants = new HashSet<>();
        participants.add(new UserNameDto(1L, "John", "Doe"));
        participants.add(new UserNameDto(2L, "Jane", "Smith"));

        event.setParticipants(participants);

        assertEquals(participants, event.getParticipants());
    }

    @Test
    void testEquals() {
        EventDetailsDto event1 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
        EventDetailsDto event2 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());

        assertEquals(event1, event2);
    }

    @Test
    void testHashCode() {
        EventDetailsDto event1 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
        EventDetailsDto event2 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());

        assertEquals(event1.hashCode(), event2.hashCode());
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
