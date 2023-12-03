package com.restapi.spartaforum.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonFormat(shape = Shape.OBJECT)
public class ErrorCode {

	private String message;

	private int code;

	private HttpStatus status;

	public static ErrorCode of(ErrorCase errorCase) {
		return new ErrorCode(
			ErrorCaseResolver.getMsg(errorCase),
			ErrorCaseResolver.getCode(errorCase),
			ErrorCaseResolver.getStatus(errorCase)
		);
	}
}
