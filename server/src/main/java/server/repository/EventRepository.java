package server.repository;

import commons.EventEntity;
import commons.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

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

    /**
     * Retrieves all participant of the selected event
     * @param eventId the id of the event
     * @return the set of all participants of the event
     */
    @Query("SELECT e.participants FROM EventEntity e WHERE e.id=:eventId")
    Set<UserEntity> getParticipantsById(@Param(value = "eventId") long eventId);

//    /**
//     * Gets the event title using the id from the database
//     * @param id the ID of the event
//     * @return the title of the corresponding ID as a String
//     */
//    @Query("select e.title from EventEntity e where e.id=:id")
//    String getEventTitleById(@Param(value = "id") long id);

}
