package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class CreatorToTitleDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;


    private String email;

    @NotBlank
    private String title;

    public CreatorToTitleDto() {
    }

    public CreatorToTitleDto(String firstName, String lastName, String email, String title) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public CreatorToTitleDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CreatorToTitleDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CreatorToTitleDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CreatorToTitleDto setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatorToTitleDto that = (CreatorToTitleDto) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, title);
    }
}
