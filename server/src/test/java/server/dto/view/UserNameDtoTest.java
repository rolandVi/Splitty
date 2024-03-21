package server.dto.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNameDtoTest {

    private UserNameDto user;

    @BeforeEach
    void setUp() {
        this.user = new UserNameDto(1L, "John", "Doe");
    }

    @Test
    void testEmptyConstructor() {
        // Arrange & Act
        UserNameDto emptyUser = new UserNameDto();

        // Assert
        assertNotNull(emptyUser);
    }

    @Test
    void testEquals() {
        // Arrange
        UserNameDto user2 = new UserNameDto(1L, "John", "Doe");
        UserNameDto user3 = new UserNameDto(2L, "Jane", "Smith");

        // Testing equality with itself
        assertEquals(user, user);

        // Testing equality with another object with the same values
        assertEquals(user, user2);

        // Testing inequality with different IDs
        assertNotEquals(user, user3);

        // Testing inequality with different first names
        UserNameDto user4 = new UserNameDto(1L, "John", "Smith");
        assertNotEquals(user, user4);

        // Testing inequality with different last names
        UserNameDto user5 = new UserNameDto(1L, "Jane", "Doe");
        assertNotEquals(user, user5);

        // Testing inequality with null
        assertNotEquals(user, null);

        // Testing inequality with a different class
        assertNotEquals(user, "Not a UserNameDto");
    }

    @Test
    void testHashCode() {
        // Arrange
        UserNameDto user2 = new UserNameDto(1L, "John", "Doe");

        // Hash code should be the same for objects with the same content
        assertEquals(user.hashCode(), user2.hashCode());
    }

    @Test
    void getId() {
        // Act
        user.setId(1L);

        // Assert
        assertEquals(1L, user.getId());
    }

    @Test
    void setId() {
        // Act
        user.setId(20L);

        // Assert
        assertEquals(20L, user.getId());
    }

    @Test
    void getFirstName() {
        // Act & Assert
        assertEquals("John", user.getFirstName());
    }

    @Test
    void setFirstName() {
        // Act
        user.setFirstName("Jane");

        // Assert
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    void getLastName() {
        // Act & Assert
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void setLastName() {
        // Act
        user.setLastName("Smith");

        // Assert
        assertEquals("Smith", user.getLastName());
    }
}
