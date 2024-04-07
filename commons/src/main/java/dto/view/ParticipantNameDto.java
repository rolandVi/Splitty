package dto.view;

import java.util.Objects;

public class ParticipantNameDto {

    private Long id;

    private String firstName;
    private String lastName;

    private String email;

    /**
     * Empty constructor
     */
    public ParticipantNameDto() {
    }

    /**
     * Constructor
     *
     * @param id        the id
     * @param firstName the firstName
     * @param lastName  the lastName
     * @param email the email
     */
    public ParticipantNameDto(Long id, String firstName, String lastName, String email) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
    }

    /**
     * Retrieves the id of the user.
     *
     * @return the id of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id of the user.
     *
     * @param id the id of the user
     * @return this UserNameDto instance
     */
    public ParticipantNameDto setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name of the user
     * @return this UserNameDto instance
     */
    public ParticipantNameDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Retrieves the last name of the user.
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name of the user
     * @return this UserNameDto instance
     */
    public ParticipantNameDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Email getter
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email setter
     * @param email the new email
     * @return the new participant info
     */
    public ParticipantNameDto setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Equals to check for equality
     *
     * @param o the object to compare to
     * @return true if they are equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantNameDto that = (ParticipantNameDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(email, that.email);
    }

    /**
     * Generating hash code
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

    /**
     * @return a string containing the first and last name of the user
     */
    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
