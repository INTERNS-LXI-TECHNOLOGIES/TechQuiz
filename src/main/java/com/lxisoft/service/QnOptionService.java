package com.lxisoft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import com.lxisoft.domain.QnOption;
import com.lxisoft.domain.Question;
import com.lxisoft.repository.*;
import com.lxisoft.service.*;

@Service
@Transactional
public class QnOptionService {

    private final Logger log = LoggerFactory.getLogger(QnOptionService.class);

    @Autowired
    private QnOptionRepository optionRepository;

    @Autowired
    private QuestionService questionService;

    public void save(QnOption qnOption)
    {
        optionRepository.save(qnOption);

    }
    public Question saveQnOption( Question question,String[] options)
    {
        for(int i=0;i<options.length;i++)
        {
            QnOption opt=new QnOption();
            opt.setOption(options[i]);
            questionService.saveQuestion(question);
            opt.setQuestion(question);
            save(opt);
        }
        return question;
    }
    
    public List<QnOption> getAll()
    {

        return  optionRepository.findAll();
    }
    
    
	public QnOption findById(String opt_Id) {
		long id=Integer.parseInt(opt_Id);
		QnOption option=null;
			Optional<QnOption> optional=optionRepository.findById(id);
			if(optional.isPresent())
			{
				option=optional.get();
			}
		return option;
	}

}
