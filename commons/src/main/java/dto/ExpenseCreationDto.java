package dto;




import dto.view.UserNameDto;

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
    private Set<UserNameDto> debtors;
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
     * @param debtors the set of debtors' ids
     * @param parentEventId the id of the parent event of the expense
     * @param date the date of the expense
     */
    public ExpenseCreationDto(String title, Double money, Long authorId,
                              Set<UserNameDto> debtors, Long parentEventId, Date date){
        this.title = title;
        this.money = money;
        this.authorId = authorId;
        this.debtors = debtors;
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
    public Double getMoney() {
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
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * Setter for author field
     * @param authorId the new author
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * Getter for the debtorsIds field
     * @return the set of debtors ids
     */
    public Set<UserNameDto> getDebtors() {
        return debtors;
    }

    /**
     * Setter for the debtorsIds field
     * @param debtors new value of the debtorsIds field
     */
    public void setDebtorsIds(Set<UserNameDto> debtors) {
        this.debtors = debtors;
    }

    /**
     * Getter for eventId
     * @return the event id
     */
    public Long getEventId() {
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
