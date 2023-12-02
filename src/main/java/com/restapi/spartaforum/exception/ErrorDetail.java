package com.restapi.spartaforum.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public record ErrorDetail(
	String field,
	String value,
	String reason,
	LocalDateTime timestamp
) {

	public static List<ErrorDetail> of(BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		return fieldErrors.stream()
			.map(fieldError ->
				new ErrorDetail(
					fieldError.getField(),
					Objects.requireNonNull(fieldError.getRejectedValue()).toString(),
					fieldError.getDefaultMessage(),
					LocalDateTime.now()
				)
			)
			.toList();
	}
}
