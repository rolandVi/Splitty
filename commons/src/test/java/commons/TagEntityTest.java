package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TagEntityTest {

    private TagEntity tagEntity;

    @BeforeEach
    void setUp() {
        tagEntity = new TagEntity();
    }

    @Test
    void testGetId() {
        assertNull(tagEntity.getId());
    }

    @Test
    void testGetTagType() {
        tagEntity.setTagType("food");
        assertEquals("food", tagEntity.getTagType());
    }

    @Test
    void testSetTagType() {
        tagEntity.setTagType("holiday");
        assertEquals("holiday", tagEntity.getTagType());
    }

    @Test
    void testGetHexValue() {
        tagEntity.setHexValue("#FFFFFF");
        assertEquals("#FFFFFF", tagEntity.getHexValue());
    }

    @Test
    void testSetHexValue() {
        tagEntity.setHexValue("#000000");
        assertEquals("#000000", tagEntity.getHexValue());
    }
}
