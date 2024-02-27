package server.repository;

import commons.EventEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Modifying
    @Query("update EventEntity e set e.title=:title where e.id=:id")
    EventEntity updateEventTitleById(@Param(value = "id") long id, @Param(value="title") String title);
}
