package com.restapi.spartaforum.domain.user.exception;

import com.restapi.spartaforum.domain.common.DomainException;
import com.restapi.spartaforum.global.exception.ErrorCode;
import com.restapi.spartaforum.global.exception.ErrorDetail;


public class DuplicatedUserException extends DomainException {

	public DuplicatedUserException(ErrorCode errorCode, ErrorDetail errorDetail) {
		super(errorCode, errorDetail);
	}
}
