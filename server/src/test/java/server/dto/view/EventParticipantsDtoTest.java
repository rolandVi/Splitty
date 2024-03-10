package server.dto.view;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventParticipantsDtoTest {

    @Test
    void testEmptyConstructor(){
        new EventParticipantsDto();
    }

    @Test
    void getParticipants() {
        Set<UserNameDto> participants = new HashSet<>();
        participants.add(new UserNameDto(1L, "John", "Doe"));
        participants.add(new UserNameDto(2L, "Jane", "Smith"));

        EventParticipantsDto eventParticipants = new EventParticipantsDto(participants);

        assertEquals(participants, eventParticipants.getParticipants());
    }

    @Test
    void setParticipants() {
        Set<UserNameDto> participants = new HashSet<>();
        participants.add(new UserNameDto(1L, "John", "Doe"));
        participants.add(new UserNameDto(2L, "Jane", "Smith"));

        EventParticipantsDto eventParticipants = new EventParticipantsDto(new HashSet<>());
        eventParticipants.setParticipants(participants);

        assertEquals(participants, eventParticipants.getParticipants());
    }

    @Test
    void testEquals() {
        Set<UserNameDto> participants1 = new HashSet<>();
        participants1.add(new UserNameDto(1L, "John", "Doe"));
        participants1.add(new UserNameDto(2L, "Jane", "Smith"));

        Set<UserNameDto> participants2 = new HashSet<>();
        participants2.add(new UserNameDto(1L, "John", "Doe"));
        participants2.add(new UserNameDto(2L, "Jane", "Smith"));

        EventParticipantsDto eventParticipants1 = new EventParticipantsDto(participants1);
        EventParticipantsDto eventParticipants2 = new EventParticipantsDto(participants2);

        assertEquals(eventParticipants1, eventParticipants2);
    }

    @Test
    void testHashCode() {
        Set<UserNameDto> participants1 = new HashSet<>();
        participants1.add(new UserNameDto(1L, "John", "Doe"));
        participants1.add(new UserNameDto(2L, "Jane", "Smith"));

        Set<UserNameDto> participants2 = new HashSet<>();
        participants2.add(new UserNameDto(1L, "John", "Doe"));
        participants2.add(new UserNameDto(2L, "Jane", "Smith"));

        EventParticipantsDto eventParticipants1 = new EventParticipantsDto(participants1);
        EventParticipantsDto eventParticipants2 = new EventParticipantsDto(participants2);

        assertEquals(eventParticipants1.hashCode(), eventParticipants2.hashCode());
    }
}
