package com.cooooode.controller;


import com.cooooode.mapper.BlogMapper;
import com.cooooode.pojo.Blog;
import com.cooooode.pojo.User;
import com.cooooode.service.BlogService;
import com.cooooode.service.SQLDumpService;
import com.cooooode.service.UserSerice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/cloudnote")
@Slf4j
public class NoteController {
    private static final int PAGE_SIZE=10;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserSerice userSerice;
    @Autowired
    private SQLDumpService sqlDumpService;
    @RequestMapping("")
    public String editor(HttpSession session,HttpServletRequest request){
        User user= (User) session.getAttribute("user");
        if(user==null) return "index";
        String str=request.getParameter("start");

        int start=0;
        if(str!=null)
            try {
                start = Math.abs(Integer.parseInt(str)-1);
            }catch (Exception e){
                start=0;
            }

        /*
        Cookie[] cookies=request.getCookies();
        String id=null;

        for (Cookie cookie:
             cookies) {
            if(cookie.getName().equals("userID"))
                id=cookie.getValue();
        }
        if(id==null) return "index";
        List<Blog> blogs=blogService.getBlogsByUserId(Integer.parseInt(id));
        */
        //List<Blog> blogs=blogService.getBlogsByUserId(user.getId());
        //分页获取
        long a=System.currentTimeMillis();
        List<Blog> blogs=blogService.getPartialBlogsByUserIDAndStartNum(user.getId(),start*PAGE_SIZE);
        log.info("Time:"+(System.currentTimeMillis()-a)+"ms");
        session.setAttribute("blogs",blogs);
        session.setAttribute("start",start+1);
        int total=blogService.getTotalNumber(user.getId());
        session.setAttribute("total",total);
        return "cloudnote";
    }
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void editor(HttpServletRequest request, HttpServletResponse response) throws IOException {


        PrintWriter out=response.getWriter();;
        String html = request.getParameter("html");
        String name = request.getParameter("name");
        String time = request.getParameter("time");
        int id=((User)request.getSession().getAttribute("user")).getId();
        /*Cookie[] cookies=request.getCookies();
        String id=null;

        for (Cookie cookie:
                cookies) {
            if(cookie.getName().equals("userID"))
                id=cookie.getValue();
        }*/
        if(blogService.saveBlog(name,html,time,id)) {

            out.write("SAVE SUCCESS");
        }
        else
            out.write("SAVE FAIL");
        out.close();
    }
    @RequestMapping(value = "{uname}/article/{bid}")
    public String article(@PathVariable String uname,@PathVariable String bid,HttpServletRequest request) throws UnsupportedEncodingException {
        //uname = new String(uname.getBytes("ISO-8859-1"), "utf8");

        int id=Integer.parseInt(bid);
        request.setAttribute("blog",blogService.getBlogById(uname,id));
        return "article";
    }
    @RequestMapping(value="{uid}/edit/{bid}")
    @ResponseBody
    public void edit(@PathVariable String uid,@PathVariable String bid,HttpServletResponse response) throws IOException {
        int u_id=Integer.parseInt(uid);
        int b_id=Integer.parseInt(bid);
        Blog blog=blogService.getBlogById(u_id,b_id);
        PrintWriter out=response.getWriter();
        out.write(blog.getHtml());
        out.close();
    }
    @RequestMapping(value="{uid}/update/{bid}",method = RequestMethod.POST)
    public void update(@PathVariable String uid,@PathVariable String bid,HttpServletRequest request,HttpServletResponse response) throws IOException {
        int u_id=Integer.parseInt(uid);
        int b_id=Integer.parseInt(bid);
        PrintWriter out=response.getWriter();;
        String html = request.getParameter("html");
        String name = request.getParameter("name");
        String time = request.getParameter("time");
        if(blogService.updateBlog(name,html,time,b_id,u_id))
            out.write("UPDATE SUCCESS");
        else
            out.write("UPDATE FAIL");
        out.close();
    }
    @RequestMapping(value="{uid}/delete/{bid}")
    public void delete(@PathVariable String uid,@PathVariable String bid,HttpServletRequest request,HttpServletResponse response) throws IOException {
        int u_id = Integer.parseInt(uid);
        int b_id = Integer.parseInt(bid);
        PrintWriter out=response.getWriter();
        if(blogService.deleteBlog(b_id,u_id)) {
            out.write("DELETE SUCCESS");
        }
        else
            out.write("DELETE FAIL");
        out.close();
    }
    @RequestMapping(value = "{uid}/download")
    public void download(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String base_path = request.getServletContext().getRealPath("");

        String filepath = sqlDumpService.dump(base_path);
        String filename = "cloudnote.sql";

        File file = new File(filepath);
        response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(filename,"UTF-8"));
        response.addHeader("Content-Type","application/json;charset=UTF-8");
        try(
                InputStream is = new FileInputStream(file);
                OutputStream os = response.getOutputStream();
        ){
            int read = 0;
            byte[] bytes = new byte[2048];
            while ((read = is.read(bytes)) != -1)
                os.write(bytes, 0, read);
        }

    }
}
