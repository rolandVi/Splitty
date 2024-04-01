package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class UserCreationDto {
    @NotBlank(message = "Please fill in first name")
    private String firstName;
    @NotBlank(message = "Please fill last name")
    private String lastName;
    @Email(message = "Please enter valid email")
    private String email;

    /**
     * Constructor for the UserCreationDto class.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param email The email address of the user.
     */
    public UserCreationDto(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Default constructor for the UserCreationDto class.
     */
    public UserCreationDto() {
    }

    /**
     * Retrieves the first name of the user.
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * @param firstName The first name to set.
     * @return This UserCreationDto instance.
     */
    public UserCreationDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Retrieves the last name of the user.
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName The last name to set.
     * @return This UserCreationDto instance.
     */
    public UserCreationDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Retrieves the email address of the user.
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * @param email The email address to set.
     * @return This UserCreationDto instance.
     */
    public UserCreationDto setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreationDto that = (UserCreationDto) o;
        return Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(email, that.email);
    }

    /**
     * Returns a hash code value for the object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
