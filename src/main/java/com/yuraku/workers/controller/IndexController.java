package com.yuraku.workers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuraku.workers.dao.MEmpRepository;
import com.yuraku.workers.dao.MyData;
import com.yuraku.workers.dao.MyDataRepository;

@Controller
public class IndexController extends BaseController {

    @Autowired
    MyDataRepository myDataRep;
    @Autowired
    MEmpRepository mEmpRep;

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","SpringBootへようこそ！");
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(Model model){
        Iterable<MyData> list = myDataRep.findAll();
        model.addAttribute("datas",list);
        return "hello";
    }
}

