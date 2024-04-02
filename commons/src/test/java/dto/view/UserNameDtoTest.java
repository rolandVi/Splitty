package dto.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserNameDtoTest {

    private ParticipantNameDto user;

    @BeforeEach
    void setUp() {
        this.user = new ParticipantNameDto(1L, "John", "Doe");
    }

    @Test
    void testEmptyConstructor() {
        // Arrange & Act
        ParticipantNameDto emptyUser = new ParticipantNameDto();

        // Assert
        Assertions.assertNotNull(emptyUser);
    }

    @Test
    void testEquals() {
        // Arrange
        ParticipantNameDto user2 = new ParticipantNameDto(1L, "John", "Doe");
        ParticipantNameDto user3 = new ParticipantNameDto(2L, "Jane", "Smith");

        // Testing equality with itself
        Assertions.assertEquals(user, user);

        // Testing equality with another object with the same values
        Assertions.assertEquals(user, user2);

        // Testing inequality with different IDs
        Assertions.assertNotEquals(user, user3);

        // Testing inequality with different first names
        ParticipantNameDto user4 = new ParticipantNameDto(1L, "John", "Smith");
        Assertions.assertNotEquals(user, user4);

        // Testing inequality with different last names
        ParticipantNameDto user5 = new ParticipantNameDto(1L, "Jane", "Doe");
        Assertions.assertNotEquals(user, user5);

        // Testing inequality with null
        Assertions.assertNotEquals(user, null);

        // Testing inequality with a different class
        Assertions.assertNotEquals(user, "Not a UserNameDto");
    }

    @Test
    void testHashCode() {
        // Arrange
        ParticipantNameDto user2 = new ParticipantNameDto(1L, "John", "Doe");

        // Hash code should be the same for objects with the same content
        Assertions.assertEquals(user.hashCode(), user2.hashCode());
    }

    @Test
    void getId() {
        // Act
        user.setId(1L);

        // Assert
        Assertions.assertEquals(1L, user.getId());
    }

    @Test
    void setId() {
        // Act
        user.setId(20L);

        // Assert
        Assertions.assertEquals(20L, user.getId());
    }

    @Test
    void getFirstName() {
        // Act & Assert
        Assertions.assertEquals("John", user.getFirstName());
    }

    @Test
    void setFirstName() {
        // Act
        user.setFirstName("Jane");

        // Assert
        Assertions.assertEquals("Jane", user.getFirstName());
    }

    @Test
    void getLastName() {
        // Act & Assert
        Assertions.assertEquals("Doe", user.getLastName());
    }

    @Test
    void setLastName() {
        // Act
        user.setLastName("Smith");

        // Assert
        Assertions.assertEquals("Smith", user.getLastName());
    }
}
