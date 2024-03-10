package server.dto.validations.enums;

import server.service.BankAccountService;
import server.service.UserService;

public enum FieldType {
    USER_EMAIL(UserService.class, "email"),
    BANK_ACCOUNT_IBAN(BankAccountService.class, "iban");

    private final Class<?> entityHandler;
    private final String fieldName;

    FieldType(Class<?> entityHandler, String name) {
        this.entityHandler = entityHandler;
        this.fieldName = name;
    }

    /**
     * The entity handler getter
     * @return the entity handler
     */
    public Class<?> getEntityHandler() {
        return entityHandler;
    }

    /**
     * Field name getter
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }
}
