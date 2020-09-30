package com.lxisoft.service.impl;

import com.lxisoft.service.ExamService;

import com.lxisoft.domain.Exam;
import com.lxisoft.domain.Question;
import com.lxisoft.repository.ExamRepository;
import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.mapper.ExamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Exam}.
 */
@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    private final Logger log = LoggerFactory.getLogger(ExamServiceImpl.class);

    private final ExamRepository examRepository;

    private final ExamMapper examMapper;
    
	@Autowired
	QuestionServiceImpl questionServiceImpl;
	
    @Autowired
	  private ExamRepository examRepo;
	 

    public ExamServiceImpl(ExamRepository examRepository, ExamMapper examMapper) {
        this.examRepository = examRepository;
        this.examMapper = examMapper;
    }

    @Override
    public ExamDTO save(ExamDTO examDTO) {
        log.debug("Request to save Exam : {}", examDTO);
        Exam exam = examMapper.toEntity(examDTO);
        exam = examRepository.save(exam);
        return examMapper.toDto(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> findAll() {
        log.debug("Request to get all Exams");
        return examRepository.findAll().stream()
            .map(examMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ExamDTO> findOne(Long id) {
        log.debug("Request to get Exam : {}", id);
        return examRepository.findById(id)
            .map(examMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exam : {}", id);
        examRepository.deleteById(id);
    }
    public Exam createOrUpdateExam(Exam exam) 
    {
        if(exam.getId()  == null) 
        {
        	exam = examRepo.save(exam);
             
            return exam;
        } 
        else
        {
            Optional<Exam> exams = examRepo.findById(exam.getId());
             
            if(exams.isPresent()) 
            {
            	Exam newEntity = exams.get();
                newEntity.setName(exam.getName());
                newEntity.setCount(exam.getCount());
                newEntity.setLevel(exam.getLevel());
 
                newEntity = examRepo.save(newEntity);
                 
                return newEntity;
            } else {
                exam = examRepo.save(exam);
                 
                return exam;
            }
        }
   
    }
    @Transactional(readOnly = true)
    public Exam getOne(long id)
    {
        return examRepository.getOne(id);
    }
    
    }
