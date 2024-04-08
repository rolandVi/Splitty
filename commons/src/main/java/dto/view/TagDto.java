package dto.view;

public class TagDto {
    private String tagType;

    private Long id;
    // Constructors
    public TagDto() {
    }

    public TagDto(Long id, String tagType) {
        this.tagType = tagType;
        this.id = id;
    }

    // Getters and Setters
    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
