package server.dto.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNameDtoTest {

    @Test
    void testEquals() {
        UserNameDto user1 = new UserNameDto(1L, "John", "Doe");
        UserNameDto user2 = new UserNameDto(1L, "John", "Doe");
        UserNameDto user3 = new UserNameDto(2L, "Jane", "Smith");

        // Testing equality with itself
        assertEquals(user1, user1);

        // Testing equality with another object with the same values
        assertEquals(user1, user2);

        // Testing inequality with different IDs
        assertNotEquals(user1, user3);

        // Testing inequality with different first names
        UserNameDto user4 = new UserNameDto(1L, "John", "Smith");
        assertNotEquals(user1, user4);

        // Testing inequality with different last names
        UserNameDto user5 = new UserNameDto(1L, "Jane", "Doe");
        assertNotEquals(user1, user5);

        // Testing inequality with null
        assertNotEquals(user1, null);
    }

    @Test
    void testHashCode() {
        UserNameDto user1 = new UserNameDto(1L, "John", "Doe");
        UserNameDto user2 = new UserNameDto(1L, "John", "Doe");

        // Hash code should be the same for objects with the same content
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
