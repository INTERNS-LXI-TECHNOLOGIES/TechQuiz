package com.lxisoft.web.rest;


import com.lxisoft.domain.Answer;

import com.lxisoft.domain.QnOption;
import com.lxisoft.domain.Question;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.impl.QuestionServiceImpl;
import com.lxisoft.security.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.lxisoft.domain.User;
import com.lxisoft.domain.Exam;
import com.lxisoft.domain.*;
import com.lxisoft.service.impl.*;
import com.lxisoft.service.*;
import com.lxisoft.service.dto.*;
import com.lxisoft.model.TechQuizModel;
import com.lxisoft.model.ExamModel;
import com.lxisoft.domain.enumeration.*;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;
import com.lxisoft.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private UserRepository userRepository;

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


     @GetMapping(value="/userview")
    public String userView(){return "userview";}

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
  public String viewQuestion(HttpServletRequest request) {
  	
  	String level = null;
  	if(request.getParameter("option") == null){
  		level = "EASY"; 
  	}
  	else {
  		level = request.getParameter("option");
  	}
  	 
  	List<QuestionDTO> listQuestion = questionServiceImpl.findAll();
  	HttpSession session = request.getSession(true);
  	
  	List<QuestionDTO> easyQuestion = new ArrayList<>();
  	List<QuestionDTO> mediumQuestion = new ArrayList<>();
  	List<QuestionDTO> hardQuestion = new ArrayList<>();
  	
  	
  	for(QuestionDTO questionDTO : listQuestion) {
  		QuestionLevel questionLevel = questionDTO.getQuestionlevel();
  		if(questionLevel == QuestionLevel.EASY) {
  			easyQuestion.add(questionDTO);
  		}
  		else if(questionLevel == QuestionLevel.MEDIUM) {
  			mediumQuestion.add(questionDTO);
  		}
  		else if(questionLevel == QuestionLevel.HARD) {
  			hardQuestion.add(questionDTO);
  		}
  	}
  	
  	if(level.equals("EASY")) {
  		session.setAttribute("listQuestion", easyQuestion);
  	}
  	else if(level.equals("MEDIUM")) {
  		session.setAttribute("listQuestion", mediumQuestion);
  	}
  	else if(level.equals("HARD")) {
  		session.setAttribute("listQuestion", hardQuestion);
  	}
  	
  	return "redirect:/questionview";
  	    }
  
  
//  @GetMapping("/questionview")
//  public ModelAndView viewQuestion(ModelAndView model,HttpServletRequest request) {
//  	HttpSession session = request.getSession(true);
//  }
 
    @GetMapping("/questionview")
    public ModelAndView viewQuestion(ModelAndView model,HttpServletRequest request) {

    	List<QuestionDTO> listQuestion = questionServiceImpl.findAll();
    	if(i<listQuestion.size())
    	{
	    	QuestionDTO question=listQuestion.get(i);
	    	question.getQuestion();
	    	question.getAnswerId();
	        /*model.addObject("question", question);*/
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

    @SuppressWarnings("static-access")
    @GetMapping(value = "/dashboard")
    public ModelAndView userDashboard(ModelAndView model) throws IOException {

        
        Optional<User> usersDet = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
        /*Optional<String> users = SecurityUtils.getCurrentUserLogin();
        String userName= users.get();*/


        
        usersDet.ifPresent(user -> {
            model.addObject("user", user);
            
        });

        
        model.setViewName("dashboard");
        return model;
    }
    	/*HttpSession session = request.getSession(true);
		@SuppressWarnings("unchecked")*/
        /*@GetMapping(value="viewQuestion")
        public ModelAndView viewQuestion(ModelAndView model,HttpServletRequest request) {
		List<QuestionDTO> listQuestion = (List<QuestionDTO>)session.getAttribute("listQuestion");
		if(i<listQuestion.size())
  	{
		 model.addObject("listQuestion", listQuestion.get(i));
		 i++;
		 model.setViewName("questionview"); 
      return model;
		}
		else
		{
			i=0;
			model.setViewName("redirect:/examresult");
			return model;
		}
  }
*/

  
//List<QuestionDTO> listQuestion = questionServiceImpl.findAll();
//List<QuestionDTO> listExam = new ArrayList<>();
//HttpSession session = request.getSession(true);
//
//for(int j=0;j<listQuestion.size();j++)
//{
//	QuestionDTO question=listQuestion.get(j);
//	question.getQuestion();
//	question.getAnswerId();
//	
////    listExam.add(exam);
//    
//}
//session.setAttribute("listQuestion", listQuestion); 
//return "redirect:/questionview";

    @GetMapping(value="/examresult")
    public String result()
    {
    	return "examresult";
    }
    
    @GetMapping(value="/selectExam")
    public String selectExam() 
    {
    	return "selectexam";
    	} 




//    @RequestMapping(value = "/newquestion", method = RequestMethod.GET)
//    public ModelAndView question(ModelAndView model)
//    {
//        Question question=new Question();
//        model.addObject("question",question);
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
//        return model;

//    @RequestMapping (value="/add")
//    public String saveQuestion(@ModelAttribute ExamModel examModel)
//    {
//        ModelAndView modelAndView =new ModelAndView();
//        AnswerDTO answerDTO=new AnswerDTO();
//        answerDTO.setAnswer(examModel.getAnswer().getAnswer());
//        QuestionDTO questionDTO=new QuestionDTO();
//        questionDTO.setQuestion(examModel.getQuestion().getQuestion());
//        //questionDTO.setAnswerId(2L);
//
//        answerServiceImpl.save(answerDTO);
//        questionServiceImpl.save(questionDTO);
//        return "techquiz";
//    }

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


