package commons.dto.view;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class EventTitleDto {

    private Long id;
    @NotBlank
    @Size(min = 1, max = 100, message = "event title should be max 100 characters")
    private String title;

    /**
     * Empty constructor
     */
    public EventTitleDto() {
    }

    /**
     * Constructor
     * @param title the title to give the new instance
     */
    public EventTitleDto(String title) {
        this.title = title;
    }

    /**
     * Title getter
     * @return the title of the event
     */
    public String getTitle() {
        return title;
    }

    /**
     * Id getter
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Id setter
     * @param id the new id
     * @return the new event
     */
    public EventTitleDto setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Title setter
     * @param title the new title
     * @return the new Dto with changed title
     */
    public EventTitleDto setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Equals to check for equality
     * @param o the object to compare to
     * @return true if they are equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventTitleDto that = (EventTitleDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    /**
     * Generating hash code
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
