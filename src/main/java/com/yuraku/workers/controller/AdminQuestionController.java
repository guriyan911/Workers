package com.yuraku.workers.controller;

import javax.validation.Valid;

import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yuraku.workers.domain.TtQuizees;
import com.yuraku.workers.domain.TtQuizeesRepository;

@Controller
public class AdminQuestionController extends BaseController {
	
	@Autowired 
    @Qualifier("jdbcGenbu") 
    protected JdbcTemplate jdbc;
	
    @Autowired
    TtQuizeesRepository ttQuizeesRepository;
    
    @RequestMapping(value="/admin_question_input", method=RequestMethod.GET)
    public String input(TtQuizees ttQuizees){
    	val test= jdbc.queryForList("select * from m_my_rule");
        return "admin_question_input";
    }

    @RequestMapping(value="/admin_question_entry", method=RequestMethod.POST)
    public String questionEntry(@Valid TtQuizees ttQuizees, BindingResult bindingResult){
    	if (bindingResult.hasErrors()) {
            return "admin_question_input";
        }
    	
    	ttQuizeesRepository.save(ttQuizees);
    	
    	// もし一覧画面から登録画面、そして登録後に一覧画面に戻るとかならredirectを使う
        //return "redirect:/admin_question_result";
        return "admin_question_result";
    }
}

