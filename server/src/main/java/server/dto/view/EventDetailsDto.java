package server.dto.view;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class EventDetailsDto {
    private Long id;
    private String inviteCode;

    private String title;

    private final Date creationDate;

    private Date lastModifiedDate;

    private Set<ExpenseDetailsDto> expenses;

    private Set<UserNameDto> participants;

    /**
     * Empty constructor
     */
    public EventDetailsDto() {
        creationDate = new Date(System.currentTimeMillis());
        lastModifiedDate = new Date(System.currentTimeMillis());
    }

    /**
     * Constructor
     * @param id the id
     * @param inviteCode the inviteCode
     * @param title teh title
     * @param expenses the expenses
     * @param participants the participants
     * @param creationDate the creation date
     * @param lastModifiedDate the last modified date
     */
    public EventDetailsDto(Long id, String inviteCode,
                           String title,
                           Set<ExpenseDetailsDto> expenses,
                           Set<UserNameDto> participants,
                           Date creationDate,
                           Date lastModifiedDate) {
        this.id = id;
        this.inviteCode = inviteCode;
        this.title = title;
        this.expenses = expenses;
        this.participants = participants;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Id getter
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Id setter
     * @param id the new id
     * @return the new event
     */
    public EventDetailsDto setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Invite code getter
     * @return the invite code
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * Invite code setter
     * @param inviteCode the new invite code
     * @return the new event
     */
    public EventDetailsDto setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    /**
     * Title getter
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Title setter
     * @param title the new title
     * @return the new event
     */
    public EventDetailsDto setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get the creation Date
     * @return The creation Date
     */
    public Date getCreationDate(){
        return creationDate;
    }

    /**
     * Get the last modified Date
     * @return The last modified Date
     */
    public Date getLastModifiedDate(){
        return lastModifiedDate;
    }

    /**
     * updates the last modified date
     */
    public void updateLastModifiedDate(){
        this.lastModifiedDate = new Date();
    }

    /**
     * Expenses getter
     * @return the expenses
     */
    public Set<ExpenseDetailsDto> getExpenses() {
        return expenses;
    }

    /**
     * Expenses setter
     * @param expenses the new expenses
     * @return teh new event
     */
    public EventDetailsDto setExpenses(Set<ExpenseDetailsDto> expenses) {
        this.expenses = expenses;
        return this;
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
    public EventDetailsDto setParticipants(Set<UserNameDto> participants) {
        this.participants = participants;
        return this;
    }

    /**
     * equals method to check for equality of two objects
     * @param o the object to check
     * @return true if they are equal and flase otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDetailsDto that = (EventDetailsDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(inviteCode, that.inviteCode)
                && Objects.equals(title, that.title)
                && Objects.equals(expenses, that.expenses)
                && Objects.equals(participants, that.participants);
    }

    /**
     * Generating hash code
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, inviteCode, title, expenses, participants);
    }
}
