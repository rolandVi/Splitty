package server.dto.view;

import java.util.Objects;
import java.util.Set;

public class EventParticipantsDto {
    private Set<UserNameDto> participants;

    public EventParticipantsDto(Set<UserNameDto> participants) {
        this.participants = participants;
    }

    public Set<UserNameDto> getParticipants() {
        return participants;
    }

    public EventParticipantsDto setParticipants(Set<UserNameDto> participants) {
        this.participants = participants;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventParticipantsDto that = (EventParticipantsDto) o;
        return Objects.equals(participants, that.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participants);
    }
}
