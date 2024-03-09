package server.dto.view;

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
     *
     * @param id        the id
     * @param firstName the firstName
     * @param lastName  the lastName
     */
    public UserNameDto(Long id, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
        UserNameDto that = (UserNameDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName);
    }

    /**
     * Generating hash code
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    /**
     * Represents a person with a unique identifier (ID), first name, and last name.
     */
    public class Person {
        private Long id;
        private String firstName;
        private String lastName;

        /**
         * Retrieves the unique identifier (ID) of the person.
         *
         * @return The ID of the person.
         */
        public Long getId() {
            return id;
        }

        /**
         * Sets the unique identifier (ID) of the person.
         *
         * @param id The ID to set for the person.
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * Retrieves the first name of the person.
         *
         * @return The first name of the person.
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * Sets the first name of the person.
         *
         * @param firstName The first name to set for the person.
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         * Retrieves the last name of the person.
         *
         * @return The last name of the person.
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Sets the last name of the person.
         *
         * @param lastName The last name to set for the person.
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
