package com.lxisoft.service.impl;

import com.lxisoft.service.AttendedExamService;
import com.lxisoft.domain.AttendedExam;
import com.lxisoft.repository.AttendedExamRepository;
import com.lxisoft.service.dto.AttendedExamDTO;
import com.lxisoft.service.mapper.AttendedExamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AttendedExam}.
 */
@Service
@Transactional
public class AttendedExamServiceImpl implements AttendedExamService {

    private final Logger log = LoggerFactory.getLogger(AttendedExamServiceImpl.class);

    private final AttendedExamRepository attendedExamRepository;

    private final AttendedExamMapper attendedExamMapper;

    public AttendedExamServiceImpl(AttendedExamRepository attendedExamRepository, AttendedExamMapper attendedExamMapper) {
        this.attendedExamRepository = attendedExamRepository;
        this.attendedExamMapper = attendedExamMapper;
    }

    @Override
    public AttendedExamDTO save(AttendedExamDTO attendedExamDTO) {
        log.debug("Request to save AttendedExam : {}", attendedExamDTO);
        AttendedExam attendedExam = attendedExamMapper.toEntity(attendedExamDTO);
        attendedExam = attendedExamRepository.save(attendedExam);
        return attendedExamMapper.toDto(attendedExam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendedExamDTO> findAll() {
        log.debug("Request to get all AttendedExams");
        return attendedExamRepository.findAll().stream()
            .map(attendedExamMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AttendedExamDTO> findOne(Long id) {
        log.debug("Request to get AttendedExam : {}", id);
        return attendedExamRepository.findById(id)
            .map(attendedExamMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttendedExam : {}", id);
        attendedExamRepository.deleteById(id);
    }
}
