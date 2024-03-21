package server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatorToTitleDto {
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    public CreatorToTitleDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public CreatorToTitleDto() {
    }

    public Long getId() {
        return id;
    }

    public CreatorToTitleDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CreatorToTitleDto setTitle(String title) {
        this.title = title;
        return this;
    }
}
