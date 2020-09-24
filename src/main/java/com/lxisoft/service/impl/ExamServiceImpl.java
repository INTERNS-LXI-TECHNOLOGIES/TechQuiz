package com.lxisoft.service.impl;

import com.lxisoft.service.ExamService;


import com.lxisoft.domain.*;
import com.lxisoft.repository.ExamRepository;
import com.lxisoft.repository.QuestionRepository;
import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.mapper.ExamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Exam}.
 */
@Service
@Transactional
public class ExamServiceImpl implements ExamService {
	
	@Autowired
	QuestionRepository questionRepo;
	@Autowired
	QuestionServiceImpl questionServiceImpl;
	 @Autowired
	    private ExamRepository examRepo;
	     
	   

    private final Logger log = LoggerFactory.getLogger(ExamServiceImpl.class);

    private final ExamRepository examRepository;

    private final ExamMapper examMapper;

    public ExamServiceImpl(ExamRepository examRepository, ExamMapper examMapper) {
        this.examRepository = examRepository;
        this.examMapper = examMapper;
    }

    @Override
    public ExamDTO save(ExamDTO examDTO) {
    	
//    	Set<QuestionDTO> questionList = new HashSet<>(); 
//		List<QuestionDTO> questions=questionServiceImpl.findAll();
//		
//		int count=examDTO.getCount();
//		int c=0;
//		int qc=0;
//		ExamLevel level=examDTO.getLevel();
//		for(QuestionDTO qn:questions)
//		{
//			if(qn.getQuestionlevel().equals(level))
//				c++;
//		}
//		if(c>=count)
//		{
//			for(QuestionDTO qn:questions)
//			{
//				if(qn.getQuestionlevel().equals(level) && (qc<count))
//				{
//					qc++;
//					questionList.add(qn);
//				}
//			}
//
//		}
		
        log.debug("Request to save Exam : {}", examDTO);
        Exam exam = examMapper.toEntity(examDTO);
        exam = examRepository.save(exam);
        return examMapper.toDto(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> findAll() {
        log.debug("Request to get all Exams");
        return examRepository.findAllWithEagerRelationships().stream()
            .map(examMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    public Page<ExamDTO> findAllWithEagerRelationships(Pageable pageable) {
        return examRepository.findAllWithEagerRelationships(pageable).map(examMapper::toDto);
    }


    //file 

    @Transactional(readOnly = true)
    public Exam getOne(long id)
    {
        return examRepository.getOne(id);
    }
    
    
    public void saveExam(Exam exam)
    {
    	examRepository.save(exam);
    }
    

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamDTO> findOne(Long id) {
        log.debug("Request to get Exam : {}", id);
        return examRepository.findOneWithEagerRelationships(id)
            .map(examMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exam : {}", id);
        examRepository.deleteById(id);
    }
    public List<Exam> listAll() {
        return examRepo.findAll();
    }
}
