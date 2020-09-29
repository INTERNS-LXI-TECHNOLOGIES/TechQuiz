package com.lxisoft.web.rest;


import com.lxisoft.domain.Answer;

import com.lxisoft.domain.QnOption;
import com.lxisoft.domain.Question;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.impl.QuestionServiceImpl;
import com.lxisoft.web.controller.FileController;

import jdk.nashorn.internal.runtime.options.Options;

import com.lxisoft.security.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import com.lxisoft.repository.UserRepository;

import java.io.File;
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


    int i=0;
    @Autowired
    private QnOptionServiceImpl optService;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private ExamServiceImpl examServiceImpl;

    @Autowired
    private UserRepository userRepository;
    

    @Autowired
    private AnswerServiceImpl answerServiceImpl;

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
    
    @RequestMapping(value = "/dlt/{id}", method = RequestMethod.GET)
    public ModelAndView deleteExam(@PathVariable("id") long id,ModelAndView model) {
    	TechQuizModel techModel = new TechQuizModel();
    	examServiceImpl.delete(id);
        model.addObject("techModel", techModel);
        model.setViewName("deleteExam");
        return model;
    }    
    
    @GetMapping(value = "/dlt")
    public ModelAndView deleteExm(@PathVariable("id") long id,ModelAndView model) {
    	long examId = (long)id;
    	examServiceImpl.delete(id);
    	List<ExamDTO> listExam = examServiceImpl.findAll();
    	model.addObject("listExam", listExam);
        model.setViewName("read");
        return model;  
  }  
    
    
//    @GetMapping(value = "/updat/{id}")
//    public ModelAndView updateExam(@PathVariable("id") long id)
//    {
//    	ModelAndView modelAndView = new ModelAndView();
//        Exam exam = examServiceImpl.getOne(id);
//        
//        exam.getId();
//        modelAndView.addObject("updateQ",exam);
//        modelAndView.setViewName("updateExam");
//        return modelAndView;
//    }
//
//    @GetMapping(value = "/updatQ")
//    public String updateExm(@ModelAttribute Exam exam)
//    {
//    	Exam exam=new Exam();
//    	exam = examServiceImpl.getOne(exam.getId());
//    	examServiceImpl.saveExam(exam);
//        return "/succUpdate";
//    } 

    
    
//    @GetMapping("/edt/{id}")
//	public String showUpdateForm(@PathVariable("id") long id, Model model) {
//		Exam exam = examServiceImpl.getOne(id);
//		model.addAttribute("exam", exam);
//		return "updateExam.html";
//	}
//
//	@PostMapping("/updateExam")
//	public String updateExam(@PathVariable("id") long id,BindingResult result,Model model) {
//		ExamDTO examDto=new ExamDTO();
//		examServiceImpl.save(examDto);
////		model.addAttribute("listExam", listExam);
//		return "redirect:/viewAll";
//	}
    
    @RequestMapping(path = "/createExm", method = RequestMethod.POST)
    public String createOrUpdateExam(Exam exam) 
    {
        examServiceImpl.createOrUpdateExam(exam);
        return "redirect:/viewAll";
    }
    
    
    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editExamById(Model model, @PathVariable("id") Optional<Long> id) 
                            
    {
        if (id.isPresent()) {
            Exam exam = examServiceImpl.getOne(id.get());
            model.addAttribute("exam", exam);
        } else {
            model.addAttribute("exam", new Exam());
        }
        return "examUpdate";
    }
	
	
	@GetMapping(value="/viewQuest")
    public String viewQuest(HttpServletRequest request) {
    	List<Question> listQuestion = questionServiceImpl.getAll();
    	List<QnOptionDTO> listOptions =optService.findAll();
    	List<TechQuizModel> listExam = new ArrayList<>();
    	List<QuestionDTO> listQues=new ArrayList<>();
    	HttpSession session = request.getSession(true);
    	for(int j=0;j<listQuestion.size();j++)
    	{
	    	Question question=listQuestion.get(j);
	    	List<QnOption> options=question.getQnOptions();
	    	TechQuizModel exam=new TechQuizModel();       
	    	exam.setQuestion(question.getQuestion());
	    	exam.setAnswer(question.getAnswer().getAnswer());
	    	exam.setQuestionlevel(question.getQuestionlevel());
	    	if(options.size()!=0)	
	    	{
	    	exam.setOption1(options.get(0).getOption());
	    	exam.setOption2(options.get(1).getOption());
	    	exam.setOption3(options.get(2).getOption());
	    	exam.setOption4(options.get(3).getOption());
	    	}
	        listExam.add(exam);
	        
    	}
    	session.setAttribute("listQuestion", listExam); 
    	return "selectexam";
    }	
	
    
	@GetMapping(value="/viewQuestion")
    public String viewQuestions(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
    	String level = null;
    	if(request.getParameter("option") == null){
    		level = "EASY"; 
    	}
    	else {
    		level = request.getParameter("option");
    	}
    	 
    	List<TechQuizModel> listQuestion = (List<TechQuizModel>) session.getAttribute("listQuestion");
    	System.out.println("size="+listQuestion.size());
    	
    	List<TechQuizModel> easyQuestion = new ArrayList<>();
    	List<TechQuizModel> mediumQuestion = new ArrayList<>();
    	List<TechQuizModel> hardQuestion = new ArrayList<>();
    	
    	for(TechQuizModel question : listQuestion) {
    		QuestionLevel questionLevel = question.getQuestionlevel();
    		if(questionLevel == QuestionLevel.EASY) {
    			easyQuestion.add(question);
    		}
    		else if(questionLevel == QuestionLevel.MEDIUM) {
    			mediumQuestion.add(question);
    		}
    		else if(questionLevel == QuestionLevel.HARD) {
    			hardQuestion.add(question);
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
		List<Question> listQuestion = (List<Question>)session.getAttribute("listQuestion");
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

    
    @GetMapping("/selectedOption")
	public String selectedOption(HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		@SuppressWarnings("unchecked")
		List<TechQuizModel> listExam = (List<TechQuizModel>) session.getAttribute("listQuestion");
		int selectedOption = 0;
		  if(request.getParameter("option")!= null)
		  {
			   selectedOption =  Integer.parseInt(request.getParameter("option"));
		  }
		  
		  switch(selectedOption)
		  {
		  case 1 :
			  listExam.get(i-1).setSelectedOption(listExam.get(i-1).getOption1());
			  break;
		  case 2 :
			  listExam.get(i-1).setSelectedOption(listExam.get(i-1).getOption2());
			  break;
		  case 3 :
			  listExam.get(i-1).setSelectedOption(listExam.get(i-1).getOption3());
			  break;
		  case 4 :
			  listExam.get(i-1).setSelectedOption(listExam.get(i-1).getOption4());
			  break;
		  default :
			  listExam.get(i-1).setSelectedOption("");
			  break;
		  }
		  
		  session.setAttribute("listExam", listExam);
			
		return "redirect:/questionview";
	}
    
    @GetMapping(value="/examresult")
    public ModelAndView result(ModelAndView model,HttpSession session)
    {
    	@SuppressWarnings("unchecked")
		List<TechQuizModel> listExam = (List<TechQuizModel>)session.getAttribute("listQuestion");
		int mark = 0;
		for(int i =0;i<listExam.size();i++)
		  {
			if(listExam.get(i).getAnswer().equals(listExam.get(i).getSelectedOption()))
			{
				mark++;
			}
		  }
		model.addObject("mark", mark);
		model.addObject("listQuestion",listExam);
		model.setViewName("examresult");
		return model;
    }
    
    @GetMapping(value="/selectExam")
    public String selectExam() 
    {
    	return "selectexam";
  	} 

    @SuppressWarnings("static-access")
    @GetMapping(value = "/dashboard")
    public ModelAndView userDashboard(ModelAndView model) throws IOException {
        
        Optional<User> usersDet = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
 

        
        usersDet.ifPresent(user -> {
            model.addObject("user", user);
            
        });

        
        model.setViewName("dashboard");
        return model;
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
    	QuestionLevel questionlevel= QuestionLevel.MEDIUM;
//    	questionlevel= QuestionLevel.MEDIUM;
//    	questionlevel= QuestionLevel.HARD;
    	 Question question = new Question();
         String ques = techModel.getQuestion();
         question.setQuestion(ques);
         Answer answer = new Answer();
         answer.setQuestion(question);
         answer.setAnswer(techModel.getAnswer());
         question.setAnswer(answer);
         question.setQuestionlevel(questionlevel);
         
         List<QnOption> qnOptions = new ArrayList<>();
         
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
//        model.addObject("listExam", temp);
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
        Question question = questionServiceImpl.get(id);
        ExamModel exam = new ExamModel();
        exam.setId(question.getId());
        String quest = question.getQuestion();
        question.setQuestion(quest);
        exam.setQuestion(question);
        exam.setAnswer(question.getAnswer());
        exam.setOption1(question.getQnOptions().get(0).getOption());
        exam.setOption2(question.getQnOptions().get(1).getOption());
        exam.setOption3(question.getQnOptions().get(2).getOption());
        exam.setOption4(question.getQnOptions().get(3).getOption());

        modelAndView.addObject("updateQ",exam);
        modelAndView.setViewName("update");
        return modelAndView;
    }
    @GetMapping(value = "/updateQ")
    public String updateQuestion(@ModelAttribute ExamModel exam)
    {
    	Answer answer = answerServiceImpl.get(exam.getId());
    	answerServiceImpl.saveAnswer(answer);
        /*
         * Question question = answerServiceImpl.get(exam.getId());
        Question q = exam.getQuestion();
        question.setQuestion(q.getQuestion());
        question.getAnswer().setAnswer(exam.getAnswer().getAnswer());
        question.getQnOptions().get(0).setOption(exam.getOption1());
        question.getQnOptions().get(1).setOption(exam.getOption2());
        question.getQnOptions().get(2).setOption(exam.getOption3());
        question.getQnOptions().get(3).setOption(exam.getOption4());
        questionServiceImpl.saveQuestion(question);*/
        return "success";
    }     
    
    
    @RequestMapping(value = "/createFile", method = RequestMethod.GET)
    public String newExam(Model model) {

   	ExamDTO examDto=new ExamDTO();
  //  examServiceImpl.createFile();   	
   	model.addAttribute("examDto",examDto);
	return "createfile";
	}

    @RequestMapping (value ="/savexam")
    public String saveExm(Exam examDto,Model model)
	{
    	//FileController fileRepo = new FileController();
    	examServiceImpl.create(examDto);
		return "readFile";

	}
    
   
//    @RequestMapping(value = "/createFile", method = RequestMethod.GET)
//    public String newExam(Model model) {
//   	ExamDTO examDto=new ExamDTO();
//    examServiceImpl.createFile();   	
//   	model.addAttribute("examDto",examDto);
//		return "createfile";
//	}
//   @RequestMapping ("saveFile")
//   public String saveExamUsingFile(List<ExamDTO> examDto,Model model)
//	{
//	   
//  //  examServiceImpl.writeToFile(examDto);
//   	return "redirect:/viewFile";
//	}
   @GetMapping(value = "/viewFile")
   public ModelAndView examFile(ModelAndView model) throws IOException {
     List<ExamDTO> listExam = examServiceImpl.findAll();
   model.addObject("listExam", listExam);
   model.setViewName("readFile");
   return model;
   }
  
}


