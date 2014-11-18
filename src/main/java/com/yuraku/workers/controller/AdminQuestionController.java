package com.yuraku.workers.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yuraku.workers.domain.TtQuizees;
import com.yuraku.workers.domain.TtQuizeesRepository;

@Controller
public class AdminQuestionController extends BaseController {

    @Autowired
    TtQuizeesRepository ttQuizeesRepository;
    
    @RequestMapping(value="/admin_question_input", method=RequestMethod.GET)
    public String input(TtQuizees ttQuizees){
        return "admin_question_input";
    }

    @RequestMapping(value="/admin_question_entry", method=RequestMethod.POST)
    public String questionEntry(@Valid TtQuizees ttQuizees, BindingResult bindingResult){
    	if (bindingResult.hasErrors()) {
            return "admin_question_input";
        }
        return "redirect:/admin_question_result";
    }
}

