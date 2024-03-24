package server.dto.view;

import java.util.Date;
import java.util.Objects;

public class EventOverviewDto {

    private long id;

    private String title;

    private String inviteCode;

    private Date creationDate;
    private Date lastModifiedDate;


    /**
     * Constructs a new EventOverviewDto with the specified ID, title, and invite code.
     *
     * @param id         The unique identifier of the event.
     * @param title      The title of the event.
     * @param inviteCode The invite code associated with the event.
     * @param creationDate The creation Date
     * @param lastModifiedDate the last modified date
     */
    public EventOverviewDto(long id, String title, String inviteCode,
                            Date creationDate, Date lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.inviteCode = inviteCode;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Empty constructor
     */
    public EventOverviewDto() {
    }

    /**
     * Retrieves the ID of the event.
     *
     * @return The ID of the event.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the event.
     *
     * @param id The ID of the event to set.
     * @return This EventOverviewDto object.
     */
    public EventOverviewDto setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Retrieves the title of the event.
     *
     * @return The title of the event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the event.
     *
     * @param title The title of the event to set.
     * @return This EventOverviewDto object.
     */
    public EventOverviewDto setTitle(String title) {
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
    public EventOverviewDto setCreationDate(Date creationDate){
        this.creationDate = creationDate;
        return this;
    }

    /**
     * Setter for last modified
     * @param lastModifiedDate The last modified Date
     * @return The last modified Date
     */
    public EventOverviewDto setLastModifiedDate(Date lastModifiedDate){
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
     * Retrieves the invite code associated with the event.
     *
     * @return The invite code associated with the event.
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * Sets the invite code associated with the event.
     *
     * @param inviteCode The invite code to set.
     * @return This EventOverviewDto object.
     */
    public EventOverviewDto setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The object to compare with.
     * @return true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventOverviewDto that = (EventOverviewDto) o;
        return id == that.id && Objects.equals(title, that.title)
                && Objects.equals(inviteCode, that.inviteCode);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, inviteCode);
    }
}

