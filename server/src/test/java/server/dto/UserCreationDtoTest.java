package server.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserCreationDtoTest {

    private UserCreationDto userCreationDto;

    @BeforeEach
    void setUp() {
        userCreationDto = new UserCreationDto("John", "Doe", "john.doe@example.com");
    }

    @Test
    void getFirstName() {
        assertEquals("John", userCreationDto.getFirstName());
    }

    @Test
    void setFirstName() {
        userCreationDto.setFirstName("Jane");
        assertEquals("Jane", userCreationDto.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", userCreationDto.getLastName());
    }

    @Test
    void setLastName() {
        userCreationDto.setLastName("Smith");
        assertEquals("Smith", userCreationDto.getLastName());
    }

    @Test
    void getEmail() {
        assertEquals("john.doe@example.com", userCreationDto.getEmail());
    }

    @Test
    void setEmail() {
        userCreationDto.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", userCreationDto.getEmail());
    }

    @Test
    void testEquals() {
        UserCreationDto sameDto = new UserCreationDto("John", "Doe", "john.doe@example.com");
        UserCreationDto differentDto = new UserCreationDto("Jane", "Smith", "jane.smith@example.com");

        // Test equality with itself
        assertEquals(userCreationDto, userCreationDto);

        // Test equality with an object having the same values
        assertEquals(userCreationDto, sameDto);

        // Test inequality with an object having different values
        assertNotEquals(userCreationDto, differentDto);

        // Test inequality with null
        assertNotEquals(userCreationDto, null);

        // Test inequality with a different class
        assertNotEquals(userCreationDto, "Not a UserCreationDto");
    }

    @Test
    void testHashCode() {
        UserCreationDto sameDto = new UserCreationDto("John", "Doe", "john.doe@example.com");

        // Hash code should be the same for objects with the same content
        assertEquals(userCreationDto.hashCode(), sameDto.hashCode());
    }
}
