package com.lxisoft.web.rest;

import com.lxisoft.domain.Answer;
import com.lxisoft.domain.QnOption;
import com.lxisoft.domain.Question;
import com.lxisoft.model.ExamModel;
import com.lxisoft.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import com.lxisoft.domain.Exam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ClientForwardController {
    @Autowired
    private QuestionService questionService;

    /**
     * Forwards any unmapped paths (except those containing a period) to the client {@code index.html}.
     * @return forward to client {@code index.html}.
     */
    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String forward() {
        return "forward:/";
    }

    @GetMapping(value="/instruction")
    public String root() {return "instruction";}

    @RequestMapping(value = "/createxam")
    public String newContact(Model model) {
    	Exam exam=new Exam();
    	model.addAttribute("exam",exam);
		return "createxam";
	}
    @GetMapping(value = "/createxam")
    public String addNewQuestion(@ModelAttribute ExamModel examModel){
        List<QnOption> qnOptions = new ArrayList<>();
        Question question = examModel.getQuestion();
        Answer answer = examModel.getAnswer();
        answer.setQuestion(question);
        question.setAnswer(answer);

        QnOption option1 = new QnOption();
        QnOption option2 = new QnOption();
        QnOption option3 = new QnOption();
        QnOption option4 = new QnOption();
        option1.setOption(examModel.getOption1());
        option2.setOption(examModel.getOption2());
        option3.setOption(examModel.getOption3());
        option4.setOption(examModel.getOption4());
        option1.setQuestion(question);
        option2.setQuestion(question);
        option3.setQuestion(question);
        option4.setQuestion(question);
        qnOptions.add(option1);
        qnOptions.add(option2);
        qnOptions.add(option3);
        qnOptions.add(option4);
       // question.setOptions(qnOptions);
        questionService.saveQuestion(question);
        return "home";
    }

    }
