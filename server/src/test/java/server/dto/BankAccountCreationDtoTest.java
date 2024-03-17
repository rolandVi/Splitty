package server.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountCreationDtoTest {

    private BankAccountCreationDto bankAccountCreationDto;

    @BeforeEach
    void setUp() {
        bankAccountCreationDto = new BankAccountCreationDto("IBAN12345", "John Doe", "BIC67890");
    }

    @Test
    void getIban() {
        assertEquals("IBAN12345", bankAccountCreationDto.getIban());
    }

    @Test
    void setIban() {
        bankAccountCreationDto.setIban("NewIBAN");
        assertEquals("NewIBAN", bankAccountCreationDto.getIban());
    }

    @Test
    void getHolder() {
        assertEquals("John Doe", bankAccountCreationDto.getHolder());
    }

    @Test
    void setHolder() {
        bankAccountCreationDto.setHolder("Jane Smith");
        assertEquals("Jane Smith", bankAccountCreationDto.getHolder());
    }

    @Test
    void getBic() {
        assertEquals("BIC67890", bankAccountCreationDto.getBic());
    }

    @Test
    void setBic() {
        bankAccountCreationDto.setBic("NewBIC");
        assertEquals("NewBIC", bankAccountCreationDto.getBic());
    }

    @Test
    void testEquals() {
        BankAccountCreationDto sameDto = new BankAccountCreationDto("IBAN12345", "John Doe", "BIC67890");
        BankAccountCreationDto differentDto = new BankAccountCreationDto("DifferentIBAN", "Jane Smith", "DifferentBIC");

        // Test equality with itself
        assertEquals(bankAccountCreationDto, bankAccountCreationDto);

        // Test equality with an object having the same values
        assertEquals(bankAccountCreationDto, sameDto);

        // Test inequality with an object having different values
        assertNotEquals(bankAccountCreationDto, differentDto);

        // Test inequality with null
        assertNotEquals(bankAccountCreationDto, null);

        // Test inequality with a different class
        assertNotEquals(bankAccountCreationDto, "Not a BankAccountCreationDto");
    }

    @Test
    void testHashCode() {
        BankAccountCreationDto sameDto = new BankAccountCreationDto("IBAN12345", "John Doe", "BIC67890");

        // Hash code should be the same for objects with the same content
        assertEquals(bankAccountCreationDto.hashCode(), sameDto.hashCode());
    }
}
