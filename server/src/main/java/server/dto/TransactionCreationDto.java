package server.dto;


import java.util.Objects;

public class TransactionCreationDto {
    private Long senderId;

    private Long receiverId;

    private Long expenseId;

    /**
     * Constructs a new TransactionDetailsDto object
     * with the provided sender, receiver, and expense IDs.
     *
     * @param senderId   the ID of the sender
     * @param receiverId the ID of the receiver
     * @param expenseId  the ID of the expense
     */
    public TransactionCreationDto(Long senderId, Long receiverId, Long expenseId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.expenseId = expenseId;
    }

    /**
     * Retrieves the sender ID.
     *
     * @return the sender ID
     */
    public Long getSenderId() {
        return senderId;
    }

    /**
     * Sets the sender ID.
     *
     * @param senderId the ID of the sender
     * @return this TransactionDetailsDto instance
     */
    public TransactionCreationDto setSenderId(Long senderId) {
        this.senderId = senderId;
        return this;
    }

    /**
     * Retrieves the receiver ID.
     *
     * @return the receiver ID
     */
    public Long getReceiverId() {
        return receiverId;
    }

    /**
     * Sets the receiver ID.
     *
     * @param receiverId the ID of the receiver
     * @return this TransactionDetailsDto instance
     */
    public TransactionCreationDto setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    /**
     * Retrieves the expense ID.
     *
     * @return the expense ID
     */
    public Long getExpenseId() {
        return expenseId;
    }

    /**
     * Sets the expense ID.
     *
     * @param expenseId the ID of the expense
     * @return this TransactionDetailsDto instance
     */
    public TransactionCreationDto setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
        return this;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two TransactionDetailsDto objects are considered equal if they have the same sender ID,
     * receiver ID, and expense ID.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionCreationDto that = (TransactionCreationDto) o;
        return Objects.equals(senderId, that.senderId)
                && Objects.equals(receiverId, that.receiverId)
                && Objects.equals(expenseId, that.expenseId);
    }

    /**
     * Returns a hash code value for the object.
     * This method is supported for the benefit of hash tables
     * such as those provided by HashMap.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(senderId, receiverId, expenseId);
    }
}
