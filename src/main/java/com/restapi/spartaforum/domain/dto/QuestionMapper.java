package com.restapi.spartaforum.domain.dto;

import com.restapi.spartaforum.domain.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    //    @Mapping(target = "user,password", ignore = true)
    QuestionResponseDto questionToResponseDto(Question question);

    //    @Mapping(target = "user", ignore = true)
    Question requestDtoToQuestion(QuestionRequestDto questionRequestDto);
}
