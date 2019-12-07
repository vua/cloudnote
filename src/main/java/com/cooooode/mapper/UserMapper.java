package com.cooooode.mapper;

import com.cooooode.pojo.Blog;
import com.cooooode.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    void insertUser(User user);
    void deleteUser(User user);
    void deleteUserById(int id);
    void updateUser(User user);

    User getUserById(int id);
    Integer getUserIdByName(String name);
    User getUserByEmail(String email);
    User getUserByName(@Param("uname")String name);
    List<User> getAllUsers();
    List<User> getUsersByIdArange(int i,int j);
    List<User> getUsersByStratAndNumber(int start,int number);
    List<Blog> getBlogsById(int id);

}
