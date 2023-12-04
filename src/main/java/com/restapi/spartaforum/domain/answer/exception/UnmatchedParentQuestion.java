package com.restapi.spartaforum.domain.answer.exception;

import com.restapi.spartaforum.domain.common.DomainException;
import com.restapi.spartaforum.global.exception.ErrorCase;
import com.restapi.spartaforum.global.exception.ErrorCode;
import com.restapi.spartaforum.global.exception.ErrorDetail;
import java.time.LocalDateTime;


public class UnmatchedParentQuestion extends DomainException {

	public UnmatchedParentQuestion(String field, String value, String reason) {
		super(
			ErrorCode.of(ErrorCase.UNMATCHED_PARENT_QUESTION),
			new ErrorDetail(field, value, reason, LocalDateTime.now())
		);
	}

	public UnmatchedParentQuestion(String field, String value) {
		super(
			ErrorCode.of(ErrorCase.UNMATCHED_PARENT_QUESTION),
			new ErrorDetail(field, value, LocalDateTime.now())
		);
	}
}
