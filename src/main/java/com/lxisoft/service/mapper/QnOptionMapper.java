package com.lxisoft.service.mapper;


import com.lxisoft.domain.*;
import com.lxisoft.service.dto.QnOptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QnOption} and its DTO {@link QnOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface QnOptionMapper extends EntityMapper<QnOptionDTO, QnOption> {

    @Mapping(source = "question.id", target = "questionId")
    QnOptionDTO toDto(QnOption qnOption);

    @Mapping(source = "questionId", target = "question")
    QnOption toEntity(QnOptionDTO qnOptionDTO);

    default QnOption fromId(Long id) {
        if (id == null) {
            return null;
        }
        QnOption qnOption = new QnOption();
        qnOption.setId(id);
        return qnOption;
    }
}
