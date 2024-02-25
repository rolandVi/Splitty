package commons;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String inviteCode;

    private String password;

    @OneToMany
    private List<ExpenseEntity> expenses;

    @ManyToMany
    private List<UserEntity> users;

    /**
     * Default constructor for JBA
     */
    public EventEntity() {
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
     * Get the password of the event.
     *
     * @return The password of the event.
     */
    public String getPassword() {
        return password;
    }


    /**
     * Get the list of expenses associated with the event.
     *
     * @return The list of expenses associated with the event.
     */
    public List<ExpenseEntity> getExpenses() {
        return expenses;
    }


    /**
     * Get the list of users associated with the event.
     *
     * @return The list of users associated with the event.
     */
    public List<UserEntity> getUsers() {
        return users;
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
                && Objects.equals(password, that.password)
                && Objects.equals(expenses, that.expenses)
                && Objects.equals(users, that.users);
    }
}
