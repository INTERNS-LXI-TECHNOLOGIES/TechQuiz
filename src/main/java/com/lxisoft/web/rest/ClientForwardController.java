package com.lxisoft.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lxisoft.model.ExamModel;

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
    
    @RequestMapping(value = "/exams", method = RequestMethod.GET)
    public ModelAndView newContact(ModelAndView model) {
        ExamModel examModel = new ExamModel();
        model.addObject("examModel", examModel);
        model.setViewName("add");
        return model;
    }

}
