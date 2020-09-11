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
    	Question qn=new Question();
    	Set<Question> qstns = new HashSet<Question>(); 
    	List<Question> listQuestion = questionService.getAll();
    	int count=exam.getCount();
    	int c=0;
    	int ct=0;
		ExamLevel level=exam.getLevel();
		for(int i=0;i<listQuestion.size();i++)
		{
			if(qn.getQuestionlevel().equals(level))
				c++;
		}
		
		if(c>=count)
		{
			for(Question qns:qstns)
			{
				if(qns.getQuestionlevel().equals(level) && (ct<count))
				{
					ct++;
					qstns.add(qns);
				}
			}
		}
			examRepository.save(exam);
		
	}


}
