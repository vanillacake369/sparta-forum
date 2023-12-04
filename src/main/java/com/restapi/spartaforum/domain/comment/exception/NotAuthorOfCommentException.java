package com.restapi.spartaforum.domain.comment.exception;

import com.restapi.spartaforum.domain.common.DomainException;
import com.restapi.spartaforum.global.exception.ErrorCase;
import com.restapi.spartaforum.global.exception.ErrorCode;
import com.restapi.spartaforum.global.exception.ErrorDetail;
import java.time.LocalDateTime;


public class NotAuthorOfCommentException extends DomainException {

	public NotAuthorOfCommentException(String field, String value, String reason) {
		super(
			ErrorCode.of(ErrorCase.NOT_AUTHOR_COMMENT),
			new ErrorDetail(field, value, reason, LocalDateTime.now())
		);
	}

	public NotAuthorOfCommentException(String field, String value) {
		super(
			ErrorCode.of(ErrorCase.NOT_AUTHOR_COMMENT),
			new ErrorDetail(field, value, LocalDateTime.now())
		);
	}
}
