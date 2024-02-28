package server.dto.view;

import java.util.Objects;
import java.util.Set;

public class EventDetailsDto {
    private Long id;
    private String inviteCode;

    private String title;

    private Set<ExpenseDetailsDto> expenses;

    private Set<UserNameDto> participants;

    public EventDetailsDto(Long id, String inviteCode,
                           String title,
                           Set<ExpenseDetailsDto> expenses,
                           Set<UserNameDto> participants) {
        this.id = id;
        this.inviteCode = inviteCode;
        this.title = title;
        this.expenses = expenses;
        this.participants = participants;
    }

    public Long getId() {
        return id;
    }

    public EventDetailsDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public EventDetailsDto setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public EventDetailsDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public Set<ExpenseDetailsDto> getExpenses() {
        return expenses;
    }

    public EventDetailsDto setExpenses(Set<ExpenseDetailsDto> expenses) {
        this.expenses = expenses;
        return this;
    }

    public Set<UserNameDto> getParticipants() {
        return participants;
    }

    public EventDetailsDto setParticipants(Set<UserNameDto> participants) {
        this.participants = participants;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDetailsDto that = (EventDetailsDto) o;
        return Objects.equals(id, that.id) && Objects.equals(inviteCode, that.inviteCode) && Objects.equals(title, that.title) && Objects.equals(expenses, that.expenses) && Objects.equals(participants, that.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inviteCode, title, expenses, participants);
    }
}
