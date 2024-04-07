package dto.view;

public class TagDto {
    private String tagType;

    // Constructors
    public TagDto() {
    }

    public TagDto(String tagType) {
        this.tagType = tagType;
    }

    // Getters and Setters
    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }
}
