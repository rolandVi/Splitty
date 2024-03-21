package commons;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String inviteCode;

    @Column(nullable = false)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @OneToMany
    private Set<ExpenseEntity> expenses;

    @ManyToMany
    private Set<UserEntity> participants;

    /**
     * Default constructor for JBA
     */
    public EventEntity() {
        creationDate = new Date(System.currentTimeMillis());
        lastModifiedDate = new Date(System.currentTimeMillis());

    }

    /**
     * Constructor with all parameters
     * @param id The id of the Event
     * @param inviteCode The invite code of the event
     * @param title The title of the event
     * @param expenses The list of expenses of the event
     * @param participants The list of users of the event
     * @param creationDate the creation date
     * @param lastModifiedDate the last modified date
     */
    public EventEntity(Long id, String inviteCode, String title,
                       Set<ExpenseEntity> expenses, Set<UserEntity> participants,
                       Date creationDate, Date lastModifiedDate) {
        this.id = id;
        this.inviteCode = inviteCode;
        this.title = title;
        this.expenses = expenses;
        this.participants = participants;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     *  Get the id of the Event
     * @return The id of the event
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the invite code of the event.
     *
     * @return The invite code of the event.
     */
    public String getInviteCode() {
        return inviteCode;
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
     * Set a new invite code
     * @param inviteCode the invite code
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    /**
     * Gets the title
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Get the list of expenses associated with the event.
     *
     * @return The list of expenses associated with the event.
     */
    public Set<ExpenseEntity> getExpenses() {
        return expenses;
    }


    /**
     * Get the list of users associated with the event.
     *
     * @return The list of users associated with the event.
     */
    public Set<UserEntity> getParticipants() {
        return participants;
    }


    /**
     * Generate a hash code value for the event entity.
     *
     * @return A hash code value for the event entity.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    /**
     * Compare this event entity to another object for equality.
     *
     * @param o The object to compare to.
     * @return true if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity that = (EventEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(inviteCode, that.inviteCode)
                && Objects.equals(expenses, that.expenses)
                && Objects.equals(participants, that.participants);
    }


    /**
     * Adds a new participant
     * @param participant The new participant
     */
    public void addParticipant(UserEntity participant) {
        this.participants.add(participant);
    }

    /**
     * Removes participant
     * @param participant The participant
     */
    public void removeParticipant(UserEntity participant) {
        this.participants.remove(participant);
    }

    /**
     * Adds new expens
     * @param expense The expense
     */
    public void addExpense(ExpenseEntity expense) {
        this.expenses.add(expense);
    }

    /**
     * Removes expense
     * @param expense The expense
     */
    public void removeExpense(ExpenseEntity expense) {
        this.expenses.remove(expense);
    }
}
