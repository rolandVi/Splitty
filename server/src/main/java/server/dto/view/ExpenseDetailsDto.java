package server.dto.view;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class ExpenseDetailsDto {
    private Long id;

    private Double money;

    private UserNameDto author;

    private String title;

    private Set<UserNameDto> debtors;

    private Date date;

    /**
     * Empty constructor
     */
    public ExpenseDetailsDto() {
    }

    /**
     * Constructor
     * @param id the id
     * @param money the money
     * @param author the author
     * @param title the title
     * @param debtors the debitors
     * @param date the date
     */
    public ExpenseDetailsDto(Long id, Double money,
                             UserNameDto author,
                             String title,
                             Set<UserNameDto> debtors, Date date) {
        this.id = id;
        this.money = money;
        this.author = author;
        this.title = title;
        this.debtors = debtors;
        this.date = date;
    }

    /**
     * id getter
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Id setter
     * @param id the new id
     * @return the new expense
     */
    public ExpenseDetailsDto setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Money getter
     * @return the money
     */
    public Double getMoney() {
        return money;
    }

    /**
     * Money setter
     * @param money the new money
     * @return the new expense
     */
    public ExpenseDetailsDto setMoney(Double money) {
        this.money = money;
        return this;
    }

    /**
     * Author getter
     * @return the author
     */
    public UserNameDto getAuthor() {
        return author;
    }

    /**
     * Author setter
     * @param author the new author
     * @return the new expense
     */
    public ExpenseDetailsDto setAuthor(UserNameDto author) {
        this.author = author;
        return this;
    }

    /**
     * Title getter
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Title setter
     * @param title the new title
     * @return the new expense
     */
    public ExpenseDetailsDto setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Debtors getter
     * @return the debtors
     */
    public Set<UserNameDto> getDebtors() {
        return debtors;
    }

    /**
     * Debtors setter
     * @param debtors the new debtor
     * @return the new expense
     */
    public ExpenseDetailsDto setDebtors(Set<UserNameDto> debtors) {
        this.debtors = debtors;
        return this;
    }

    /**
     * date getter
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * date setter
     * @param date new date
     * @return the new expense
     */
    public ExpenseDetailsDto setDate(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Equals to check for equality
     * @param o the object to compare to
     * @return true if they are equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseDetailsDto that = (ExpenseDetailsDto) o;
        return Objects.equals(id, that.id) && Objects.equals(money, that.money)
                && Objects.equals(author, that.author)
                && Objects.equals(title, that.title)
                && Objects.equals(debtors, that.debtors)
                && Objects.equals(date, that.date);
    }

    /**
     * Generating hash code
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, money, author, title, debtors, date);
    }
}
