package com.restapi.spartaforum.global.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.restapi.spartaforum.config.MessageConfiguration;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MessageConfiguration.class)
@TestPropertySource(locations = "classpath:test_exception.yml")
class ErrorCaseResolverTest {

	//	@InjectMocks
//	ErrorCaseResolver errorCaseResolver;
//
//	@MockBean
//	MessageSource messageSource;
	@Autowired
	MessageSource messageSource;
	@Value("${spring.messages.basename}") String encoding;


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
		assertEquals(errorCase.getCode(), "dataError.code");
		String message = messageSource.getMessage(errorCase.getCode(), null, Locale.getDefault());
		System.out.println(message);
		System.out.println(encoding);

		// WHEN
//		int code = ErrorCaseResolver.getCode(errorCase);
//		String msg = ErrorCaseResolver.getMsg(errorCase);
//		HttpStatus status = ErrorCaseResolver.getStatus(errorCase);
//		code: "-9999"
//		msg: "형식에 맞지 않는 데이터 오류가 발생하였습니다."
//		status: 404

		// THEN
//		assertEquals("형식에 맞지 않는 데이터 오류가 발생하였습니다.", msg);
//		assertEquals(HttpStatus.BAD_REQUEST, status);

		// WHEN
//		int code = new ErrorCaseResolver(messageSource).getCodeTest(errorCase);
		// THEN
//		assertEquals(-9999, code);
	}
}