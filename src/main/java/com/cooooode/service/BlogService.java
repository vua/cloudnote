package com.cooooode.service;

import com.cooooode.controller.NoteController;
import com.cooooode.mapper.BlogMapper;
import com.cooooode.mapper.UserMapper;
import com.cooooode.pojo.Blog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BlogService {
    private static final int PAGE_SIZE=6;
    private static SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Resource(name="redisTemplate")
    private ValueOperations<String,String> valueOperations;
    @Resource(name="redisTemplate")
    private HashOperations<String,String,Blog> hashOperations;
    public boolean saveBlog(String name,String html,String time,int id){
        try {
            Blog blog = new Blog();
            blog.setId(Integer.parseInt(time.substring(5,13)));
            blog.setUser_id(id);
            blog.setName(name);
            blog.setHtml(html);
            blog.setTime(dateFormat.format(new Date(Long.parseLong(time))));
            blogMapper.insertBlog(blog);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }
    public Blog getBlogById(String uname,int bid){
        Blog blog=null;

        try {
            blog=blogMapper.getBlogByUnameAndBid(uname,bid);
            /*int uid=userMapper.getUserIdByName(uname);
            blog=blogMapper.getBlogById(uid,bid);*/
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        return blog;
    }
    public Blog getBlogById(int uid,int bid){
        /*
        * 加入redis做缓存
        * */
        String key=Blog.getRedisKeyName(uid,bid);

        if(hashOperations.hasKey("blog",key)){//){
            log.info("读取redis缓存数据");
            return hashOperations.get("blog",key);
        }else {
            Blog blog = null;
            try {
                log.info("读取mysql数据");
                blog = blogMapper.getBlogById(uid, bid);
                log.info("将数据存入redis");
                hashOperations.put("blog",key,blog);
            } catch (Exception e) {
                e.getStackTrace();
                System.out.println(e.getMessage());
            }
            return blog;
        }
    }
    public List<Blog> getBlogsByUserId(int id){
        return userMapper.getBlogsById(id);
    }
    public List<Blog> getPartialBlogsByUserIDAndStartNum(int id,int start){
        return blogMapper.getPartialBlogs(id,start);
    }
    public int getTotalNumber(int id){
        return blogMapper.getBlogsNumberByUserId(id);
    }
    public boolean updateBlog(String name,String html,String time,int bid,int uid){
        /*
        Blog blog = new Blog();
        blog.setName(name);
        blog.setHtml(html);
        blog.setTime(dateFormat.format(new Date(Long.parseLong(time))));
        */
        String key=Blog.getRedisKeyName(uid,bid);
        try {
            if(hashOperations.hasKey("blog",key)) {//){
                log.info("删除redis缓存数据");
                hashOperations.delete("blog",key);
            }
            blogMapper.updateBlog(name,html,dateFormat.format(new Date(Long.parseLong(time))),uid, bid);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }
    public boolean deleteBlog(int bid,int uid){
        try{
            blogMapper.deleteBlog(uid,bid);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }
}
