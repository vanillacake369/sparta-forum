package com.restapi.spartaforum.domain.user.exception;

import com.restapi.spartaforum.domain.common.DomainException;
import com.restapi.spartaforum.global.exception.ErrorCase;
import com.restapi.spartaforum.global.exception.ErrorCode;
import com.restapi.spartaforum.global.exception.ErrorDetail;
import java.time.LocalDateTime;


public class DuplicatedUserException extends DomainException {

	public DuplicatedUserException(String field, String value, String reason) {
		super(
			ErrorCode.of(ErrorCase.EXISTING_USER),
			new ErrorDetail(field, value, reason, LocalDateTime.now())
		);
	}

	public DuplicatedUserException(String field, String value) {
		super(
			ErrorCode.of(ErrorCase.EXISTING_USER),
			new ErrorDetail(field, value, LocalDateTime.now())
		);
	}
}
