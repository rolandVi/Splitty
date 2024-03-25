package server.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Objects;
import java.util.Set;


public class ExpenseCreationDto {
    //@NotEmpty
    private String title;
    //@NotNull
    private Double money;
    //@NotNull
    private Long authorId;
    //@NotNull
    private Set<Long> debtorsIds;
    //@NotNull
    private Long eventId;
    //@NotNull
    private Date date;

    /**
     * Default constructor
     */
    public ExpenseCreationDto(){}

    /**
     * Constructor injections
     * @param title title of the expense
     * @param money the amount in the expense
     * @param authorId the id of the author of the expense
     * @param debtorsIds the set of debtors' ids
     * @param parentEventId the id of the parent event of the expense
     * @param date the date of the expense
     */
    public ExpenseCreationDto(String title, Double money, Long authorId,
                              Set<Long> debtorsIds, Long parentEventId, Date date){
        this.title = title;
        this.money = money;
        this.authorId = authorId;
        this.debtorsIds = debtorsIds;
        this.eventId = parentEventId;
        this.date = date;
    }

    /**
     * Getter for the title field
     * @return the title value
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the title field
     * @param title the new title value
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for the money field
     * @return the money value
     */
    public double getMoney() {
        return money;
    }

    /**
     * Setter for the money field
     * @param money the new money value
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * Getter for the author
     * @return the UserNameDto author object
     */
    public long getAuthorId() {
        return authorId;
    }

    /**
     * Setter for author field
     * @param author the new author
     */
    public void setAuthorId(long author) {
        this.authorId = authorId;
    }

    /**
     * Getter for the debtorsIds field
     * @return the set of debtors ids
     */
    public Set<Long> getDebtorsIds() {
        return debtorsIds;
    }

    /**
     * Setter for the debtorsIds field
     * @param debtorsIds new value of the debtorsIds field
     */
    public void setDebtorsIds(Set<Long> debtorsIds) {
        this.debtorsIds = debtorsIds;
    }

    /**
     * Getter for eventId
     * @return the event id
     */
    public long getEventId() {
        return eventId;
    }

    /**
     * Setter for the event id
     * @param eventId new event id
     */
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    /**
     * Getter for the date of the expense
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for the date of the expense
     * @param date the new date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Equals method
     * @param o the object to compare
     * @return boolean whether the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseCreationDto that = (ExpenseCreationDto) o;
        return Double.compare(money, that.money) == 0
                && Objects.equals(title, that.title) && Objects.equals(authorId, that.authorId);
    }

    /**
     * @return a hash code for the expenseCreationDto object
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, money, authorId);
    }
}
