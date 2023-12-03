package com.restapi.spartaforum.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorCode {

	private String message;

	private int code;

	private HttpStatus status;
}
