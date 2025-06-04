package pl.edu.s30174.tpo10.Validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;
import pl.edu.s30174.tpo10.Annotations.Password;

public class PasswordValidationViolation extends Throwable implements ConstraintViolation<Password> {
    private final String message;

    public PasswordValidationViolation(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getMessageTemplate() {
        return "";
    }

    @Override
    public Password getRootBean() {
        return null;
    }

    @Override
    public Class<Password> getRootBeanClass() {
        return null;
    }

    @Override
    public Object getLeafBean() {
        return null;
    }

    @Override
    public Object[] getExecutableParameters() {
        return new Object[0];
    }

    @Override
    public Object getExecutableReturnValue() {
        return null;
    }

    @Override
    public Path getPropertyPath() {
        return null;
    }

    @Override
    public Object getInvalidValue() {
        return null;
    }

    @Override
    public ConstraintDescriptor<?> getConstraintDescriptor() {
        return null;
    }

    @Override
    public <U> U unwrap(Class<U> aClass) {
        return null;
    }
}
