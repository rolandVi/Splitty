package server.repository;

import commons.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Gets the event title using the id from the database
     * @param id the ID of the user
     * @return the firstname of the corresponding userID as a String
     */
    @Query("select u.firstName from UserEntity u where u.id=:id")
    String getUserNameById(@Param(value = "id") long id);

}
