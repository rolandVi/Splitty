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

    public Long getId() {
        return id;
    }

    public ExpenseDetailsDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getMoney() {
        return money;
    }

    public ExpenseDetailsDto setMoney(Double money) {
        this.money = money;
        return this;
    }

    public UserNameDto getAuthor() {
        return author;
    }

    public ExpenseDetailsDto setAuthor(UserNameDto author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ExpenseDetailsDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public Set<UserNameDto> getDebtors() {
        return debtors;
    }

    public ExpenseDetailsDto setDebtors(Set<UserNameDto> debtors) {
        this.debtors = debtors;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ExpenseDetailsDto setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseDetailsDto that = (ExpenseDetailsDto) o;
        return Objects.equals(id, that.id) && Objects.equals(money, that.money) && Objects.equals(author, that.author) && Objects.equals(title, that.title) && Objects.equals(debtors, that.debtors) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, money, author, title, debtors, date);
    }
}
