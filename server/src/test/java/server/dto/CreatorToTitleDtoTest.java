package server.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CreatorToTitleDtoTest {
    @Test
    void constructor_withArguments_setsFieldsCorrectly() {
        // Arrange
        Long id = 1L;
        String title = "Test Title";

        // Act
        CreatorToTitleDto dto = new CreatorToTitleDto(id, title);

        // Assert
        assertEquals(id, dto.getId());
        assertEquals(title, dto.getTitle());
    }

    @Test
    void defaultConstructor_setsFieldsToDefaultValues() {
        // Act
        CreatorToTitleDto dto = new CreatorToTitleDto();

        // Assert
        assertNull(dto.getId());
        assertNull(dto.getTitle());
    }

    @Test
    void setId_setsIdCorrectly() {
        // Arrange
        CreatorToTitleDto dto = new CreatorToTitleDto();
        Long id = 1L;

        // Act
        dto.setId(id);

        // Assert
        assertEquals(id, dto.getId());
    }

    @Test
    void setTitle_setsTitleCorrectly() {
        // Arrange
        CreatorToTitleDto dto = new CreatorToTitleDto();
        String title = "New Title";

        // Act
        dto.setTitle(title);

        // Assert
        assertEquals(title, dto.getTitle());
    }
}
