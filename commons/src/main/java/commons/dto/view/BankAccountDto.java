package commons.dto.view;

import java.util.Objects;

public class BankAccountDto {

    private long id;

    private String iban;
    private String holder;
    private String bic;

    /**
     * Empty constructor
     */
    public BankAccountDto() {
    }

    /**
     *  Constructor
     * @param id the id
     * @param iban iban
     * @param holder email of userHolder
     * @param bic bic
     */
    public BankAccountDto(long id, String iban, String holder, String bic) {
        this.id = id;
        this.iban = iban;
        this.holder = holder;
        this.bic = bic;
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

        BankAccountDto that = (BankAccountDto) o;

        if (id != that.id) return false;
        if (!iban.equals(that.iban)) return false;
        if (!holder.equals(that.holder)) return false;
        return bic.equals(that.bic);
    }

    /**
     * Generating hash code
     * @return hashed index
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + iban.hashCode();
        result = 31 * result + holder.hashCode();
        result = 31 * result + bic.hashCode();
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}
