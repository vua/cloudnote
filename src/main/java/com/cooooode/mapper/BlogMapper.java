package com.cooooode.mapper;

import com.cooooode.pojo.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    void insertBlog(Blog blog);

    void deleteBlog(@Param("user_id")int uid, @Param("id")int bid);

    void deleteBlogById(int id);

    void updateBlog(@Param("name")String name,
                    @Param("html")String html,
                    @Param("time")String time,
                    @Param("user_id")int uid,
                    @Param("id")int bid);

    Blog getBlogById(@Param("user_id")int uid, @Param("id")int bid);
    Blog getBlogByUnameAndBid(@Param("user_name")String uname,@Param("id")int bid);
    Blog getBlogsByUserId(int id);
    List<Blog> getBlogByFK(int user_id);
    List<Blog> getAllBlogs();
    List<Blog> getPartialBlogs(@Param("user_id")int uid, @Param("start")int start);
    int getBlogsNumberByUserId(@Param("user_id")int uid);
}
