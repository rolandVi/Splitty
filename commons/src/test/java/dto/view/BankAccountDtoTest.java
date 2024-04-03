package dto.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(bankAccount1, bankAccount2);
        Assertions.assertEquals(bankAccount1, sameBankAccount);
        Assertions.assertNotEquals(bankAccount1, differentBankAccount);
    }

    @Test
    void testHashCode() {
        // Arrange
        BankAccountDto sameBankAccount = new BankAccountDto(1L, "DE89370400440532013000", "John Doe", "COBADEFFXXX");

        // Act & Assert
        Assertions.assertEquals(bankAccount1.hashCode(), bankAccount2.hashCode());
        Assertions.assertEquals(bankAccount1.hashCode(), sameBankAccount.hashCode());
    }

    @Test
    void getId() {
        // Arrange & Act & Assert
        Assertions.assertEquals(1L, bankAccount1.getId());
    }

    @Test
    void setId() {
        // Arrange
        bankAccount1.setId(2L);

        // Act & Assert
        Assertions.assertEquals(2L, bankAccount1.getId());
    }

    @Test
    void getIban() {
        // Arrange & Act & Assert
        Assertions.assertEquals("DE89370400440532013000", bankAccount1.getIban());
    }

    @Test
    void setIban() {
        // Arrange
        bankAccount1.setIban("GB29NWBK60161331926819");

        // Act & Assert
        Assertions.assertEquals("GB29NWBK60161331926819", bankAccount1.getIban());
    }

    @Test
    void getHolder() {
        // Arrange & Act & Assert
        Assertions.assertEquals("John Doe", bankAccount1.getHolder());
    }

    @Test
    void setHolder() {
        // Arrange
        bankAccount1.setHolder("Jane Doe");

        // Act & Assert
        Assertions.assertEquals("Jane Doe", bankAccount1.getHolder());
    }

    @Test
    void getBic() {
        // Arrange & Act & Assert
        Assertions.assertEquals("COBADEFFXXX", bankAccount1.getBic());
    }

    @Test
    void setBic() {
        // Arrange
        bankAccount1.setBic("GENODEF1M04");

        // Act & Assert
        Assertions.assertEquals("GENODEF1M04", bankAccount1.getBic());
    }
}
