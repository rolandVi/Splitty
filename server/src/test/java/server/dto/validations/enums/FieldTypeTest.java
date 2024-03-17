package server.dto.validations.enums;

import org.junit.jupiter.api.Test;
import server.service.BankAccountService;
import server.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldTypeTest {

    @Test
    void getEntityHandler() {
        // Arrange
        Class<?> expectedUserService = UserService.class;
        Class<?> expectedBankAccountService = BankAccountService.class;

        // Act
        Class<?> actualUserService = FieldType.USER_EMAIL.getEntityHandler();
        Class<?> actualBankAccountService = FieldType.BANK_ACCOUNT_IBAN.getEntityHandler();

        // Assert
        assertEquals(expectedUserService, actualUserService);
        assertEquals(expectedBankAccountService, actualBankAccountService);
    }

    @Test
    void getFieldName() {
        // Arrange
        String expectedEmailFieldName = "email";
        String expectedIbanFieldName = "iban";

        // Act
        String actualEmailFieldName = FieldType.USER_EMAIL.getFieldName();
        String actualIbanFieldName = FieldType.BANK_ACCOUNT_IBAN.getFieldName();

        // Assert
        assertEquals(expectedEmailFieldName, actualEmailFieldName);
        assertEquals(expectedIbanFieldName, actualIbanFieldName);
    }

    @Test
    void values() {
        // Act
        FieldType[] values = FieldType.values();

        // Assert
        assertEquals(2, values.length);
        assertEquals(FieldType.USER_EMAIL, values[0]);
        assertEquals(FieldType.BANK_ACCOUNT_IBAN, values[1]);
    }

    @Test
    void valueOf() {
        // Act
        FieldType emailFieldType = FieldType.valueOf("USER_EMAIL");
        FieldType ibanFieldType = FieldType.valueOf("BANK_ACCOUNT_IBAN");

        // Assert
        assertEquals(FieldType.USER_EMAIL, emailFieldType);
        assertEquals(FieldType.BANK_ACCOUNT_IBAN, ibanFieldType);
    }
}
