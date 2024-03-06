package server.repository;

import commons.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {


    /**
     * Retrieves a list of expenses associated with a specific user ID using custom JPQL query.
     * @param userId the ID of the user
     * @return a list of expenses associated with the specified user ID
     */
    @Query("SELECT e FROM ExpenseEntity e JOIN e.debtors d WHERE d.id = :userId")
    List<ExpenseEntity> findExpensesByUserId(@Param("userId") Long userId);
}

