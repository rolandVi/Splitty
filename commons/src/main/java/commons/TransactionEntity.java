package commons;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double money;
    private UserEntity sender;
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
}
