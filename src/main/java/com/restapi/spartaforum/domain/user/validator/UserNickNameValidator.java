package com.restapi.spartaforum.domain.user.validator;

import static com.restapi.spartaforum.domain.user.validator.UserConstraint.NICKNAME_CONSTRAINT;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UserNickNameValidator implements ConstraintValidator<ValidNickName, String> {

	@Override
	public void initialize(ValidNickName constraintAnnotation) {
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
			throwExceptionWithMessage(context, UserValidationMessage.NULL_ERROR.errorMessage);
			return false;
		}
		return true;
	}

	private boolean isValidLength(String value, ConstraintValidatorContext context) {
		if (value.length() >= NICKNAME_CONSTRAINT.lengthMax && value.length() <= NICKNAME_CONSTRAINT.lengthMin) {
			throwExceptionWithMessage(context, UserValidationMessage.NICK_NAME_LENGTH_ERROR.errorMessage);
			return false;
		}
		return true;
	}

	private boolean isValidForm(String value, ConstraintValidatorContext context) {
		if (!Pattern.matches(NICKNAME_CONSTRAINT.regex, value)) {
			throwExceptionWithMessage(context, UserValidationMessage.NICK_NAME_FORMAT_ERROR.errorMessage);
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
