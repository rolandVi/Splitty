package server.repository;

import commons.ExpenseEntity;
import commons.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<ExpenseEntity, Long> {

    @Query("SELECT e FROM TagEntity e WHERE e.tagType = :tagtype")
    TagEntity findByTagType(@Param("tagtype") String tagtype);
}
