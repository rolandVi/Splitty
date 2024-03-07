package commons;

import commons.dto.view.BankAccountDto;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;


@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany
    private Set<EventEntity> events;

    @OneToOne
    private BankAccountEntity bankAccount;

    /**
     * Empty constructor tha will be used by JPA
     */
    public UserEntity() {
    }

    /**
     * All params constructor
     * @param id the id of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param events the events
     * @param bankAccount the events
     */
    public UserEntity(Long id, String firstName, String lastName,
                      String email, Set<EventEntity> events, BankAccountEntity bankAccount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.events = events;
        this.bankAccount = bankAccount;
    }

    /**
     * Id getter
     * @return the id of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * First name getter
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Last name getter
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Email getter
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Events getter
     * @return the events of the user
     */
    public Set<EventEntity> getEvents() {
        return events;
    }

    /**
     * BankAccount getter
     * @return the bank account of the user
     */
    public BankAccountEntity getBankAccount() {
        return bankAccount;
    }

    /**
     * Checks whether two objects are equal or not
     * @param o the object to compare to
     * @return true if the objects are equal or false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * Generates a hash code for the specific object
     * @return the generated hash code
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     *setter for firstname
     * @param firstName firstname
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * setter for lastname
     * @param lastName lastname
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * setter for email
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * setter for bankaccount
     * @param bankAccount bankaccount
     */
    public void setBankAccount(BankAccountEntity bankAccount) {
        this.bankAccount = bankAccount;
    }
}
