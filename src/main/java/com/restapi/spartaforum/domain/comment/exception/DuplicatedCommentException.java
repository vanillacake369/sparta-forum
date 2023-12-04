package com.restapi.spartaforum.domain.comment.exception;

import com.restapi.spartaforum.domain.common.DomainException;
import com.restapi.spartaforum.global.exception.ErrorCase;
import com.restapi.spartaforum.global.exception.ErrorCode;
import com.restapi.spartaforum.global.exception.ErrorDetail;
import java.time.LocalDateTime;


public class DuplicatedCommentException extends DomainException {

	public DuplicatedCommentException(String field, String value, String reason) {
		super(
			ErrorCode.of(ErrorCase.DUPLICATED_COMMENT),
			new ErrorDetail(field, value, reason, LocalDateTime.now())
		);
	}

	public DuplicatedCommentException(String field, String value) {
		super(
			ErrorCode.of(ErrorCase.DUPLICATED_COMMENT),
			new ErrorDetail(field, value, LocalDateTime.now())
		);
	}
}
