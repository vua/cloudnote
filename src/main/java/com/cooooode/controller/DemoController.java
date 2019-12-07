package com.cooooode.controller;

import com.cooooode.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {
    @Autowired
    private CountService countService;
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        int i=countService.getCount();
        System.out.println(i);
        return ""+i;
    }
}
