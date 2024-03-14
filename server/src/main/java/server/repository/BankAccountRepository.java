package server.repository;

import commons.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {

    /**
     * Gets the holderInfo using the id from the database
     * @param id the ID of the bankAccount
     * @return the holder of the corresponding ID as a String
     */
    @Query("select b.holder from BankAccountEntity b where b.id=:id")
    String getEventTitleById(@Param(value = "id") long id);

    /**
     * Checks if such iban exists
     * @param iban teh iban
     * @return true if it exits and false otherwise
     */
    boolean existsByIban(String iban);
}
