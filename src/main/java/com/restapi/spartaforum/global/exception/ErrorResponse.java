package com.restapi.spartaforum.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonFormat(shape = Shape.OBJECT)
public class ErrorResponse {

	private ErrorCode errorCode;

	private List<ErrorDetail> errorDetails;

	private ErrorResponse(ErrorCode errorCode, List<ErrorDetail> errorDetails) {
		this.errorCode = errorCode;
		this.errorDetails = errorDetails;
	}

	private ErrorResponse(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
		return new ErrorResponse(
			errorCode,
			ErrorDetail.of(bindingResult)
		);
	}

	public static ErrorResponse of(final ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	public static ErrorResponse of(ErrorCode errorCode, ErrorDetail... errorDetail) {
		List<ErrorDetail> details = Arrays.stream(errorDetail).toList();
		return new ErrorResponse(
			errorCode,
			details
		);
	}
}
