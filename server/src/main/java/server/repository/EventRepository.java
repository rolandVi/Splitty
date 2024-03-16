package server.repository;

import commons.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    /**
     * Update EventEntity title by id
     * @param id the id of the event to update
     * @param title the new title
     */
    @Modifying
    @Query("update EventEntity e set e.title=:title where e.id=:id")
    void updateEventTitleById(@Param(value = "id") long id,
                                               @Param(value="title") String title);

//    /**
//     * Gets the event title using the id from the database
//     * @param id the ID of the event
//     * @return the title of the corresponding ID as a String
//     */
//    @Query("select e.title from EventEntity e where e.id=:id")
//    String getEventTitleById(@Param(value = "id") long id);

}
