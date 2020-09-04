package com.lxisoft.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import com.lxisoft.domain.Exam;


@Controller
public class ClientForwardController {

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
    }
