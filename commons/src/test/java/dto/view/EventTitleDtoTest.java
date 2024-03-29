package dto.view;

import dto.view.EventTitleDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTitleDtoTest {

    @Test
    void testEmptyConstructor(){
        new EventTitleDto();
    }

    @Test
    void getTitle() {
        String title = "Test Event";
        EventTitleDto eventTitleDto = new EventTitleDto(title);

        Assertions.assertEquals(title, eventTitleDto.getTitle());
    }

    @Test
    void getId() {
        Long id = 123L;
        EventTitleDto eventTitleDto = new EventTitleDto("Test Event");
        eventTitleDto.setId(id);

        Assertions.assertEquals(id, eventTitleDto.getId());
    }

    @Test
    void setId() {
        Long id = 123L;
        EventTitleDto eventTitleDto = new EventTitleDto("Test Event");
        eventTitleDto.setId(id);

        Assertions.assertEquals(id, eventTitleDto.getId());
    }

    @Test
    void setTitle() {
        String title = "Updated Event";
        EventTitleDto eventTitleDto = new EventTitleDto("Test Event");
        eventTitleDto.setTitle(title);

        Assertions.assertEquals(title, eventTitleDto.getTitle());
    }

    @Test
    void testEquals() {
        EventTitleDto event1 = new EventTitleDto("Test Event");
        EventTitleDto event2 = new EventTitleDto("Test Event");

        Assertions.assertEquals(event1, event2);
    }

    @Test
    void testHashCode() {
        EventTitleDto event1 = new EventTitleDto("Test Event");
        EventTitleDto event2 = new EventTitleDto("Test Event");

        Assertions.assertEquals(event1.hashCode(), event2.hashCode());
    }
}
