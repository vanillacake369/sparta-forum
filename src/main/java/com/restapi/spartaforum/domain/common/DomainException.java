package com.restapi.spartaforum.domain.common;

import com.restapi.spartaforum.global.exception.ErrorCode;
import com.restapi.spartaforum.global.exception.ErrorDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DomainException {

	protected final ErrorCode errorCode;
	protected final ErrorDetail errorDetail;
}
