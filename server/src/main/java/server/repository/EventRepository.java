package server.repository;

import commons.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    /**
     * Update EventEntity title by id
     * @param id the id of the event to update
     * @param title the new title
     * @return the updated event
     */
    @Modifying
    @Query("update EventEntity e set e.title=:title where e.id=:id")
    void updateEventTitleById(@Param(value = "id") long id,
                                               @Param(value="title") String title);
    @Query("select e.title from EventEntity e where e.id=:id")
    Optional<EventEntity> getEventTitleById(@Param(value = "id") long id);

}
