package com.example.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

    @RequestMapping(value="/hello.do", method=RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "안녕하세요 SpringBoot!";
    }
    
    @RequestMapping("/hi.do")
    public String hi(Model model){
        
        String name = "홍길동";
        
        model.addAttribute("name", name);

        return "hi";    // /templates/ + hi + .html

    }
    
    // /bye.do?name=홍길동
    // =>결과 : 홍길동님 안녕하세요
    @RequestMapping("/bye.do")
    public String bye(@RequestParam(name="name", defaultValue = "홍길동") String name,
                        Model model){

        model.addAttribute("name", name);

        return "bye";
    }
}