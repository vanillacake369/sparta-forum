package com.restapi.spartaforum.domain.user.exception;

import com.restapi.spartaforum.domain.common.DomainException;
import com.restapi.spartaforum.global.exception.ErrorCase;
import com.restapi.spartaforum.global.exception.ErrorCode;
import com.restapi.spartaforum.global.exception.ErrorDetail;
import java.time.LocalDateTime;


public class NotSignedInException extends DomainException {

	public NotSignedInException(String field, String value, String reason) {
		super(
			ErrorCode.of(ErrorCase.NOT_SIGNED_IN),
			new ErrorDetail(field, value, reason, LocalDateTime.now())
		);
	}

	public NotSignedInException(String field, String value) {
		super(
			ErrorCode.of(ErrorCase.NOT_SIGNED_IN),
			new ErrorDetail(field, value, LocalDateTime.now())
		);
	}
}
