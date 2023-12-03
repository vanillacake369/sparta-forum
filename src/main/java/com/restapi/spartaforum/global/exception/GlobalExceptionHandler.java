package com.restapi.spartaforum.global.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> argsValidationError(MethodArgumentNotValidException ex) {
		log.error("handleMethodArgumentNotValidException", ex);
		int code = ErrorCaseResolver.getCode(ErrorCase.ARGUMENT_NOT_VALID);
		String message = ErrorCaseResolver.getMsg(ErrorCase.ARGUMENT_NOT_VALID);
		HttpStatus httpStatusCode = ErrorCaseResolver.getStatus(ErrorCase.ARGUMENT_NOT_VALID);
		final ErrorResponse response = ErrorResponse.of(code, message, httpStatusCode, ex.getBindingResult());
		return new ResponseEntity<>(response, httpStatusCode);
	}

}
