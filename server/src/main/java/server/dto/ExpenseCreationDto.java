package server.dto;

import server.dto.view.EventDetailsDto;
import server.dto.view.UserNameDto;

import java.util.Date;
import java.util.Objects;


public class ExpenseCreationDto {
    private String title;
    private double money;
    private UserNameDto author;
//    private Set<UserNameDto> debtors;
    private EventDetailsDto event;
    private Date date;

    /**
     * Default constructor
     */
    public ExpenseCreationDto(){}

    /**
     * Constructor injections
     * @param title title of the expense
     * @param money the amount in the expense
     * @param author the author of the expense
     * @param parentEvent the parent event of the expense
     * @param date the date of the expense
     */
    public ExpenseCreationDto(String title, double money, UserNameDto author,
                              EventDetailsDto parentEvent, Date date){
        this.title = title;
        this.money = money;
        this.author = author;
//        this.debtors = debtors;
        this.event = parentEvent;
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
    public UserNameDto getAuthor() {
        return author;
    }

    /**
     * Setter for author field
     * @param author the new author
     */
    public void setAuthor(UserNameDto author) {
        this.author = author;
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
                && Objects.equals(title, that.title) && Objects.equals(author, that.author);
    }

    /**
     * @return a hash code for the expenseCreationDto object
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, money, author);
    }
}
