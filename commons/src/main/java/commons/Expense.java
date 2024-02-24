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
     * Default constructor for JBA
     */
    public Expense() {
    }

    /**
     * Regular constructor for Expense requiring all fields
     * @param id unique ID to identify expense
     * @param money amount of money in the expense
     * @param author author of the expense
     * @param debtors list of debtors
     * @param title title summarizing the expense
     * @param date date of the expense
     */
    public Expense(Long id, Double money, UserEntity author, ArrayList<UserEntity> debtors, String title, Date date) {
        this.id = id;
        this.money = money;
        this.author = author;
        this.debitors = debtors;
        this.title = title;
        this.date = date;
    }

    /**
     * Getter for the ID of the expense
     * @return ID of the expense as Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for the amount of money for the expense
     * @return amount of money as Double
     */
    public Double getMoney() {
        return money;
    }

    /**
     * Getter for the author of the expense
     * @return author as UserEntity
     */
    public UserEntity getAuthor() {
        return author;
    }

    /**
     * Getter for the list of debtors
     * @return list of debtors as ArrayList of UserEntity
     */
    public ArrayList<UserEntity> getDebtors() {
        return debitors;
    }

    /**
     * Getter for the title of the expense
     * @return title as String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the date of the expense
     * @return date as Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for the amount of money for the expense
     * @param money the amount as Double
     */
    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * Setter for the title of the expense
     * @param title as String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for the date of the expense
     * @param date as Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Adds new debtor to the list of debtors
     * @param debtor as UserEntity
     */
    public void addDebtor(UserEntity debtor){
        debitors.add(debtor);
    }

    /**
     * Equals method comparing this object to object o
     * @param o the object for comparing
     * @return true if the object o is equal to this object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(id, expense.id) && Objects.equals(money, expense.money) && Objects.equals(author, expense.author) && Objects.equals(debitors, expense.debitors) && Objects.equals(title, expense.title) && Objects.equals(date, expense.date);
    }

    /**
     * Hashcode method for Expense
     * @return the hashcode as int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, money, author, debitors, title, date);
    }
}
