package com.cooooode.mapper;

import com.cooooode.pojo.Blog;
import com.cooooode.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MybatisTest {
    static ApplicationContext applicationContext;
    static UserMapper userMapper;
    static BlogMapper blogMapper;
    static {
        applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        userMapper= (UserMapper) applicationContext.getBean("userMapper");
        Assert.assertNotNull(userMapper);
        blogMapper= (BlogMapper)applicationContext.getBean("blogMapper");
    }
    @Test
    public void userMapperInsert(){
        User user=new User("黎小田","111111","12891739@qq.com");
        userMapper.insertUser(user);
    }
    @Test
    public void blogMapperInsert(){
        Blog blog=new Blog();
        blog.setName("BLOG TEST");
        blog.setTime("2019.07.25");
        blog.setHtml("HELLO WORD!");
        blog.setUser_id(1);
        blogMapper.insertBlog(blog);
    }
    @Test
    public void userMapperSelectTest(){
        User user=userMapper.getUserById(1);
        Blog blog=user.getBlogs().get(0);
        System.out.println(blog.getName());
    }
    @Test
    public void userMapperdeleteTest(){
        userMapper.deleteUserById(2);
    }
    @Test
    public void userMapperUpdate(){
        User user=userMapper.getUserById(1);
        user.setName("彰化中");
        userMapper.updateUser(user);
    }
    @Test
    public void userGetBlogsByIdTest(){
        List<Blog> blogs=userMapper.getBlogsById(1);
        System.out.println(blogs.get(0).getName());
    }
    @Test
    public void blogGetUserByIdTest(){
        Blog blog=blogMapper.getBlogById(1,1);
        System.out.println(blog.getUser().getName());
    }
    @Test
    public void updateBlogTest(){
        Blog blog=blogMapper.getBlogById(1,1);
        blog.setHtml("I LOVE U. emmm....");
        //blogMapper.updateBlog(blog,1,1);
    }
    @Test
    public void getUserIdByNameTest(){
        User user=userMapper.getUserById(1);
        System.out.println(user.getName());
        int id=userMapper.getUserIdByName("nike");
        System.out.println(id);
    }
}
