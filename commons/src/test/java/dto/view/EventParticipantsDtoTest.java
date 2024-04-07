package dto.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class EventParticipantsDtoTest {

    @Test
    void testEmptyConstructor(){
        new EventParticipantsDto();
    }

    @Test
    void getParticipants() {
        Set<ParticipantNameDto> participants = new HashSet<>();
        participants.add(new ParticipantNameDto(1L, "John", "Doe", ""));
        participants.add(new ParticipantNameDto(2L, "Jane", "Smith", ""));

        EventParticipantsDto eventParticipants = new EventParticipantsDto(participants);

        Assertions.assertEquals(participants, eventParticipants.getParticipants());
    }

    @Test
    void setParticipants() {
        Set<ParticipantNameDto> participants = new HashSet<>();
        participants.add(new ParticipantNameDto(1L, "John", "Doe", ""));
        participants.add(new ParticipantNameDto(2L, "Jane", "Smith", ""));

        EventParticipantsDto eventParticipants = new EventParticipantsDto(new HashSet<>());
        eventParticipants.setParticipants(participants);

        Assertions.assertEquals(participants, eventParticipants.getParticipants());
    }

    @Test
    void testEquals() {
        Set<ParticipantNameDto> participants1 = new HashSet<>();
        participants1.add(new ParticipantNameDto(1L, "John", "Doe", ""));
        participants1.add(new ParticipantNameDto(2L, "Jane", "Smith", ""));

        Set<ParticipantNameDto> participants2 = new HashSet<>();
        participants2.add(new ParticipantNameDto(1L, "John", "Doe", ""));
        participants2.add(new ParticipantNameDto(2L, "Jane", "Smith", ""));

        EventParticipantsDto eventParticipants1 = new EventParticipantsDto(participants1);
        EventParticipantsDto eventParticipants2 = new EventParticipantsDto(participants2);

        Assertions.assertEquals(eventParticipants1, eventParticipants2);
    }

    @Test
    void testHashCode() {
        Set<ParticipantNameDto> participants1 = new HashSet<>();
        participants1.add(new ParticipantNameDto(1L, "John", "Doe", ""));
        participants1.add(new ParticipantNameDto(2L, "Jane", "Smith", ""));

        Set<ParticipantNameDto> participants2 = new HashSet<>();
        participants2.add(new ParticipantNameDto(1L, "John", "Doe", ""));
        participants2.add(new ParticipantNameDto(2L, "Jane", "Smith", ""));

        EventParticipantsDto eventParticipants1 = new EventParticipantsDto(participants1);
        EventParticipantsDto eventParticipants2 = new EventParticipantsDto(participants2);

        Assertions.assertEquals(eventParticipants1.hashCode(), eventParticipants2.hashCode());
    }
}
