package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Bankaccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    private String iban;
    private String holder;
    private String bic;

    /**
     *
     * @param iban the IBAN  of the bankaccount from the user.
     * @param holder the username of the user who the bankaccount is from.
     * @param bic the BIC  from the bankaccount from the user.
     */
    public Bankaccount(String iban, String holder, String bic) {
        this.iban = iban;
        this.holder = holder;
        this.bic = bic;
    }

    /**
     * default constructor (JPA requirement)
     */
    @SuppressWarnings("unused")
    public Bankaccount() {
    }

    /**
     * Getter method for the IBAN
     * @return iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * Getter method for the username which the bankaccount is connected to
     * @return the username from whom this bankaccount is
     */
    public String getHolder() {
        return holder;
    }

    /**
     * Getter method for the bic number from the bankaccount
     * @return the bic number of the bankaccount
     */
    public String getBic() {
        return bic;
    }

    /**
     * Setter for the iban in case the user has to link a new bankaccount to their profile
     * @param iban iban related to the new bankaccount the user provides
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Setter for the new accountHolder name in case the user
     * has to link a new bankaccount to their profile
     * @param holder
     */
    public void setHolder(String holder) {
        this.holder = holder;
    }

    /**
     * Setter for the bic in case the user has to link a new bankaccount to their profile
     * @param bic bic related to the new bankaccount the user provides
     */
    public void setBic(String bic) {
        this.bic = bic;
    }


    /**
     *
     * @param o Object to compare
     * @return true or false based on if the object compared is equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bankaccount that = (Bankaccount) o;

        if (!Objects.equals(iban, that.iban)) return false;
        if (!Objects.equals(holder, that.holder)) return false;
        return Objects.equals(bic, that.bic);
    }

    /**
     * @return the hashed value of the input
     */
    @Override
    public int hashCode() {
        int result = iban != null ? iban.hashCode() : 0;
        result = 31 * result + (holder != null ? holder.hashCode() : 0);
        result = 31 * result + (bic != null ? bic.hashCode() : 0);
        return result;
    }

}
