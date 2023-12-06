package com.restapi.spartaforum.domain.common;

import com.restapi.spartaforum.global.exception.ErrorCode;
import com.restapi.spartaforum.global.exception.ErrorDetail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DomainException extends RuntimeException {

	protected ErrorCode errorCode;
	protected ErrorDetail errorDetail;
}
