package com.restapi.spartaforum.domain.validator;

import static com.restapi.spartaforum.domain.validator.UserSignUpConstraint.USERNAME_CONSTRAINT;
import static com.restapi.spartaforum.domain.validator.UserStatusMessage.NULL_ERROR;
import static com.restapi.spartaforum.domain.validator.UserStatusMessage.USER_NAME_FORMAT_ERROR;
import static com.restapi.spartaforum.domain.validator.UserStatusMessage.USER_NAME_LENGTH_ERROR;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UserNameValidator implements ConstraintValidator<ValidUserName, String> {
    @Override
    public void initialize(ValidUserName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isNotEmpty(value, context)
                && isValidLength(value, context)
                && isValidForm(value, context);
    }

    private boolean isNotEmpty(String value, ConstraintValidatorContext context) {
        if (value.isEmpty()) {
            throwExceptionWithMessage(context, NULL_ERROR.errorMessage);
            return false;
        }
        return true;
    }

    private boolean isValidLength(String value, ConstraintValidatorContext context) {
        if (value.length() >= USERNAME_CONSTRAINT.lengthMax && value.length() <= USERNAME_CONSTRAINT.lengthMin) {
            throwExceptionWithMessage(context, USER_NAME_LENGTH_ERROR.errorMessage);
            return false;
        }
        return true;
    }

    private boolean isValidForm(String value, ConstraintValidatorContext context) {
        if (!Pattern.matches(USERNAME_CONSTRAINT.regex, value)) {
            throwExceptionWithMessage(context, USER_NAME_FORMAT_ERROR.errorMessage);
            return false;
        }
        return true;
    }

    private void throwExceptionWithMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        customMessageForValidation(context, message);
    }

    private void customMessageForValidation(ConstraintValidatorContext constraintContext, String message) {
        // build new violation message and add it
        constraintContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
