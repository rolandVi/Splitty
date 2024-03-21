package server.repository;

import commons.EventEntity;
import commons.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
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

//    /**
//     * Gets the event title using the id from the database
//     * @param id the ID of the event
//     * @return the title of the corresponding ID as a String
//     */
//    @Query("select e.title from EventEntity e where e.id=:id")
//    String getEventTitleById(@Param(value = "id") long id);

    @Query("select e.participants from EventEntity e where e.id=:id")
    Set<UserEntity> findEventParticipants(@Param(value = "id") long id);

    /**
     * find the event entity with eh given invite code
     * @param inviteCode the invite code
     * @return the event entity
     */
    Optional<EventEntity> findEventEntityByInviteCode(String inviteCode);

}
