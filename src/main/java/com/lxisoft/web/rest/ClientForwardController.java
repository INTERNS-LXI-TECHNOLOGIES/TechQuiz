package com.lxisoft.web.rest;

import com.lxisoft.domain.Answer;
import com.lxisoft.domain.QnOption;
import com.lxisoft.domain.Question;
//import com.lxisoft.service.QuestionService;
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
import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.impl.ExamServiceImpl;
import com.lxisoft.service.impl.QuestionServiceImpl;

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
    			i=0;
    			model.setViewName("examresult");
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


