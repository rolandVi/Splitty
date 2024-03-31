package dto.view;

import java.util.Objects;

public class TransactionDetailsDto {
    private String expenseTitle;
    private String receiverFirstName;
    private String receiverLastName;

    private double money;

    private String date;

    /**
     * Default constructor for TransactionDetailsDto.
     */
    public TransactionDetailsDto() {
    }

    /**
     * Constructs a TransactionDetailsDto with specified parameters.
     *
     * @param expenseTitle       Title of the expense
     * @param receiverFirstName  First name of the receiver
     * @param receiverLastName   Last name of the receiver
     * @param money              Amount of money involved in the transaction
     * @param date               Date of the transaction
     */
    public TransactionDetailsDto(String expenseTitle, String receiverFirstName,
                                 String receiverLastName, double money,
                                 String date) {
        this.expenseTitle = expenseTitle;
        this.receiverFirstName = receiverFirstName;
        this.receiverLastName = receiverLastName;
        this.money = money;
        this.date = date;
    }

    // Getters and setters for private fields

    /**
     * Retrieves the title of the expense.
     *
     * @return The title of the expense.
     */
    public String getExpenseTitle() {
        return expenseTitle;
    }

    /**
     * Sets the title of the expense.
     *
     * @param expenseTitle The title of the expense.
     * @return Current instance of TransactionDetailsDto for method chaining.
     */
    public TransactionDetailsDto setExpenseTitle(String expenseTitle) {
        this.expenseTitle = expenseTitle;
        return this;
    }

    /**
     * Retrieves the first name of the receiver.
     *
     * @return The first name of the receiver.
     */
    public String getReceiverFirstName() {
        return receiverFirstName;
    }

    /**
     * Sets the first name of the receiver.
     *
     * @param receiverFirstName The first name of the receiver.
     * @return Current instance of TransactionDetailsDto for method chaining.
     */
    public TransactionDetailsDto setReceiverFirstName(String receiverFirstName) {
        this.receiverFirstName = receiverFirstName;
        return this;
    }

    /**
     * Retrieves the last name of the receiver.
     *
     * @return The last name of the receiver.
     */
    public String getReceiverLastName() {
        return receiverLastName;
    }

    /**
     * Sets the last name of the receiver.
     *
     * @param receiverLastName The last name of the receiver.
     * @return Current instance of TransactionDetailsDto for method chaining.
     */
    public TransactionDetailsDto setReceiverLastName(String receiverLastName) {
        this.receiverLastName = receiverLastName;
        return this;
    }

    /**
     * Retrieves the amount of money involved in the transaction.
     *
     * @return The amount of money involved in the transaction.
     */
    public double getMoney() {
        return money;
    }

    /**
     * Sets the amount of money involved in the transaction.
     *
     * @param money The amount of money involved in the transaction.
     * @return Current instance of TransactionDetailsDto for method chaining.
     */
    public TransactionDetailsDto setMoney(double money) {
        this.money = money;
        return this;
    }

    /**
     * Retrieves the date of the transaction.
     *
     * @return The date of the transaction.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the transaction.
     *
     * @param date The date of the transaction.
     * @return Current instance of TransactionDetailsDto for method chaining.
     */
    public TransactionDetailsDto setDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Compares this TransactionDetailsDto to the specified object for equality.
     * Returns true if the objects are equal; false otherwise.
     *
     * @param o The object to compare this TransactionDetailsDto against.
     * @return True if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDetailsDto that = (TransactionDetailsDto) o;
        return Double.compare(that.money, money) == 0 &&
                Objects.equals(expenseTitle, that.expenseTitle) &&
                Objects.equals(receiverFirstName, that.receiverFirstName) &&
                Objects.equals(receiverLastName, that.receiverLastName) &&
                Objects.equals(date, that.date);
    }

    /**
     * Returns a hash code value for the object.
     * This method is supported for the benefit of hash tables such as those provided by HashMap.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(expenseTitle, receiverFirstName, receiverLastName, money, date);
    }
}
