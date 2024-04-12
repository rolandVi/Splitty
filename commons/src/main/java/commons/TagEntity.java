package commons;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tagType;

    private String hexValue; // New attribute for hex value

    /**
     * Constructor with tag type and hex value
     * @param tagType the type that will be given to an event or expense
     *                f.e. food, holiday
     * @param hexValue the hex value associated with the tag
     */
    public TagEntity(String tagType, String hexValue) {
        this.tagType = tagType;
        this.hexValue = hexValue;
    }

    /**
     * Empty constructor
     */
    public TagEntity() {
    }

    /**
     * Getter for the id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for the tag type
     * @return the tagType
     */
    public String getTagType() {
        return tagType;
    }

    /**
     * Setter for the tag type
     * @param tagType the tagType to set
     */
    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    /**
     * Getter for the hex value
     * @return the hexValue
     */
    public String getHexValue() {
        return hexValue;
    }

    /**
     * Setter for the hex value
     * @param hexValue the hexValue to set
     */
    public void setHexValue(String hexValue) {
        this.hexValue = hexValue;
    }
}