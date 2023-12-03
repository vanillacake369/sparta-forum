package com.restapi.spartaforum.global.exception;

import jakarta.annotation.PostConstruct;
import java.util.Locale;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ErrorCaseResolver {

	static MessageSource messageSource;
	final MessageSource wiredMessageSource;

	public ErrorCaseResolver(MessageSource wiredMessageSource) {
		this.wiredMessageSource = wiredMessageSource;
	}

	// 에러 케이스 코드를 반환
	public static int getCode(ErrorCase errorCase) {
		return Integer.parseInt(getMessage(errorCase.getCode()));
	}

	// 에러 메세지를 반환
	public static String getMsg(ErrorCase errorCase) {
		return getMessage(errorCase.getMsg());
	}

	// 에러에 대한 HTTP STATUS를 반환
	public static HttpStatus getStatus(ErrorCase errorCase) {
		return HttpStatus.resolve(
			Integer.parseInt(
				getMessage(errorCase.getStatus())
			)
		);
	}

	// code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
	private static String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, Locale.KOREAN);
//		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
//		왜,,,왜 자꾸 en_US가 뜨는 거냐고오오오오오
	}

	// code정보에 해당하는 메시지를 조회합니다.
	private static String getMessage(String code) {
		return getMessage(code, null);
	}

	@PostConstruct
	public void init() {
		messageSource = wiredMessageSource;
	}
}
