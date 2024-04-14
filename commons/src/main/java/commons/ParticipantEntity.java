package commons;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


@Entity
@Table(name = "users")
public class ParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
    @Column
    private String email;

    @ManyToOne(cascade = CascadeType.DETACH)
    private EventEntity event;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BankAccountEntity bankAccount;

    /**
     * Empty constructor tha will be used by JPA
     */
    public ParticipantEntity() {
    }

    /**
     * All params constructor
     * @param id the id of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param event the event
     * @param bankAccount the events
     */
    public ParticipantEntity(Long id, String firstName,
                             String lastName, String email,
                             EventEntity event, BankAccountEntity bankAccount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.event = event;
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
     * getter for the event
     * @return the event
     */
    public EventEntity getEvent() {
        return event;
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

    /**
     * setter for the event
     * @param event the new event
     * @return the new participant info
     */
    public ParticipantEntity setEvent(EventEntity event) {
        this.event = event;
        return this;
    }
}
