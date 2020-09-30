package com.lxisoft.service.mapper;


import com.lxisoft.domain.*;
import com.lxisoft.service.dto.ExamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exam} and its DTO {@link ExamDTO}.
 */
@Mapper(componentModel = "spring", uses = {AttendedExamMapper.class})
public interface ExamMapper extends EntityMapper<ExamDTO, Exam> {

    @Mapping(source = "attendedExam.id", target = "attendedExamId")
    ExamDTO toDto(Exam exam);

    @Mapping(source = "attendedExamId", target = "attendedExam")
    Exam toEntity(ExamDTO examDTO);

    default Exam fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exam exam = new Exam();
        exam.setId(id);
        return exam;
    }
}
