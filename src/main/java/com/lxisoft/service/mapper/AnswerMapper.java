package com.lxisoft.service.mapper;


import com.lxisoft.domain.*;

import com.lxisoft.service.dto.AnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Answer} and its DTO {@link AnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {


    @Mapping(target = "question", ignore = true)
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
