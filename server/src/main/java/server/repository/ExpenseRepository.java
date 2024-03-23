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

    /**
     * Retrieves a list of expenses associated with a specific user ID
     * @param eventId - the ID of the event
     * @return a list of expenses associated with the specified event ID
     */
    @Query("SELECT e FROM ExpenseEntity e WHERE e.event.id = :eventId")
    List<ExpenseEntity> findExpensesByEventId(@Param("eventId") long eventId);

    /**
     * Finds sum of all expenses
     * @return The sum of all expenses
     */
    @Query("SELECT SUM(e.money) FROM ExpenseEntity e")
    Double getTotalSumOfExpenses();

}

