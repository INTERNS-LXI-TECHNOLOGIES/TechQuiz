package com.lxisoft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lxisoft.domain.QnOption;
import com.lxisoft.domain.Question;
import com.lxisoft.repository.*;
import com.lxisoft.service.*;

@Service
@Transactional
public class QnOptionService {

    private final Logger log = LoggerFactory.getLogger(QnOptionService.class);

    @Autowired
    private QnOptionService optionService;
    @Autowired
    private QuestionService questionService;

    public void save(QnOption qnOption)
    {
        optionRepository.save(qnOption);

    }
    public Question saveQnOption( Question question,String ...options)
    {
        log.debug("options saving {} "+options[0],options[1],options[2]);
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

}
