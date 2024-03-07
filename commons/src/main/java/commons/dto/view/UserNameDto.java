package commons.dto.view;

import java.util.Objects;

public class UserNameDto {

    private Long id;

    private String firstName;
    private String lastName;

    /**
     * Empty constructor
     */
    public UserNameDto() {
    }

    /**
     * Constructor
     * @param id the id
     * @param firstName the firstName
     * @param lastName the lastName
     */
    public UserNameDto(Long id, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Equals to check for equality
     * @param o the object to compare to
     * @return true if they are equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNameDto that = (UserNameDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName);
    }

    /**
     * Generating hash code
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
