package com.yuraku.workers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {

    @Autowired
    MyDataRepository repository;

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","SpringBootへようこそ！");
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(Model model){
        Iterable<MyData> list = repository.findAll();
        model.addAttribute("datas",list);
        return "hello";
    }
}

