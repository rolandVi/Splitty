package server.dto.view;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EventDetailsDtoTest {

    @Test
    void testEmptyConstructor(){
        new EventDetailsDto();
    }

    @Test
    void getId() {
        Long id = 123L;
        EventDetailsDto event = new EventDetailsDto(id, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>());

        assertEquals(id, event.getId());
    }

    @Test
    void setId() {
        Long id = 123L;
        EventDetailsDto event = new EventDetailsDto(null, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>());

        event.setId(id);

        assertEquals(id, event.getId());
    }

    @Test
    void getInviteCode() {
        String inviteCode = "invite123";
        EventDetailsDto event = new EventDetailsDto(123L, inviteCode, "Test Event",
                new HashSet<>(), new HashSet<>());

        assertEquals(inviteCode, event.getInviteCode());
    }

    @Test
    void setInviteCode() {
        String inviteCode = "invite123";
        EventDetailsDto event = new EventDetailsDto(123L, null, "Test Event",
                new HashSet<>(), new HashSet<>());

        event.setInviteCode(inviteCode);

        assertEquals(inviteCode, event.getInviteCode());
    }

    @Test
    void getTitle() {
        String title = "Test Event";
        EventDetailsDto event = new EventDetailsDto(123L, "invite123", title,
                new HashSet<>(), new HashSet<>());

        assertEquals(title, event.getTitle());
    }

    @Test
    void setTitle() {
        String title = "Test Event";
        EventDetailsDto event = new EventDetailsDto(123L, "invite123", null,
                new HashSet<>(), new HashSet<>());

        event.setTitle(title);

        assertEquals(title, event.getTitle());
    }

    @Test
    void getExpenses() {
        Set<ExpenseDetailsDto> expenses = new HashSet<>();
        expenses.add(new ExpenseDetailsDto(1L, 100.0, null, "Expense 1", new HashSet<>(), new Date()));
        expenses.add(new ExpenseDetailsDto(2L, 200.0, null, "Expense 2", new HashSet<>(), new Date()));

        EventDetailsDto event = new EventDetailsDto(123L, "invite123", "Test Event",
                expenses, new HashSet<>());

        assertEquals(expenses, event.getExpenses());
    }

    @Test
    void setExpenses() {
        Set<ExpenseDetailsDto> expenses = new HashSet<>();
        expenses.add(new ExpenseDetailsDto(1L, 100.0, null, "Expense 1", new HashSet<>(), new Date()));
        expenses.add(new ExpenseDetailsDto(2L, 200.0, null, "Expense 2", new HashSet<>(), new Date()));

        EventDetailsDto event = new EventDetailsDto(123L, "invite123", "Test Event",
                null, new HashSet<>());

        event.setExpenses(expenses);

        assertEquals(expenses, event.getExpenses());
    }

    @Test
    void getParticipants() {
        Set<UserNameDto> participants = new HashSet<>();
        participants.add(new UserNameDto(1L, "John", "Doe"));
        participants.add(new UserNameDto(2L, "Jane", "Smith"));

        EventDetailsDto event = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), participants);

        assertEquals(participants, event.getParticipants());
    }

    @Test
    void setParticipants() {
        Set<UserNameDto> participants = new HashSet<>();
        participants.add(new UserNameDto(1L, "John", "Doe"));
        participants.add(new UserNameDto(2L, "Jane", "Smith"));

        EventDetailsDto event = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), null);

        event.setParticipants(participants);

        assertEquals(participants, event.getParticipants());
    }

    @Test
    void testEquals() {
        EventDetailsDto event1 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>());
        EventDetailsDto event2 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>());

        assertEquals(event1, event2);
    }

    @Test
    void testHashCode() {
        EventDetailsDto event1 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>());
        EventDetailsDto event2 = new EventDetailsDto(123L, "invite123", "Test Event",
                new HashSet<>(), new HashSet<>());

        assertEquals(event1.hashCode(), event2.hashCode());
    }
}
