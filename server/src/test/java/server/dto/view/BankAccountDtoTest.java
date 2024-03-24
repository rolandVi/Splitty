package server.dto.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BankAccountDtoTest {

    private BankAccountDto bankAccount1;
    private BankAccountDto bankAccount2;

    @BeforeEach
    void setUp() {
        bankAccount1 = new BankAccountDto(1L, "DE89370400440532013000", "John Doe", "COBADEFFXXX");
        bankAccount2 = new BankAccountDto(1L, "DE89370400440532013000", "John Doe", "COBADEFFXXX");
    }

    @Test
    void testEquals() {
        // Arrange
        BankAccountDto sameBankAccount = new BankAccountDto(1L, "DE89370400440532013000", "John Doe", "COBADEFFXXX");
        BankAccountDto differentBankAccount = new BankAccountDto(2L, "DE89370400440532013000", "Jane Doe", "COBADEFFYYY");

        // Act & Assert
        assertEquals(bankAccount1, bankAccount2);
        assertEquals(bankAccount1, sameBankAccount);
        assertNotEquals(bankAccount1, differentBankAccount);
    }

    @Test
    void testHashCode() {
        // Arrange
        BankAccountDto sameBankAccount = new BankAccountDto(1L, "DE89370400440532013000", "John Doe", "COBADEFFXXX");

        // Act & Assert
        assertEquals(bankAccount1.hashCode(), bankAccount2.hashCode());
        assertEquals(bankAccount1.hashCode(), sameBankAccount.hashCode());
    }

    @Test
    void getId() {
        // Arrange & Act & Assert
        assertEquals(1L, bankAccount1.getId());
    }

    @Test
    void setId() {
        // Arrange
        bankAccount1.setId(2L);

        // Act & Assert
        assertEquals(2L, bankAccount1.getId());
    }

    @Test
    void getIban() {
        // Arrange & Act & Assert
        assertEquals("DE89370400440532013000", bankAccount1.getIban());
    }

    @Test
    void setIban() {
        // Arrange
        bankAccount1.setIban("GB29NWBK60161331926819");

        // Act & Assert
        assertEquals("GB29NWBK60161331926819", bankAccount1.getIban());
    }

    @Test
    void getHolder() {
        // Arrange & Act & Assert
        assertEquals("John Doe", bankAccount1.getHolder());
    }

    @Test
    void setHolder() {
        // Arrange
        bankAccount1.setHolder("Jane Doe");

        // Act & Assert
        assertEquals("Jane Doe", bankAccount1.getHolder());
    }

    @Test
    void getBic() {
        // Arrange & Act & Assert
        assertEquals("COBADEFFXXX", bankAccount1.getBic());
    }

    @Test
    void setBic() {
        // Arrange
        bankAccount1.setBic("GENODEF1M04");

        // Act & Assert
        assertEquals("GENODEF1M04", bankAccount1.getBic());
    }
}
