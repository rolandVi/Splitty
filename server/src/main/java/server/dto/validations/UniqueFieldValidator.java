package server.dto.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.ApplicationContext;
import server.dto.validations.enums.FieldType;

import java.lang.reflect.InvocationTargetException;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {
    private final ApplicationContext appContext;

    private FieldType fieldType;
    private String message;

    /**
     * Overriden constructor
     * @param appContext the application context
     */
    public UniqueFieldValidator(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    /**
     * Initializes the validator
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(UniqueField constraintAnnotation) {
        this.fieldType = constraintAnnotation.fieldType();
        this.message=constraintAnnotation.message();
    }

    /**
     *
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return whether the annotated field is valid
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        var result=false;

        try {
            var instance=appContext.getBean(this.fieldType.getEntityHandler());

            var existsMethod = this.fieldType.getEntityHandler()
                    .getDeclaredMethod(
                            String.format("%sExists", this.fieldType.getFieldName()), String.class);
            existsMethod.setAccessible(true);

            result= (boolean) existsMethod.invoke(instance, value);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return !result;
    }
}
