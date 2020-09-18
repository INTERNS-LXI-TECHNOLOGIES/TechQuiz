package com.lxisoft.web.rest;

import com.lxisoft.domain.Answer;

import com.lxisoft.domain.QnOption;
import com.lxisoft.domain.Question;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.impl.QuestionServiceImpl;
import com.lxisoft.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import com.lxisoft.domain.User;
import com.lxisoft.domain.Exam;
import com.lxisoft.service.impl.*;
import com.lxisoft.service.*;
import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.dto.AnswerDTO;
import com.lxisoft.model.ExamModel;
import com.lxisoft.service.impl.ExamServiceImpl;
import com.lxisoft.service.impl.QuestionServiceImpl;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import com.lxisoft.domain.Exam;
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

    @Autowired
    private AnswerServiceImpl answerServiceImpl;

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
    public String userView() {return "userview";}

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
    public String newQuestion(Model model) {
        QuestionDTO questionDTO=new QuestionDTO();
        model.addAttribute("questionDto",questionDTO);
        return "add";
    }*/
   @RequestMapping(value = "/newquestion", method = RequestMethod.GET)
    public ModelAndView newQuestion(ModelAndView model) {
        ExamModel examModel = new ExamModel();
        model.addObject("examModel", examModel);
        model.setViewName("add");
        return model;
   }
 /*   @RequestMapping (value="/add")
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

  /*  @RequestMapping(value = "/addQuestion")
    public String addNewQuestion(@ModelAttribute Exam exam){

        @GetMapping(value = "/add")
        public String addNewQuestion(@ModelAttribute Exam exam){
        	List<AnsOption> ansOptions = new ArrayList<>();
            Question question = exam.getQuestion();
            Answer answer = exam.getAnswer();
            answer.setQuestion(question);
            question.setAnswer(answer);
            
            AnsOption option1 = new AnsOption();
            AnsOption option2 = new AnsOption();
            AnsOption option3 = new AnsOption();
            AnsOption option4 = new AnsOption();
                 
            option1.setAOption(exam.getOption1());
            option2.setAOption(exam.getOption2());
            option3.setAOption(exam.getOption3());
            option4.setAOption(exam.getOption4());

            option1.setQuestion(question);
            option2.setQuestion(question);
            option3.setQuestion(question);
            option4.setQuestion(question);

            ansOptions.add(option1);
            ansOptions.add(option2);
            ansOptions.add(option3);
            ansOptions.add(option4);

            question.setOptions(ansOptions);        
            questionService.saveQuestion(question);
           return "techquiz";
        }
    }*/
    
}


