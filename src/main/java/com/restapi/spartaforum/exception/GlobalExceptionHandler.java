package com.restapi.spartaforum.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> argsValidationError(MethodArgumentNotValidException ex) {
		log.error("handleMethodArgumentNotValidException", ex);
		String code = getMessage("dataError.code");
		String message = getMessage("dataError.message");
		HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(Integer.parseInt(getMessage("dataError.status")));
		final ErrorResponse response = ErrorResponse.of(code, message, ex.getBindingResult());
		return new ResponseEntity<>(response, httpStatusCode);
	}


	// code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
	private String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}

	// code정보에 해당하는 메시지를 조회합니다.
	private String getMessage(String code) {
		return getMessage(code, null);
	}

}
