package dto.view;

import java.util.Objects;
import java.util.Set;

public class EventParticipantsDto {
    private Set<UserNameDto> participants;

    /**
     * Empty constructor
     */
    public EventParticipantsDto() {
    }

    /**
     * Constructor
     * @param participants the participants
     */
    public EventParticipantsDto(Set<UserNameDto> participants) {
        this.participants = participants;
    }

    /**
     * Participants getter
     * @return the participants
     */
    public Set<UserNameDto> getParticipants() {
        return participants;
    }

    /**
     * Participants setter
     * @param participants the new participants
     * @return the new event
     */
    public EventParticipantsDto setParticipants(Set<UserNameDto> participants) {
        this.participants = participants;
        return this;
    }

    /**
     * Equals method to check for equality between two obejcts
     * @param o teh object to compare to
     * @return true if they are equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventParticipantsDto that = (EventParticipantsDto) o;
        return Objects.equals(participants, that.participants);
    }

    /**
     * Generating hash code
     * @return the hash code of the current object
     */
    @Override
    public int hashCode() {
        return Objects.hash(participants);
    }
}
