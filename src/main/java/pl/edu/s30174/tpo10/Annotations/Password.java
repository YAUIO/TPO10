package pl.edu.s30174.tpo10.Annotations;

import jakarta.validation.Constraint;
import pl.edu.s30174.tpo10.Validators.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Password should contain at least one lowercase, two uppercase characters, three digits, four special characters and be at least 10 characters long.";
    Class[] groups() default {};
    Class[] payload() default {};
}
