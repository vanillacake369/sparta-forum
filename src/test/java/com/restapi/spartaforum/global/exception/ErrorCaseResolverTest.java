package com.restapi.spartaforum.global.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

class ErrorCaseResolverTest {

	public static Stream<Arguments> getInvalidInputErrorCase() {
		return Stream.of(
			Arguments.of(
				ErrorCase.ARGUMENT_NOT_VALID
			)
		);
	}

	@ParameterizedTest
	@DisplayName("형식에 맞지 않는 입력에 대한 예외 케이스에 대해 메세지 국제화를 사용합니다.")
	@MethodSource("getInvalidInputErrorCase")
	public void 입력예외_메세지국제화(ErrorCase errorCase) {
		// WHEN
		int code = ErrorCaseResolver.getCode(errorCase);
		String msg = ErrorCaseResolver.getMsg(errorCase);
		HttpStatus status = ErrorCaseResolver.getStatus(errorCase);
//		code: "-9999"
//		msg: "형식에 맞지 않는 데이터 오류가 발생하였습니다."
//		status: 404

		// THEN
		assertEquals(-9999, code);
		assertEquals("형식에 맞지 않는 데이터 오류가 발생하였습니다.", msg);
		assertEquals(HttpStatus.BAD_REQUEST, status);
	}
}