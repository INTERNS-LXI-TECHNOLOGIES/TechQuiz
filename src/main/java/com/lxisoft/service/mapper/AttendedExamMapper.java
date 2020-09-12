package com.lxisoft.service.mapper;


import com.lxisoft.domain.*;
import com.lxisoft.service.dto.AttendedExamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttendedExam} and its DTO {@link AttendedExamDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttendedExamMapper extends EntityMapper<AttendedExamDTO, AttendedExam> {


    @Mapping(target = "exams", ignore = true)
    @Mapping(target = "removeExam", ignore = true)
    AttendedExam toEntity(AttendedExamDTO attendedExamDTO);

    default AttendedExam fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttendedExam attendedExam = new AttendedExam();
        attendedExam.setId(id);
        return attendedExam;
    }
}
