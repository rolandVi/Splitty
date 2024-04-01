package commons;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private double money;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(nullable = false)
    private UserEntity sender;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(nullable = false)
    private UserEntity receiver;

    @ManyToOne(cascade = CascadeType.DETACH)
    private ExpenseEntity expense;

    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Default constructor for JBA
     */
    public TransactionEntity(){

    }

    /**
     * Regular constructor TransactionEntity requiring all fields
     * @param id unique ID to identify a transaction
     * @param money amount of money that has been transferred
     * @param sender user sending the money
     * @param receiver user receiver money from sender
     * @param expense the expense of the transaction
     * @param date the date of the transaction
     */
    public TransactionEntity(Long id, double money, UserEntity sender,
                             UserEntity receiver, ExpenseEntity expense,
                             Date date) {
        this.id = id;
        this.money = money;
        this.sender = sender;
        this.receiver = receiver;
        this.expense = expense;
        this.date = date;
    }

    /**
     * Getter for ID of the transition
     * @return unique ID number
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for money that has been transferred
     * @return amount of money
     */
    public double getMoney() {
        return money;
    }

    /**
     * Getter for the user sending the money
     * @return sender as user entity
     */
    public UserEntity getSender() {
        return sender;
    }
    /**
     * Getter for the user receiving the money
     * @return receiver as user entity
     */
    public UserEntity getReceiver() {
        return receiver;
    }

    /**
     * Getter for the expanse
     * @return teh expanse entity
     */
    public ExpenseEntity getExpense() {
        return expense;
    }

    /**
     * Setter for the ID of the transaction.
     * @param id unique ID to identify a transaction
     * @return the TransactionEntity object with the updated ID
     */
    public TransactionEntity setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Setter for the amount of money that has been transferred.
     * @param money amount of money
     * @return the TransactionEntity object with the updated money amount
     */
    public TransactionEntity setMoney(double money) {
        this.money = money;
        return this;
    }

    /**
     * Setter for the user sending the money.
     * @param sender user sending the money
     * @return the TransactionEntity object with the updated sender
     */
    public TransactionEntity setSender(UserEntity sender) {
        this.sender = sender;
        return this;
    }

    /**
     * Setter for the user receiving the money.
     * @param receiver user receiving the money
     * @return the TransactionEntity object with the updated receiver
     */
    public TransactionEntity setReceiver(UserEntity receiver) {
        this.receiver = receiver;
        return this;
    }

    /**
     * Setter for the expense associated with the transaction.
     * @param expense the expense entity
     * @return the TransactionEntity object with the updated expense
     */
    public TransactionEntity setExpense(ExpenseEntity expense) {
        this.expense = expense;
        return this;
    }

    /**
     * Getter for the date
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for the date
     * @param date the new date
     * @return the new transaction
     */
    public TransactionEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Compares this transaction to the object o
     * @param o the object to compare if it is equal to this
     * @return returns true if they are equal and false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return Double.compare(money, that.money) == 0
                && Objects.equals(id, that.id)
                && Objects.equals(sender, that.sender)
                && Objects.equals(receiver, that.receiver)
                && Objects.equals(expense, that.expense)
                && Objects.equals(date, that.date);
    }

    /**
     * Generates the hashcode value of this transaction
     * @return integer hashcode value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, money, sender, receiver, expense, date);
    }
}
