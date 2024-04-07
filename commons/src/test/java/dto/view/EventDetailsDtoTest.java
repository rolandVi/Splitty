package dto.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
        Assertions.assertNull(event.getId());
    }

    @Test
    void setId() {
        Long id = 123L;
        event.setId(id);

        Assertions.assertEquals(id, event.getId());
    }

    @Test
    void getInviteCode() {
        Assertions.assertEquals("invite123", event.getInviteCode());
    }

    @Test
    void setInviteCode() {
        String inviteCode = "invite123";
        event.setInviteCode(inviteCode);

        Assertions.assertEquals(inviteCode, event.getInviteCode());
    }

    @Test
    void getTitle() {
        Assertions.assertEquals("Test Event", event.getTitle());
    }

    @Test
    void setTitle() {
        String title = "Test Event";
        event.setTitle(title);

        Assertions.assertEquals(title, event.getTitle());
    }

    @Test
    void getExpenses() {
        Assertions.assertEquals(new HashSet<>(), event.getExpenses());
    }

    @Test
    void setExpenses() {
        Set<ExpenseDetailsDto> expenses = new HashSet<>();
        expenses.add(new ExpenseDetailsDto(1L, 100.0, null, "Expense 1", new HashSet<>(), new Date(), tag));
        expenses.add(new ExpenseDetailsDto(2L, 200.0, null, "Expense 2", new HashSet<>(), new Date(), tag));

        event.setExpenses(expenses);

        Assertions.assertEquals(expenses, event.getExpenses());
    }

    @Test
    void getParticipants() {
        Assertions.assertEquals(new HashSet<>(), event.getParticipants());
    }

    @Test
    void setParticipants() {
        Set<ParticipantNameDto> participants = new HashSet<>();
        participants.add(new ParticipantNameDto(1L, "John", "Doe",""));
        participants.add(new ParticipantNameDto(2L, "Jane", "Smith", ""));

        event.setParticipants(participants);

        Assertions.assertEquals(participants, event.getParticipants());
    }

    @Test
    void testEquals() {
        EventDetailsDto event1 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
        EventDetailsDto event2 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());

        Assertions.assertEquals(event1, event2);
    }

    @Test
    void testHashCode() {
        EventDetailsDto event1 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());
        EventDetailsDto event2 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>(), new Date(), new Date());

        Assertions.assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void testGetCreationDate() {
        Assertions.assertNotNull(event.getCreationDate());
    }

    @Test
    public void testGetLastModifiedDate() {
        Assertions.assertNotNull(event.getLastModifiedDate());
    }

    @Test
    public void testUpdateLastModifiedDate() throws InterruptedException {
        Date initialLastModifiedDate = event.getLastModifiedDate();

        Thread.sleep(1);

        event.updateLastModifiedDate();
        Assertions.assertNotEquals(initialLastModifiedDate, event.getLastModifiedDate());
    }
}
