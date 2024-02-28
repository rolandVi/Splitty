package server.dto.view;

import java.util.Objects;

public class UserNameDto {

    private Long id;

    private String firstName;
    private String lastName;

    /**
     * Constructor
     * @param id the id
     * @param firstName the firstName
     * @param lastName the lastName
     */
    public UserNameDto(Long id, String firstName, String lastName) {
        this.id = id;
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
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    /**
     * Generating hash code
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
