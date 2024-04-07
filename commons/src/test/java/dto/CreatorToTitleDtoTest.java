package dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreatorToTitleDtoTest {
    @Test
    public void testDefaultConstructor() {
        CreatorToTitleDto dto = new CreatorToTitleDto();
        assertNotNull(dto);
        assertNull(dto.getFirstName());
        assertNull(dto.getLastName());
        assertNull(dto.getEmail());
        assertNull(dto.getTitle());
    }

    @Test
    public void testParameterizedConstructorAndGetters() {
        CreatorToTitleDto dto = new CreatorToTitleDto("John", "Doe", "john@example.com", "Mr.");
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals("Mr.", dto.getTitle());
    }

    @Test
    public void testSetFirstName() {
        CreatorToTitleDto dto = new CreatorToTitleDto();
        dto.setFirstName("Jane");
        assertEquals("Jane", dto.getFirstName());
    }

    @Test
    public void testSetLastName() {
        CreatorToTitleDto dto = new CreatorToTitleDto();
        dto.setLastName("Smith");
        assertEquals("Smith", dto.getLastName());
    }

    @Test
    public void testSetEmail() {
        CreatorToTitleDto dto = new CreatorToTitleDto();
        dto.setEmail("jane@example.com");
        assertEquals("jane@example.com", dto.getEmail());
    }

    @Test
    public void testSetTitle() {
        CreatorToTitleDto dto = new CreatorToTitleDto();
        dto.setTitle("Ms.");
        assertEquals("Ms.", dto.getTitle());
    }

    @Test
    public void testEqualsAndHashCode() {
        CreatorToTitleDto dto1 = new CreatorToTitleDto("John", "Doe", "john@example.com", "Mr.");
        CreatorToTitleDto dto2 = new CreatorToTitleDto("John", "Doe", "john@example.com", "Mr.");
        CreatorToTitleDto dto3 = new CreatorToTitleDto("Jane", "Smith", "jane@example.com", "Ms.");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto2, dto3);

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
        assertNotEquals(dto2.hashCode(), dto3.hashCode());
    }
}
