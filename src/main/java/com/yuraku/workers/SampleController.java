package com.yuraku.workers;

//import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
@Controller
public class SampleController {

    @Autowired
    MyDataRepository repository;

    @RequestMapping("/hello")
    public String hello(Model model){
        //model.addAttribute("val","コントローラーのテキストです。");
        Iterable<MyData> list = repository.findAll();
        model.addAttribute("datas",list);
        return "hello";
    }
}

