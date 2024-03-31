package dto.view;

import dto.view.EventOverviewDto;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(1L, event1.getId());
    }

    @Test
    void setId() {
        // Arrange
        event1.setId(2L);

        // Act & Assert
        Assertions.assertEquals(2L, event1.getId());
    }

    @Test
    void getTitle() {
        // Arrange & Act & Assert
        Assertions.assertEquals("Birthday Party", event1.getTitle());
    }

    @Test
    void setTitle() {
        // Arrange
        event1.setTitle("Wedding Celebration");

        // Act & Assert
        Assertions.assertEquals("Wedding Celebration", event1.getTitle());
    }

    @Test
    void getInviteCode() {
        // Arrange & Act & Assert
        Assertions.assertEquals("BDAY123", event1.getInviteCode());
    }

    @Test
    void setInviteCode() {
        // Arrange
        event1.setInviteCode("WEDD789");

        // Act & Assert
        Assertions.assertEquals("WEDD789", event1.getInviteCode());
    }

    @Test
    void testEquals() {
        // Arrange
        EventOverviewDto sameEvent = new EventOverviewDto
                (1L, "Birthday Party", "BDAY123", new Date(), new Date());
        EventOverviewDto differentEvent = new EventOverviewDto
                (2L, "Wedding Celebration", "WEDD789", new Date(), new Date());

        // Act & Assert
        Assertions.assertEquals(event1, event2);
        Assertions.assertEquals(event1, sameEvent);
        Assertions.assertNotEquals(event1, differentEvent);
    }

    @Test
    void testHashCode() {
        // Arrange
        EventOverviewDto sameEvent = new EventOverviewDto
                (1L, "Birthday Party", "BDAY123", new Date(), new Date());

        // Act & Assert
        Assertions.assertEquals(event1.hashCode(), event2.hashCode());
        Assertions.assertEquals(event1.hashCode(), sameEvent.hashCode());
    }
}
