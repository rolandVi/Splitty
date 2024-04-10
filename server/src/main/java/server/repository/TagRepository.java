package server.repository;


import commons.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    /**
     * queries on tagtype
     * @param tagtype the tagtpe
     * @return the entity corresponding to the tagtype
     */
    @Query("SELECT e FROM TagEntity e WHERE e.tagType = :tagtype")
    TagEntity findByTagType(@Param("tagtype") String tagtype);

    /**
     * updates a tag
     * @param tagId the id
     * @param newTagType new tagtype
     * @param newHexValue new hexvalue
     */
    @Modifying
    @Query("UPDATE TagEntity e SET e.tagType = :newTagType, " +
            "e.hexValue = :newHexValue WHERE e.id = :tagId")
    void updateTag(@Param("tagId") Long tagId,
                   @Param("newTagType") String newTagType,
                   @Param("newHexValue") String newHexValue);

    /**
     * deletes a tag on id
     * @param tagId the id
     */
    @Modifying
    @Query("DELETE FROM TagEntity e WHERE e.id = :tagId")
    void deleteTagById(@Param("tagId") Long tagId);

}
