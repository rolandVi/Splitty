package server.repository;

import commons.BankAccountEntity;
import commons.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {

    /**
     * Gets the holderInfo using the id from the database
     * @param id the ID of the event
     * @return the holder of the corresponding ID as a String
     */
    @Query("select b.holder from BankAccountEntity b where b.id=:id")
    String getEventTitleById(@Param(value = "id") long id);
}
