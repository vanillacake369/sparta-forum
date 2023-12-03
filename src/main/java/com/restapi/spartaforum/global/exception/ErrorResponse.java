package com.restapi.spartaforum.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonFormat(shape = Shape.OBJECT)
public class ErrorResponse {

	private ErrorCode errorCode;

	private List<ErrorDetail> errorDetails;

	public ErrorResponse(ErrorCode errorCode, List<ErrorDetail> errorDetails) {
		this.errorCode = errorCode;
		this.errorDetails = errorDetails;
	}

	public static ErrorResponse of(final int code, final String message, final HttpStatus status,
		final BindingResult bindingResult) {
		return new ErrorResponse(
			new ErrorCode(message, code, status),
			ErrorDetail.of(bindingResult)
		);
	}
}
