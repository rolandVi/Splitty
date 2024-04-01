package dto;

import dto.UserCreationDto;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals("John", userCreationDto.getFirstName());
    }

    @Test
    void setFirstName() {
        userCreationDto.setFirstName("Jane");
        Assertions.assertEquals("Jane", userCreationDto.getFirstName());
    }

    @Test
    void getLastName() {
        Assertions.assertEquals("Doe", userCreationDto.getLastName());
    }

    @Test
    void setLastName() {
        userCreationDto.setLastName("Smith");
        Assertions.assertEquals("Smith", userCreationDto.getLastName());
    }

    @Test
    void getEmail() {
        Assertions.assertEquals("john.doe@example.com", userCreationDto.getEmail());
    }

    @Test
    void setEmail() {
        userCreationDto.setEmail("jane.smith@example.com");
        Assertions.assertEquals("jane.smith@example.com", userCreationDto.getEmail());
    }

    @Test
    void testEquals() {
        UserCreationDto sameDto = new UserCreationDto("John", "Doe", "john.doe@example.com");
        UserCreationDto differentDto = new UserCreationDto("Jane", "Smith", "jane.smith@example.com");

        // Test equality with itself
        Assertions.assertEquals(userCreationDto, userCreationDto);

        // Test equality with an object having the same values
        Assertions.assertEquals(userCreationDto, sameDto);

        // Test inequality with an object having different values
        Assertions.assertNotEquals(userCreationDto, differentDto);

        // Test inequality with null
        Assertions.assertNotEquals(userCreationDto, null);

        // Test inequality with a different class
        Assertions.assertNotEquals(userCreationDto, "Not a UserCreationDto");
    }

    @Test
    void testHashCode() {
        UserCreationDto sameDto = new UserCreationDto("John", "Doe", "john.doe@example.com");

        // Hash code should be the same for objects with the same content
        Assertions.assertEquals(userCreationDto.hashCode(), sameDto.hashCode());
    }
}
