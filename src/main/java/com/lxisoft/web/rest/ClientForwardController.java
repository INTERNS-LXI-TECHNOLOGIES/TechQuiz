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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import com.lxisoft.domain.Exam;
import com.lxisoft.service.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;


@Controller
public class ClientForwardController {
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private ExamService examService;

    /**
     * Forwards any unmapped paths (except those containing a period) to the client {@code index.html}.
     * @return forward to client {@code index.html}.
     */
    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String forward() {
        return "forward:/";
    }
    @GetMapping(value="/techquiz")
    public String first() {return "techquiz";}

    @GetMapping(value="/instruction")
    public String root() {return "instruction";}

    @RequestMapping(value = "/createxam")
    public String newContact(Model model) {
    	Exam exam=new Exam();
    	model.addAttribute("exam",exam);
		return "createxam";
	}
    
    
    @RequestMapping ("saveexam")
    public String saveExam(Exam exam,Model model)
	{
		examService.saveExam(exam);
			
		return "createxam";
	}
    
    @GetMapping(value="/viewQuestion")
    public String viewQuestion(HttpServletRequest request) {
    	HttpSession session = request.getSession(true);
    	
    	List<Question> listQuestion = questionService.getAll();
    	Question question=listQuestion.get(0);
    	question.getQuestion();
	    
    	session.setAttribute("listExam", listQuestion); 
    	return "redirect:/view";
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
//        question.setOptions(qnOptions);
        questionService.saveQuestion(question);
        return "home";
    }

    }
