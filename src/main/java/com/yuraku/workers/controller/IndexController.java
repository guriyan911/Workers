package com.yuraku.workers.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import lombok.val;

import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuraku.workers.domain.MEmpRepository;
import com.yuraku.workers.domain.MyDataRepository;

@Controller
public class IndexController extends BaseController {

    @Autowired
    MyDataRepository myDataRep;
    @Autowired
    MEmpRepository mEmpRep;
    @Autowired
    MessageSource messageSource;
    
    @RequestMapping("/index")
    public String index(Model model,Locale locale) throws IOException{
    	// localeをもらえばmessages_xx.propertiesから値は取れる
    	// 1つだけpropertiesを置くならmessages_ja_JP.propertiesを置くとか。そしたら下ので取得する。
    	// Thymeleafが#{msg}で読み出すのはこの設定と全く関係がない。
    	String welcome = messageSource.getMessage("m.welcome", null, locale);
        model.addAttribute("msg", welcome);        
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(Model model){
        val list = myDataRep.findAll();
        model.addAttribute("datas",list);
        return "hello";
    }
}

