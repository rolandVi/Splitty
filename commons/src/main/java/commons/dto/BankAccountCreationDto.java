package commons.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class BankAccountCreationDto {
    @NotBlank
    private String iban;
    @NotNull
    @Size(max = 20)
    private String holder;
    @NotBlank
    private String bic;

    public BankAccountCreationDto(String iban, String holder, String bic) {
        this.iban = iban;
        this.holder = holder;
        this.bic = bic;
    }

    public BankAccountCreationDto() {
    }

    public String getIban() {
        return iban;
    }

    public BankAccountCreationDto setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public String getHolder() {
        return holder;
    }

    public BankAccountCreationDto setHolder(String holder) {
        this.holder = holder;
        return this;
    }

    public String getBic() {
        return bic;
    }

    public BankAccountCreationDto setBic(String bic) {
        this.bic = bic;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccountCreationDto that = (BankAccountCreationDto) o;
        return Objects.equals(iban, that.iban) && Objects.equals(holder, that.holder) && Objects.equals(bic, that.bic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, holder, bic);
    }
}
