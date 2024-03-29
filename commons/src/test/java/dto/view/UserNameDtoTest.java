package dto.view;

import dto.view.UserNameDto;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertNotNull(emptyUser);
    }

    @Test
    void testEquals() {
        // Arrange
        UserNameDto user2 = new UserNameDto(1L, "John", "Doe");
        UserNameDto user3 = new UserNameDto(2L, "Jane", "Smith");

        // Testing equality with itself
        Assertions.assertEquals(user, user);

        // Testing equality with another object with the same values
        Assertions.assertEquals(user, user2);

        // Testing inequality with different IDs
        Assertions.assertNotEquals(user, user3);

        // Testing inequality with different first names
        UserNameDto user4 = new UserNameDto(1L, "John", "Smith");
        Assertions.assertNotEquals(user, user4);

        // Testing inequality with different last names
        UserNameDto user5 = new UserNameDto(1L, "Jane", "Doe");
        Assertions.assertNotEquals(user, user5);

        // Testing inequality with null
        Assertions.assertNotEquals(user, null);

        // Testing inequality with a different class
        Assertions.assertNotEquals(user, "Not a UserNameDto");
    }

    @Test
    void testHashCode() {
        // Arrange
        UserNameDto user2 = new UserNameDto(1L, "John", "Doe");

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
