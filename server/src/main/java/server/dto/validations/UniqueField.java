package server.dto.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import server.dto.validations.enums.FieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueFieldValidator.class)
public @interface UniqueField {

    FieldType fieldType();

    String message() default "Entity with such property already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
