package com.yuraku.workers.controller;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.CharacterEncodingFilter;

@Controller
public abstract class BaseController {

	@Bean
    public Filter characterEncodingFilter() {
		// この記述はApp.javaでもいいのでは？
	      CharacterEncodingFilter filter = new CharacterEncodingFilter();
	      filter.setEncoding("UTF-8");
	      filter.setForceEncoding(true);
	      return filter;
    }
}

