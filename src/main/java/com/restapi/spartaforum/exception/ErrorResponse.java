package com.restapi.spartaforum.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonFormat(shape = Shape.OBJECT)
public class ErrorResponse {

	private String code;

	private String message;

	private List<ErrorDetail> errorDetails;

	public static ErrorResponse of(final String code, final String message, final BindingResult bindingResult) {
		return new ErrorResponse(code, message, ErrorDetail.of(bindingResult));
	}
}
