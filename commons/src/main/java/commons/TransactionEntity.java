package commons;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private double money;
    @ManyToOne
    private UserEntity sender;
    @ManyToOne
    private UserEntity receiver;

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
     */
    public TransactionEntity(Long id, double money, UserEntity sender, UserEntity receiver) {
        this.id = id;
        this.money = money;
        this.sender = sender;
        this.receiver = receiver;
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
                && Objects.equals(receiver, that.receiver);
    }

    /**
     * Generates the hashcode value of this transaction
     * @return integer hashcode value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, money, sender, receiver);
    }
}
