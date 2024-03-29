package dto.view;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Objects;

public class EventTitleDto {

    private Long id;
    @NotBlank
    private String title;

    private Date creationDate;
    private Date lastModifiedDate;

    /**
     * Empty constructor
     */
    public EventTitleDto() {
        this.creationDate = new Date(System.currentTimeMillis());
        this.lastModifiedDate = new Date(System.currentTimeMillis());
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
     * Getter for creation daet
     * @return The creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Setter for creation date
     * @param creationDate The creation Date
     * @return The creation Date
     */
    public EventTitleDto setCreationDate(Date creationDate){
        this.creationDate = creationDate;
        return this;
    }

    /**
     * Setter for last modified
     * @param lastModifiedDate The last modified Date
     * @return The last modified Date
     */
    public EventTitleDto setLastModifiedDate(Date lastModifiedDate){
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    /**
     * Getter for last modified Date
     * @return The last modified date
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
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
