package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionEntityTest {
    private ParticipantEntity sender;
    private ParticipantEntity receiver;

    private ExpenseEntity expense;

    private EventEntity event;

    private Date date;
    private TransactionEntity transaction;

    private TagEntity tag;
    @BeforeEach
    void setup() {
        this.date=new Date();
        this.sender = new ParticipantEntity(1L, "Sender", "LastName",
                "email@gmail.com", new EventEntity(), new BankAccountEntity());
        this.receiver = new ParticipantEntity(2L, "Receiver", "LastName",
                "email@gmail.com", new EventEntity(), new BankAccountEntity());
        this.event=new EventEntity();
        this.expense= new ExpenseEntity(1L, 20d, receiver,
                new HashSet<>(List.of(this.sender)), "title", date, event, tag);
        this.transaction = new TransactionEntity(1234L, 19.99, sender, receiver, expense, date);
    }

    @Test
    void getId() {
        assertEquals(1234L, transaction.getId());
    }

    @Test
    void getMoney() {
        assertEquals(19.99, transaction.getMoney());
    }

    @Test
    void getSender() {
        assertEquals(sender, transaction.getSender());
    }

    @Test
    void getReceiver() {
        assertEquals(receiver, transaction.getReceiver());
    }

    @Test
    void getExpense() {
        assertEquals(expense, transaction.getExpense());
    }


    @Test
    void getDate() {
        assertEquals(date, transaction.getDate());
    }

    @Test
    void testEquals() {
        TransactionEntity transaction2 = new TransactionEntity(1234L, 19.99, sender, receiver, expense, date);
        assertEquals(transaction, transaction2);
    }

    @Test
    void testHashCode() {
        TransactionEntity transaction2 = new TransactionEntity(1234L, 19.99, sender, receiver, expense, date);
        assertEquals(transaction.hashCode(), transaction2.hashCode());
    }

    @Test
    void testEmptyConstructor() {
        TransactionEntity emptyTransaction = new TransactionEntity();
        assertNotNull(emptyTransaction);
    }

    @Test
    void testSetDate(){
        TransactionEntity emptyTransaction = new TransactionEntity();
        emptyTransaction.setDate(new Date());

        assertNotNull(emptyTransaction.getDate());
    }

    @Test
    void testSetId(){
        TransactionEntity emptyTransaction = new TransactionEntity();
        emptyTransaction.setId(1L);

        assertEquals(1L, (long) emptyTransaction.getId());
    }
}