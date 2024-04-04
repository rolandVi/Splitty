package server.repository;

import commons.EventEntity;
import commons.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {
    /**
     * Query to get the events of a specific user
     * @param id the id of the user
     * @return the events
     */
    @Query("select p.event from ParticipantEntity p where p.id=:id")
    EventEntity getEventByUserId(@Param(value="id") long id);

}
