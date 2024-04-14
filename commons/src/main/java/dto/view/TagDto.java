package dto.view;

public class TagDto {
    private String tagType;
    private Long id;
    private String hexValue; // New attribute for hex value

    /**
     * empty constructor
     */
    public TagDto() {
    }

    /**
     * tagdto constructor
     * @param id id
     * @param tagType type
     * @param hexValue hexvalue
     */
    public TagDto(Long id, String tagType, String hexValue) {
        this.tagType = tagType;
        this.id = id;
        this.hexValue = hexValue;
    }

    /**
     * getter for the type
     * @return tagtype
     */
    public String getTagType() {
        return tagType;
    }

    /**
     * setter for the tagtype
     * @param tagType the tagtype
     */
    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    /**
     * getter for the id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * setter for the id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter for the hex
     * @return hexvalue
     */
    public String getHexValue() {
        return hexValue;
    }

    /**
     * setter for the hexvalue
     * @param hexValue hexvalue
     */
    public void setHexValue(String hexValue) {
        this.hexValue = hexValue;
    }
}
