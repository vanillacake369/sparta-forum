package com.restapi.spartaforum.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonFormat(shape = Shape.OBJECT)
public class ErrorDetail {

	private String field;

	private String value;

	private String reason;

	private LocalDateTime timestamp;

	public ErrorDetail(String field, String value, LocalDateTime timestamp) {
		this.field = field;
		this.value = value;
		this.timestamp = timestamp;
	}

	public ErrorDetail(String field, String value, String reason, LocalDateTime timestamp) {
		this.field = field;
		this.value = value;
		this.reason = reason;
		this.timestamp = timestamp;
	}

	/**
	 * 이 메서드의 역할
	 * @param bindingResult 파라미터의 역할
	 * @return 반환값은 어떤 반환값인지 설명
	 */
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
