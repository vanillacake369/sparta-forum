package com.restapi.spartaforum.global.exception;

import com.restapi.spartaforum.domain.common.DomainException;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	/**
	 * InValid Args Input Exception
	 * @param ex 입력예외형식에 어긋난 경우의 예외
	 * @return ErrorResponse => {에러코드,메세지,HttpStatus 를 담은 ErrorCode} + {에러발생사유를 담은 ErrorDetail}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.error("handleMethodArgumentNotValidException", ex);
		ErrorCode errorCode = ErrorCode.of(ErrorCase.ARGUMENT_NOT_VALID);
		final ErrorResponse response = ErrorResponse.of(errorCode, ex.getBindingResult());
		return new ResponseEntity<>(response, errorCode.getStatus());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
		log.error("handleDomainException", ex);
		ErrorCode errorCode = ErrorCode.of(ErrorCase.ACCESS_DENIED);
		final ErrorResponse response = ErrorResponse.of(errorCode);
		return new ResponseEntity<>(response, errorCode.getStatus());
	}

	/**
	 * 비즈니스 관련된 예외처리 핸들러
	 * @param ex 비즈니스 도메인 예외
	 * @return ErrorResponse => {에러코드,메세지,HttpStatus 를 담은 ErrorCode} + {에러발생사유를 담은 ErrorDetail}
	 */
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
		log.error("handleDomainException", ex);
		ErrorCode errorCode = ex.getErrorCode();
		ErrorDetail errorDetail = ex.getErrorDetail();
		final ErrorResponse response = ErrorResponse.of(errorCode, errorDetail);
		return new ResponseEntity<>(response, errorCode.getStatus());
	}
}
