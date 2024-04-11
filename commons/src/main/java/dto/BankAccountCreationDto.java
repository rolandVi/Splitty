package dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class BankAccountCreationDto {
    @NotBlank
    private String iban;

    @NotBlank
    private String bic;

    /**
     * Constructor for the BankAccountCreationDto class.
     * @param iban The International Bank Account Number (IBAN) of the bank account.
     * @param bic The Bank Identifier Code (BIC) of the bank account.
     */
    public BankAccountCreationDto(String iban, String bic) {
        this.iban = iban;
        this.bic = bic;
    }

    /**
     * Default constructor for the BankAccountCreationDto class.
     */
    public BankAccountCreationDto() {
    }

    /**
     * Retrieves the International Bank Account Number (IBAN) of the bank account.
     * @return The IBAN of the bank account.
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets the International Bank Account Number (IBAN) of the bank account.
     * @param iban The IBAN to set.
     * @return This BankAccountCreationDto instance.
     */
    public BankAccountCreationDto setIban(String iban) {
        this.iban = iban;
        return this;
    }

    /**
     * Retrieves the Bank Identifier Code (BIC) of the bank account.
     * @return The BIC of the bank account.
     */
    public String getBic() {
        return bic;
    }

    /**
     * Sets the Bank Identifier Code (BIC) of the bank account.
     * @param bic The BIC to set.
     * @return This BankAccountCreationDto instance.
     */
    public BankAccountCreationDto setBic(String bic) {
        this.bic = bic;
        return this;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccountCreationDto that = (BankAccountCreationDto) o;
        return Objects.equals(iban, that.iban)
                && Objects.equals(bic, that.bic);
    }

    /**
     * Returns a hash code value for the object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(iban, bic);
    }
}
