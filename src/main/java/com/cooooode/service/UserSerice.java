package com.cooooode.service;

import com.cooooode.mapper.UserMapper;
import com.cooooode.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserSerice {
    private final String regEx = "[`@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，？]|\n|\r|\t";
    @Autowired
    private UserMapper userMapper;
    public User getUserById(int id){
        return userMapper.getUserById(id);
    }
    public User getUserByName(String name){
        return userMapper.getUserByName(name);
    }
    /*
    * 用户登录验证
    * */
    public User loginVerification(String name,String password){

        User user=null;
        try {
            user = getUserByName(name);

        }catch (Exception e){
            e.printStackTrace();
        }

        if(user==null||!user.getPassword().equals(password)) return null;
        log.info(user.toString());
        return user;
    }
    public String beforeRegister(String name,String password){
        if(name.length()>20)
            return "The length of username should not exceed 20.";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        if(m.find())
            return "Username can't contain special character.(except _ 、! .)";
        if(password.length()<6||password.length()>18)
            return "The length of the password is 6 to 18.";
        m=p.matcher(password);
        if(m.find())
            return "Password can't contain special character.(except _ 、! .)";
        return "pass";
    }
    public User registerNewUser(String name,String password){
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        userMapper.insertUser(user);
        return user;
    }
}
