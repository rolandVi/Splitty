package server.dto.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EventOverviewDtoTest {

    private EventOverviewDto event1;
    private EventOverviewDto event2;

    @BeforeEach
    void setUp() {
        event1 = new EventOverviewDto(1L, "Birthday Party", "BDAY123", new Date(), new Date());
        event2 = new EventOverviewDto(1L, "Birthday Party", "BDAY123", new Date(), new Date());
    }

    @Test
    void getId() {
        // Arrange & Act & Assert
        assertEquals(1L, event1.getId());
    }

    @Test
    void setId() {
        // Arrange
        event1.setId(2L);

        // Act & Assert
        assertEquals(2L, event1.getId());
    }

    @Test
    void getTitle() {
        // Arrange & Act & Assert
        assertEquals("Birthday Party", event1.getTitle());
    }

    @Test
    void setTitle() {
        // Arrange
        event1.setTitle("Wedding Celebration");

        // Act & Assert
        assertEquals("Wedding Celebration", event1.getTitle());
    }

    @Test
    void getInviteCode() {
        // Arrange & Act & Assert
        assertEquals("BDAY123", event1.getInviteCode());
    }

    @Test
    void setInviteCode() {
        // Arrange
        event1.setInviteCode("WEDD789");

        // Act & Assert
        assertEquals("WEDD789", event1.getInviteCode());
    }

    @Test
    void testEquals() {
        // Arrange
        EventOverviewDto sameEvent = new EventOverviewDto
                (1L, "Birthday Party", "BDAY123", new Date(), new Date());
        EventOverviewDto differentEvent = new EventOverviewDto
                (2L, "Wedding Celebration", "WEDD789", new Date(), new Date());

        // Act & Assert
        assertEquals(event1, event2);
        assertEquals(event1, sameEvent);
        assertNotEquals(event1, differentEvent);
    }

    @Test
    void testHashCode() {
        // Arrange
        EventOverviewDto sameEvent = new EventOverviewDto
                (1L, "Birthday Party", "BDAY123", new Date(), new Date());

        // Act & Assert
        assertEquals(event1.hashCode(), event2.hashCode());
        assertEquals(event1.hashCode(), sameEvent.hashCode());
    }
}
