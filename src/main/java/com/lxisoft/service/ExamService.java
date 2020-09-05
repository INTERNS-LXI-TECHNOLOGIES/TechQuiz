package com.lxisoft.service;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lxisoft.domain.*;
import com.lxisoft.domain.enumeration.ExamLevel;
import com.lxisoft.repository.*;

@Service
@Transactional
public class ExamService {

    private final Logger log = LoggerFactory.getLogger(ExamService.class);
    

	@Autowired
    QuestionService questionService;
    
    @Autowired
    ExamRepository examRepository;
    
    public void saveExam(Exam exam) 
	{
		
			examRepository.save(exam);
		
	}


}
