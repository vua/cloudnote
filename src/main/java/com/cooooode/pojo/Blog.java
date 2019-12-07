package com.cooooode.pojo;


import lombok.Data;

import java.io.Serializable;

@Data
public class Blog implements Serializable {
    private Integer id;
    private String html;
    private String time;
    private String name;
    private Integer user_id;
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public static String  getRedisKeyName(int uid,int bid){
        return "user:"+uid+":blog:"+bid;
    }

}
