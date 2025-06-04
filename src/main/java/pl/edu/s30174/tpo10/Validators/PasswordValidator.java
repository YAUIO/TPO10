package pl.edu.s30174.tpo10.Validators;

import jakarta.validation.*;
import org.springframework.stereotype.Service;
import pl.edu.s30174.tpo10.Annotations.Password;
import pl.edu.s30174.tpo10.Services.Language;

import java.util.*;

@Service
public class PasswordValidator implements ConstraintValidator<Password, String> {
    private final ResourceBundle resources;

    public PasswordValidator (Language language) {
        resources = ResourceBundle.getBundle("messages", language.locale);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        Character[] chars = new Character[s.length()];
        for (int i = 0; i < chars.length; i++)
            chars[i] = s.charAt(i);
        boolean valid = true;

        try {
            validateLength(chars);
        } catch (PasswordValidationViolation e) {
            context.buildConstraintViolationWithTemplate(resources.getString("password.error.length"))
                    .addConstraintViolation();
            valid = false;
        }

        try {
            validateLowercase(chars);
        } catch (PasswordValidationViolation e) {
            context.buildConstraintViolationWithTemplate(resources.getString("password.error.lowercase"))
                    .addConstraintViolation();
            valid = false;

        }

        try {
            validateUppercase(chars);
        } catch (PasswordValidationViolation e) {
            context.buildConstraintViolationWithTemplate(resources.getString("password.error.uppercase"))
                    .addConstraintViolation();
            valid = false;
        }

        try {
            validateDigits(chars);
        } catch (PasswordValidationViolation e) {
            context.buildConstraintViolationWithTemplate(resources.getString("password.error.digits"))
                    .addConstraintViolation();
            valid = false;
        }

        try {
            validateSpecialChars(chars);
        } catch (PasswordValidationViolation e) {
            context.buildConstraintViolationWithTemplate(resources.getString("password.error.special"))
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }

    private void validateLowercase(Character[] arr) throws PasswordValidationViolation {
        Arrays.stream(arr)
                .filter(Character::isLetter)
                .filter(Character::isLowerCase)
                .findFirst()
                .orElseThrow(() -> new PasswordValidationViolation(resources.getString("password.error.lowercase")));
    }

    private void validateUppercase(Character[] arr) throws PasswordValidationViolation {
        if (Arrays.stream(arr)
                .filter(Character::isLetter)
                .filter(Character::isUpperCase)
                .count() < 2) throw new PasswordValidationViolation(resources.getString("password.error.uppercase"));
    }

    private void validateDigits(Character[] arr) throws PasswordValidationViolation {
        if (Arrays.stream(arr)
                .filter(Character::isDigit)
                .count() <= 2) throw new PasswordValidationViolation(resources.getString("password.error.digits"));
    }

    private void validateSpecialChars(Character[] arr) throws PasswordValidationViolation {
        if (Arrays.stream(arr)
                .filter(c -> !Character.isLetter(c))
                .filter(c -> !Character.isDigit(c))
                .count() < 4) throw new PasswordValidationViolation(resources.getString("password.error.special"));
    }

    private void validateLength(Character[] arr) throws PasswordValidationViolation {
        if (arr.length < 10) throw new PasswordValidationViolation(resources.getString("password.error.length"));
    }
}
