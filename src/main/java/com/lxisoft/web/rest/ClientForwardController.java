package com.lxisoft.web.rest;

import com.lxisoft.domain.Answer;



import com.lxisoft.domain.QnOption;
import com.lxisoft.domain.Question;
import com.lxisoft.domain.enumeration.QuestionLevel;
import com.lxisoft.service.dto.QuestionDTO;

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
import com.lxisoft.service.impl.*;
import com.lxisoft.service.*;
import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.dto.AnswerDTO;
import com.lxisoft.model.TechQuizModel;
import com.lxisoft.model.ExamModel;
import com.lxisoft.domain.enumeration.QuestionLevel;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import com.lxisoft.domain.Exam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ClientForwardController {

    private final Logger log = LoggerFactory.getLogger(ClientForwardController.class);

//    @Autowired
//    private ExamService examService;
//
    int i=0;
    @Autowired
    private QnOptionService optService;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

  //  @Autowired
//    private AnswerServiceImpl answerServiceImpl;

    @Autowired
    private ExamServiceImpl examServiceImpl;

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



     /*@GetMapping("/login")
    public String login1(Model model)
    {
        return "login";
    }*/

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String adminpage(Model model)
    {
        return "instruction";
    }

    @RequestMapping(value = "/createxam", method = RequestMethod.GET)
    public String newContact(Model model) {
    	ExamDTO examDto=new ExamDTO();
    	model.addAttribute("examDto",examDto);
		return "createxam";
	}

    @RequestMapping ("saveexam")
    public String saveExam(ExamDTO examDto,Model model)
	{
		examServiceImpl.save(examDto);
		return "redirect:/viewAll";
	}

    @GetMapping(value = "/viewAll")
    public ModelAndView listExam(ModelAndView model) throws IOException {
        List<ExamDTO> listExam = examServiceImpl.findAll();
        model.addObject("listExam", listExam);
        model.setViewName("read");
        return model;
    }

    @GetMapping(value="viewQuestion")
    public ModelAndView viewQuestion(ModelAndView model,HttpServletRequest request) {
    	List<QuestionDTO> listQuestion = questionServiceImpl.findAll();
    	if(i<listQuestion.size())
    	{
	    	QuestionDTO question=listQuestion.get(i);
	    	question.getQuestion();
	    	question.getAnswerId();
	        model.addObject("question", question);
	    	model.addObject("question", question);
	       // model.addObject("question", qnoption);
	        model.setViewName("questionview");
	        i++;
	        return model;
    		}
    		else
    		{
    			model.setViewName("examresult");
    			return model;
    		}
    }

    @GetMapping(value="/examresult")
    public String result() {return "examresult";}


//    @GetMapping(value="/viewQuestion")
//    public String viewQuestion(HttpServletRequest request) {
//    	HttpSession session = request.getSession(true);
//
//    	List<Question> listQuestion = questionService.getAll();
//    	Question question=listQuestion.get(0);
//    	question.getQuestion();
//
//    	session.setAttribute("listExam", listQuestion);
//    	return "redirect:/view";
//    }

   
    /* @RequestMapping(value = "/newquestion", method = RequestMethod.GET)
    public ModelAndView newQuestion(ModelAndView model) {
        ExamModel examModel = new ExamModel();
        model.addObject("examModel", examModel);
        model.setViewName("add");
        return model;
   }
    @RequestMapping (value="/add")
    public String saveQuestion(@ModelAttribute ExamModel examModel)
    {
        ModelAndView modelAndView =new ModelAndView();
        AnswerDTO answerDTO=new AnswerDTO();
        answerDTO.setAnswer(examModel.getAnswer().getAnswer());
        QuestionDTO questionDTO=new QuestionDTO();
        questionDTO.setQuestion(examModel.getQuestion().getQuestion());
        //questionDTO.setAnswerId(2L);

        answerServiceImpl.save(answerDTO);
        questionServiceImpl.save(questionDTO);
        return "techquiz";
    }
    */
    @RequestMapping(value = "/newquestion", method = RequestMethod.GET)
    public ModelAndView newQuestion(ModelAndView model) {
    	TechQuizModel techModel=new TechQuizModel();
    	model.addObject("techModel", techModel);
        model.setViewName("addquestion");
        return model;
   }
    @RequestMapping(value = "/addQuestion")
    public String addNewQuestion(@ModelAttribute TechQuizModel techModel)
    {
    	 Question question = new Question();
         String ques = techModel.getQuestion();
         question.setQuestion(ques);
         Answer answer = new Answer();
         answer.setQuestion(question);
         answer.setAnswer(techModel.getAnswer());
         question.setAnswer(answer);
         Set<QnOption> qnOptions = new HashSet<>();
         
         QnOption option1 = new QnOption();
         QnOption option2 = new QnOption();
         QnOption option3 = new QnOption();
         QnOption option4 = new QnOption();
    
         option1.setOption(techModel.getOption1());
         option2.setOption(techModel.getOption2());
         option3.setOption(techModel.getOption3());
         option4.setOption(techModel.getOption4());
       
         option1.setQuestion(question);
         option2.setQuestion(question);
         option3.setQuestion(question);
         option4.setQuestion(question);
    
         qnOptions.add(option1);
         qnOptions.add(option2);
         qnOptions.add(option3);
         qnOptions.add(option4);
         question.setQnOptions(qnOptions);
         questionServiceImpl.saveQuestionWithEnity(question);
         return "techquiz";
         }
    
}


