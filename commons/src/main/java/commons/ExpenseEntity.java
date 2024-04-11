package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "expenses")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double money;

    @ManyToOne(optional = false)
    private ParticipantEntity author;

    @ManyToMany
    private Set<ParticipantEntity> debtors;

    @ManyToOne(cascade = CascadeType.DETACH)
    private EventEntity event;

    private String title;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    private TagEntity tag;


    /**
     * Default constructor for JBA
     */
    public ExpenseEntity() {
    }

    /**
     * Regular constructor for Expense requiring all fields
     *
     * @param id      unique ID to identify expense
     * @param money   amount of money in the expense
     * @param author  author of the expense
     * @param debtors list of debtors
     * @param title   title summarizing the expense
     * @param date    date of the expense
     * @param event   the parent event entity
     * @param tag     the tag for an expense
     */
    public ExpenseEntity(Long id, Double money, ParticipantEntity author,
                         Set<ParticipantEntity> debtors, String title,
                         Date date, EventEntity event, TagEntity tag) {
        this.id = id;
        this.money = money;
        this.author = author;
        this.debtors = debtors;
        this.title = title;
        this.date = date;
        this.event = event;
        this.tag = tag;
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
    public ParticipantEntity getAuthor() {
        return author;
    }

    /**
     * Getter for the list of debtors
     * @return list of debtors as ArrayList of UserEntity
     */
    public Set<ParticipantEntity> getDebtors() {
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
     * Getter for the parent event of the expense
     * @return - the event entity
     */
    public EventEntity getEvent(){
        return event;
    }

    /**
     * Getter for the tag of an event
     * @return the tag of an event
     */
    public TagEntity getTag() {
        return tag;
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
     * Setter for the author
     * @param author the author
     */
    public void setAuthor(ParticipantEntity author) {
        this.author = author;
    }

    /**
     * Setter for the debtors field
     * @param debtors the set of debtors
     */
    public void setDebtors(Set<ParticipantEntity> debtors){
        this.debtors = debtors;
    }

    /**
     * Setter for the parent event
     * @param event the parent event
     */
    public void setEvent(EventEntity event) {
        this.event = event;
    }

    /**
     * Setter for the tag of an event
     * @param tag the new tag for the expense
     */
    public void setTag(TagEntity tag) {
        this.tag = tag;
    }

    /**
     * Adds new debtor to the list of debtors
     * @param debtor as UserEntity
     */
    public void addDebtor(ParticipantEntity debtor){
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
                && Objects.equals(date, expenseEntity.date)
                && Objects.equals(tag, expenseEntity.tag)
                &&Objects.equals(event, expenseEntity.event);
    }

    /**
     * Hashcode method for Expense
     * @return the hashcode as int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, money, author, debtors, title, date, tag);
    }


}