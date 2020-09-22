package com.lxisoft.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.lxisoft.domain.*;

import com.lxisoft.service.impl.*;
import com.lxisoft.service.*;
import com.lxisoft.service.dto.*;
import com.lxisoft.model.TechQuizModel;
import com.lxisoft.model.ExamModel;
import com.lxisoft.domain.enumeration.*;

import org.springframework.ui.Model;

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
    	ExamController filerepo = new ExamController();
    //	filerepo.save(examDto);
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
  
    @GetMapping("/questionview")
    public ModelAndView viewQuestion(ModelAndView model,HttpServletRequest request) {
    	HttpSession session = request.getSession(true);
		@SuppressWarnings("unchecked")
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
         return "redirect:/viewAllQn";
         }
  

    
    @GetMapping(value = "/viewAllQn")
    public ModelAndView listQuestion(ModelAndView model) throws IOException {
        List<Question> listExam = questionServiceImpl.getAll();
        model.addObject("listExam", listExam);
        model.setViewName("view");
        return model;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteQuestion(@PathVariable("id") int id,ModelAndView model) {
    	TechQuizModel techModel = new TechQuizModel();
    	questionServiceImpl.deleteById(id);
        model.addObject("techModel", techModel);
        model.setViewName("deleteconfirmation");
        return model;
    }    
    
    @GetMapping(value = "/delete")
    public ModelAndView deleteQuest(@PathVariable("id") int id,ModelAndView model) {
    	long examId = (long)id;
    	questionServiceImpl.deleteById(id);
    	List<Question> listExam = questionServiceImpl.getAll();
    	model.addObject("listExam", listExam);
        model.setViewName("view");
        return model;  
  }  
 
    
   @GetMapping(value = "/update/{id}")
    public ModelAndView updateQuestion(@PathVariable("id") long id)
    {
	      
	   ModelAndView modelAndView = new ModelAndView();
	   Question question =new Question();
       question = questionServiceImpl.get(id);
       ExamModel exam = new ExamModel();
       exam.setId(question.getId());
       String quest = question.getQuestion();
       question.setQuestion(quest);
       exam.setQuestion(question);
       exam.setAnswer(question.getAnswer());
      /* exam.setOption1(question.getOptions().get(0).getAOption());
       exam.setOption2(question.getOptions().get(1).getAOption());
       exam.setOption3(question.getOptions().get(2).getAOption());
       exam.setOption4(question.getOptions().get(3).getAOption());*/

       modelAndView.addObject("updateQ",exam);
       modelAndView.setViewName("update");
                    
        return modelAndView;
                        
        
    }
    
   @GetMapping(value = "/updateQ")
   public String updateQuestion(@ModelAttribute ExamModel exam)
   {
	   Question question =new Question();
       question = questionServiceImpl.get(exam.getId());
       Question q = exam.getQuestion();
       question.setQuestion(q.getQuestion());
       question.getAnswer().setAnswer(exam.getAnswer().getAnswer());
     /*  question.getOptions().get(0).setAOption(exam.getOption1());
       question.getOptions().get(1).setAOption(exam.getOption2());
       question.getOptions().get(2).setAOption(exam.getOption3());
       question.getOptions().get(3).setAOption(exam.getOption4());*/
       questionServiceImpl.saveQuestion(question);
       return "view";
   }   
  
}


