package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double money;

    @Column(nullable = false)
    private UserEntity author;

    @Column(nullable = false)
    private ArrayList<UserEntity> debitors;

    private String title;

    @Column(nullable = false)
    private Date date;

    /**
     *
     */
    public Expense() {
    }

    /**
     *
     * @param id
     * @param money
     * @param author
     * @param debitors
     * @param title
     * @param date
     */
    public Expense(Long id, Double money, UserEntity author, ArrayList<UserEntity> debitors, String title, Date date) {
        this.id = id;
        this.money = money;
        this.author = author;
        this.debitors = debitors;
        this.title = title;
        this.date = date;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public Double getMoney() {
        return money;
    }

    /**
     *
     * @return
     */
    public UserEntity getAuthor() {
        return author;
    }

    /**
     *
     * @return
     */
    public ArrayList<UserEntity> getDebitors() {
        return debitors;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param money
     */
    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @param debitor
     */
    public void addDebitor(UserEntity debitor){
        debitors.add(debitor);
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(id, expense.id) && Objects.equals(money, expense.money) && Objects.equals(author, expense.author) && Objects.equals(debitors, expense.debitors) && Objects.equals(title, expense.title) && Objects.equals(date, expense.date);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, money, author, debitors, title, date);
    }
}
