package dto.view;

public class TagDto {
    private String tagType;
    private Long id;
    private String hexValue; // New attribute for hex value

    // Constructors
    public TagDto() {
    }

    public TagDto(Long id, String tagType, String hexValue) {
        this.tagType = tagType;
        this.id = id;
        this.hexValue = hexValue;
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

    public String getHexValue() {
        return hexValue;
    }

    public void setHexValue(String hexValue) {
        this.hexValue = hexValue;
    }
}
