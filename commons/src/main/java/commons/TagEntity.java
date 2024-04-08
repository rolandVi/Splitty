package commons;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class TagEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tagType;

    /**
     *
     * @param tagType the type that will be given to an event or expense
     *                f.e. food, holiday
     */
    public TagEntity(String tagType) {
        this.tagType = tagType;
    }

    /**
     * empty constructor
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
     * Getter for the tag
     * @return the tagType
     */
    public String getTagType() {
        return tagType;
    }

    /**
     * setter for the tag
     * @param tagType the tagType
     */
    public void setTagType(String tagType) {
        this.tagType = tagType;
    }
}
