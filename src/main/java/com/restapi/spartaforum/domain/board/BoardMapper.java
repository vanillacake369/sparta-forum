package com.restapi.spartaforum.domain.board;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    //    @Mapping(target = "user,password", ignore = true)
    BoardResponseDTO questionToResponseDto(Board question);

    //    @Mapping(target = "user", ignore = true)
    Board requestDtoToQuestion(BoardRequestDTO boardRequestDto);
}
