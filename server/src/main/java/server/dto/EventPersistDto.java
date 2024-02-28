package server.dto;

public class EventPersistDto {
    private String title;

    /**
     * Constructor
     * @param title the title to give the new instance
     */
    public EventPersistDto(String title) {
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
     * Title setter
     * @param title the new title
     * @return the new Dto with changed title
     */
    public EventPersistDto setTitle(String title) {
        this.title = title;
        return this;
    }
}
