package com.restapi.spartaforum.domain.question.dto;

import com.restapi.spartaforum.domain.question.entity.Question;
import java.time.LocalDateTime;

public record QuestionResponseDto(
	Long id,
	String title,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt) {


	public static QuestionResponseDto of(Question savedQuestion) {
		return new QuestionResponseDto(savedQuestion.getId(),
			savedQuestion.getTitle(),
			savedQuestion.getCreatedAt(),
			savedQuestion.getModifiedAt());
	}
}
