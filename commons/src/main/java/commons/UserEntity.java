package commons;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


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

    private String password;

    private boolean isAdmin;

    @OneToOne
    private BankAccountEntity bankAccount;

    /**
     * Empty constructor tha will be used by JPA
     */
    public UserEntity() {
    }

    /**
     * All params constuctor
     * @param id the id of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param password the password of the user
     * @param isAdmin whether the user is an admin or not
     */
    public UserEntity(Long id, String firstName, String lastName,
                      String email, String password, boolean isAdmin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
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
     * Password getter
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for the isAdmin field
     * @return whether the user is an admin or not
     */
    public boolean isAdmin() {
        return isAdmin;
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
}
