package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatorToTitleDto {
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    /**
     * Constructs a new CreatorToTitleDto object with the specified ID and title.
     *
     * @param id    The ID of the creator.
     * @param title The title associated with the creator.
     */
    public CreatorToTitleDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * Constructs a new CreatorToTitleDto object with default values.
     */
    public CreatorToTitleDto() {
    }

    /**
     * Returns the ID of the creator.
     *
     * @return The ID of the creator.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the creator.
     *
     * @param id The ID of the creator.
     * @return This CreatorToTitleDto object.
     */
    public CreatorToTitleDto setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Returns the title associated with the creator.
     *
     * @return The title associated with the creator.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title associated with the creator.
     *
     * @param title The title associated with the creator.
     * @return This CreatorToTitleDto object.
     */
    public CreatorToTitleDto setTitle(String title) {
        this.title = title;
        return this;
    }
}
