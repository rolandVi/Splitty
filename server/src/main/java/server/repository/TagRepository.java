package server.repository;

import commons.ExpenseEntity;
import commons.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query("SELECT e FROM TagEntity e WHERE e.tagType = :tagtype")
    TagEntity findByTagType(@Param("tagtype") String tagtype);
}
