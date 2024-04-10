package server.repository;

import commons.ExpenseEntity;
import commons.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query("SELECT e FROM TagEntity e WHERE e.tagType = :tagtype")
    TagEntity findByTagType(@Param("tagtype") String tagtype);

    @Modifying
    @Query("UPDATE TagEntity e SET e.tagType = :newTagType, e.hexValue = :newHexValue WHERE e.id = :tagId")
    void updateTag(@Param("tagId") Long tagId, @Param("newTagType") String newTagType, @Param("newHexValue") String newHexValue);

    @Modifying
    @Query("DELETE FROM TagEntity e WHERE e.id = :tagId")
    void deleteTagById(@Param("tagId") Long tagId);

}
