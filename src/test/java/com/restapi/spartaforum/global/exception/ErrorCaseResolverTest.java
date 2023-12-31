package com.restapi.spartaforum.global.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.restapi.spartaforum.config.MessageConfiguration;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.LocaleResolver;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MessageConfiguration.class, ErrorCaseResolver.class})
class ErrorCaseResolverTest {

	@Autowired
	MessageSource messageSource;
	@Autowired
	ErrorCaseResolver errorCaseResolver;
	@Autowired
	LocaleResolver localeResolver;

	public static Stream<Arguments> getInvalidInputErrorCase() {
		return Stream.of(
			Arguments.of(
				ErrorCase.ARGUMENT_NOT_VALID
			)
		);
	}

	@BeforeEach
	void setUp() {
		errorCaseResolver.init();
	}

	@Test
	@DisplayName("동일한 메세지 국제화 인스턴스가 주입되는지 확인합니다.")
	public void 동일한_국제화인스턴스() {
		// WHEN
		MessageSource source = ErrorCaseResolver.messageSource;

		// THEN
		assertEquals(source, messageSource);
	}

	@Test
	@DisplayName("Default Locale이 한국으로 설정되었는지 확인합니다.")
	public void 기본_Locale설정() {
		// THEN
		assertEquals(Locale.US, Locale.getDefault());
	}

	@ParameterizedTest
	@DisplayName("형식에 맞지 않는 입력에 대한 예외 케이스에 대해 메세지 국제화를 사용합니다.")
	@MethodSource("getInvalidInputErrorCase")
	public void 입력예외_메세지국제화(ErrorCase errorCase) {
		// WHEN
		int code = ErrorCaseResolver.getCode(errorCase);
		String msg = ErrorCaseResolver.getMsg(errorCase);
		HttpStatus status = ErrorCaseResolver.getStatus(errorCase);

		// THEN
		assertEquals(-9002, code);
		assertEquals("형식에 맞지 않는 데이터 오류가 발생하였습니다.", msg);
		assertEquals(HttpStatus.BAD_REQUEST, status);
		/*
		 * code: "-9999"
		 * msg: "형식에 맞지 않는 데이터 오류가 발생하였습니다."
		 * status: 404
		 */
	}
}