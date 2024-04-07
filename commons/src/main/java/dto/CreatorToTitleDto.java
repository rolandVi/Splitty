package dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class CreatorToTitleDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;


    private String email;

    @NotBlank
    private String title;

    /**
     * Default constructor for CreatorToTitleDto.
     */
    public CreatorToTitleDto() {
    }

    /**
     * Constructs a CreatorToTitleDto with the specified details.
     *
     * @param firstName The first name of the creator.
     * @param lastName  The last name of the creator.
     * @param email     The email address of the creator.
     * @param title     The title associated with the entity.
     */
    public CreatorToTitleDto(String firstName, String lastName, String email, String title) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
    }

    /**
     * Retrieves the first name of the creator.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the creator.
     *
     * @param firstName The first name to set.
     * @return This CreatorToTitleDto instance.
     */
    public CreatorToTitleDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Retrieves the last name of the creator.
     *
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the creator.
     *
     * @param lastName The last name to set.
     * @return This CreatorToTitleDto instance.
     */
    public CreatorToTitleDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Retrieves the email address of the creator.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the creator.
     *
     * @param email The email address to set.
     * @return This CreatorToTitleDto instance.
     */
    public CreatorToTitleDto setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Retrieves the title associated with the entity.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title associated with the entity.
     *
     * @param title The title to set.
     * @return This CreatorToTitleDto instance.
     */
    public CreatorToTitleDto setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Checks if this CreatorToTitleDto is equal to another object.
     *
     * @param o The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatorToTitleDto that = (CreatorToTitleDto) o;
        return Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(email, that.email)
                && Objects.equals(title, that.title);
    }

    /**
     * Computes the hash code for this CreatorToTitleDto.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, title);
    }
}
