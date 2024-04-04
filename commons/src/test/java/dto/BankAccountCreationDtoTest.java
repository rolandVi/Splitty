package dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankAccountCreationDtoTest {

    private BankAccountCreationDto bankAccountCreationDto;

    @BeforeEach
    void setUp() {
        bankAccountCreationDto = new BankAccountCreationDto("IBAN12345", "John Doe", "BIC67890");
    }

    @Test
    void getIban() {
        Assertions.assertEquals("IBAN12345", bankAccountCreationDto.getIban());
    }

    @Test
    void setIban() {
        bankAccountCreationDto.setIban("NewIBAN");
        Assertions.assertEquals("NewIBAN", bankAccountCreationDto.getIban());
    }

    @Test
    void getBic() {
        Assertions.assertEquals("BIC67890", bankAccountCreationDto.getBic());
    }

    @Test
    void setBic() {
        bankAccountCreationDto.setBic("NewBIC");
        Assertions.assertEquals("NewBIC", bankAccountCreationDto.getBic());
    }

    @Test
    void testEquals() {
        BankAccountCreationDto sameDto = new BankAccountCreationDto("IBAN12345", "John Doe", "BIC67890");
        BankAccountCreationDto differentDto = new BankAccountCreationDto("DifferentIBAN", "Jane Smith", "DifferentBIC");

        // Test equality with itself
        Assertions.assertEquals(bankAccountCreationDto, bankAccountCreationDto);

        // Test equality with an object having the same values
        Assertions.assertEquals(bankAccountCreationDto, sameDto);

        // Test inequality with an object having different values
        Assertions.assertNotEquals(bankAccountCreationDto, differentDto);

        // Test inequality with null
        Assertions.assertNotEquals(bankAccountCreationDto, null);

        // Test inequality with a different class
        Assertions.assertNotEquals(bankAccountCreationDto, "Not a BankAccountCreationDto");
    }

    @Test
    void testHashCode() {
        BankAccountCreationDto sameDto = new BankAccountCreationDto("IBAN12345", "John Doe", "BIC67890");

        // Hash code should be the same for objects with the same content
        Assertions.assertEquals(bankAccountCreationDto.hashCode(), sameDto.hashCode());
    }
}
