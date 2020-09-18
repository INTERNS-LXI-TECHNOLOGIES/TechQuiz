package com.lxisoft.web.rest;

import com.lxisoft.domain.*;
import com.lxisoft.domain.enumeration.ExamLevel;
import com.lxisoft.domain.enumeration.QuestionLevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import com.lxisoft.service.*;
import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.impl.ExamServiceImpl;
import com.lxisoft.service.impl.QuestionServiceImpl;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




@Controller
public class ClientForwardController {
    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private ExamServiceImpl examServiceImpl;

    int i=0;
    @Autowired
    private QnOptionService optService;



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
    
//
//    @GetMapping(value="viewQuestion")
//    public ModelAndView viewQuestion(ModelAndView model) {
//    	List<QuestionDTO> listQuestion = questionServiceImpl.findAll();
//    	
//    	if(i<listQuestion.size())
//    	{
//	    	QuestionDTO question=listQuestion.get(i);
//	    	question.getQuestion();
//	    	question.getAnswerId();
//
//	        model.addObject("question", question);
//	        model.setViewName("questionview");
//	        i++;
//	        return model;
//    	}
//		else
//		{
//			i=0;
//			model.setViewName("examresult");
//			return model;
//		}
//    	}
    
  enum  TestEnum{
	  EASY,MEDIUM,HARD;
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



    
    @GetMapping("/questionview")
    public ModelAndView viewQuestion(ModelAndView model,HttpServletRequest request) {
    	HttpSession session = request.getSession(true);
		@SuppressWarnings("unchecked")
		List<QuestionDTO> listQuestion = (List<QuestionDTO>)session.getAttribute("listQuestion");
//		if (listQuestion.size()== 0)
//		{
//			listQuestion = questionServiceImpl.findAll();
//		}
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


//   @RequestMapping(value = "/newquestion", method = RequestMethod.GET)
//    public ModelAndView newQuestion(ModelAndView model) {
//        Question question = new Question();
//        model.addObject("question", question);
//        model.setViewName("add");
//        return model;
//   }


    @RequestMapping(value = "/newquestion", method = RequestMethod.GET)
    public ModelAndView question(ModelAndView model)
    {
        Question question=new Question();
        model.addObject("question",question);
        model.setViewName("add");
        return model;
    }
    
   /* @GetMapping(value = "/add")
    public String addNewQuestion(@ModelAttribute Question question){
        List<QnOption> qnOptions = new ArrayList<>();
        Answer answer = question.getAnswer();
        answer.setQuestion(question);
        question.setAnswer(answer);
        QnOption option1 = new QnOption();
        QnOption option2 = new QnOption();
        QnOption option3 = new QnOption();
        QnOption option4 = new QnOption();
        option1.setOption(question.getQuestion());
        option2.setOption(question.getQuestion());
        option3.setOption(question.getQuestion());
        option4.setOption(question.getQuestion());
        option1.setQuestion(question);
        option2.setQuestion(question);
        option3.setQuestion(question);
        option4.setQuestion(question);
        qnOptions.add(option1);
        qnOptions.add(option2);
        qnOptions.add(option3);
        qnOptions.add(option4);
        question.setOptions(qnOptions);
        questionService.saveQuestion(question);
        return "add";
    }*/
//   @RequestMapping(value="/add")
//   public String createExam( Question question ,BindingResult bindingResult,@RequestParam String[] options,Model model)
//   {
//           questionService.saveQuestion(question);
//           optService.saveQnOption(question, options);
//           model.addAttribute("success", true);
//           return "add";
//       }
   }


