package com.cooooode.controller;

import com.cooooode.pojo.User;
import com.cooooode.service.UserSerice;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: vua(杨晓迪)
 * @Date: Created on 2019-08-31
 * @Description:
 **/
@Controller
@RequestMapping("/")
@Slf4j
public class LoginController {
    @Autowired
    private UserSerice userSerice;
    @RequestMapping("")
    public String index(){
        return "index";
    }
    //private  final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @ResponseBody
    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam(value = "username") String username,
                        @RequestParam(value = "pw") String pw, HttpServletResponse response){
        String mess="";
        log.info("username:"+username+" password:"+pw);

        User user=userSerice.loginVerification(username,pw);

        session.setAttribute("user",user);
        /*
        Cookie cookie=new Cookie("userID",String.valueOf(user.getId()));
        cookie.setMaxAge(360);
        response.addCookie(cookie);
        */
        if(user==null)
            mess="ERROR Incorrect username or password. please check again.";
        else
            mess="Log in successfully!";
        return mess;
    }
    @ResponseBody
    @RequestMapping("/register")
    public String redister(HttpSession session, @RequestParam(value = "username") String username,
                           @RequestParam(value = "pw") String pw){

        String mess=userSerice.beforeRegister(username,pw);
        if(!mess.equals("pass")) return mess;
        User user=userSerice.registerNewUser(username,pw);
        session.setAttribute("user",user);
        return "redister successfully!";
    }
}
