package commons;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "expenses")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double money;

    @ManyToOne(optional = false)
    private UserEntity author;

    @ManyToMany
    private Set<UserEntity> debtors;

    private String title;

    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Default constructor for JBA
     */
    public ExpenseEntity() {
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
    public ExpenseEntity(Long id, Double money, UserEntity author,
                         Set<UserEntity> debtors, String title, Date date) {
        this.id = id;
        this.money = money;
        this.author = author;
        this.debtors = debtors;
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
    public Set<UserEntity> getDebtors() {
        return debtors;
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
        debtors.add(debtor);
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
        ExpenseEntity expenseEntity = (ExpenseEntity) o;
        return Objects.equals(id, expenseEntity.id) && Objects.equals(money, expenseEntity.money)
                && Objects.equals(author, expenseEntity.author)
                && Objects.equals(debtors, expenseEntity.debtors)
                && Objects.equals(title, expenseEntity.title)
                && Objects.equals(date, expenseEntity.date);
    }

    /**
     * Hashcode method for Expense
     * @return the hashcode as int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, money, author, debtors, title, date);
    }
}