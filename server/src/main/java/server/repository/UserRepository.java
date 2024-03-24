package server.repository;

import commons.EventEntity;
import commons.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Gets the event title using the id from the database
     * @param id the ID of the user
     * @return the firstname of the corresponding userID as a String
     */
    @Query("select u.firstName from UserEntity u where u.id=:id")
    String getUserNameById(@Param(value = "id") long id);

    /**
     * Query to get the events of a specific user
     * @param id the id of the user
     * @return the events
     */
    @Query("select u.events from UserEntity u where u.id=:id")
    Set<EventEntity> getEventsByUserId(@Param(value="id") long id);

    /**
     * Query to get the id of a specific user
     * @param email the email of the user
     * @return the id
     */
    @Query("select u.id from UserEntity u where u.email=:email")
    Long getUserIdByUserEmail(@Param(value="email") String email);

    /**
     * Checks if such an email exists
     * @param email the email
     * @return true if it exists and false otherwise
     */
    boolean existsByEmail(String email);

}
