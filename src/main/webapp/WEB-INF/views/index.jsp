<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: 53121
  Date: 2018/9/19
  Time: 9:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private String getTime(){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
%>
<html>
<head>
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%--<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.slim.min.js"></script>--%>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <style>
        body{
            margin: 0;
            font-family: "Microsoft YaHei UI";
        }
        .bg{
            margin: 0;
            position: fixed;
            width: 100%;
            height: 100%;
            background-image:url("images/init_background.jpg");
            background-size: cover;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            z-index: -1;
        }
        .LRFormContainer{
            padding-top:50px ;
            padding-bottom:50px;

        }
        .LRForm{
            margin: auto;
            width:400px;
            height:600px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background: rgba(255,255,255,0.5);
            border-radius: 4px;
        }
        .logo{
            font-size: 50px;
            font-family: "Matura MT Script Capitals";
            margin-bottom: 100px;
        }
        .LRForm .form{
            width: 320px;
        }
        .LRForm .username,.password{
            width: 320px;
            height: 40px;
            border: azure;
            background: white;
            border-radius: 3px;
            margin: 1px;
            display: none;

        }
        .mess{

            height: 55px;
            background: rgba(0,0,0,0.5);
            color:snow;
            display: none;
            padding: 15px;
            font-size: 14px;
        }
        .LRForm div{

        }
        .LRForm div input{
            height: 100%;
            width: 280px;
            margin-left:20px;
            outline: none;
            border: none;
            font-family: "Microsoft YaHei UI";
            font-size: 16px;

        }
        .LRForm button{
            width: 320px;
            height: 40px;
            background: cornflowerblue;
            border: none;
            color: palegoldenrod;
            font-size: medium;
            margin: 1px;
        }
    </style>
</head>
<body>
<% System.out.println("Tomcat"); %>
<div class="mainContainer">
    <div class="bg"></div>
    <pre style="border: none;color: white;position: absolute;">
	_________ .__                   .___             __
	\_   ___ \|  |   ____  __ __  __| _/____   _____/  |_  ____
	/    \  \/|  |  /  _ \|  |  \/ __ |/    \ /  _ \   __\/ __ \
	\     \___|  |_(  <_> )  |  / /_/ |   |  (  <_> )  | \  ___/
	 \______  /____/\____/|____/\____ |___|  /\____/|__|  \___  >
	        \/                       \/    \/                 \/
	</pre>
    <div class="LRFormContainer">

        <div class="LRForm">
            <%--<div style="margin-bottom:10px; "><%=getTime()%></div>--%>
            <div class="logo"><span>C<span style="color: crimson">oooo</span>ode</span></div>

            <div class="form">
                <div class="username" style="display:none"><input type="text" placeholder="username" id="username"></div>
                <div class="password" style="display:none"><input type="password" placeholder="6-18位密码" id="password"></div>
                <div class="password" style="display:none"><input type="password" placeholder="重复密码" id="re-password"></div>
                <div class="mess"></div>
                <button class="login"><b>登录</b></button>
                <button class="register"><b>注册</b></button>
            </div>
        </div>
    </div>
</div>
<script>
    window.onload=function() {
        /* var Ajax=function(json){
             var method=json.method;
             var url=json.url;
             var data=json.data;
             var success=json.success;
             var fail=json.fail;
             var request = new XMLHttpRequest();
             request.onreadystatechange = function () {
                 if (request.readyState === 4) {
                     if (request.status === 200) {
                         return success(request.responseText);
                     } else {
                         return fail(request.responseText);
                     }
                 } else {
                 }
             };
             console.log(json)
             request.open(method, url);
             request.send(data);
         };*/
        var oUsername = document.getElementsByClassName("username")[0];
        var oUsernameInput = document.getElementById("username");
        var oPw = document.getElementsByClassName("password")[0];
        var oRPw = document.getElementsByClassName("password")[1];
        var oPwInput = document.getElementById("password");
        var oRPwInput = document.getElementById("re-password");
        var oLoginBtn = document.getElementsByClassName("login")[0];
        var oRegisterBtn = document.getElementsByClassName("register")[0];
        var oMess = document.getElementsByClassName("mess")[0];
        oLoginBtn.onclick = function () {

            if (oUsernameInput.value && oPwInput.value && oRPw.style.display == "none") {
                /*                json={
                                    "method":'POST',
                                    "url":'/login',
                                    "data":{
                                        "email":oEmailInput.value,
                                        "pw":oPwInput.value
                                    },
                                    "success":function (mess) {

                                    },
                                    "fail":function (mess) {
                                        oMess.innerHTML=mess;
                                    }
                                };
                                Ajax(json);*/
                $.post("login",
                    {
                        "username": oUsernameInput.value,
                        "pw": oPwInput.value
                    },
                    function (data) {
                        oMess.style.display = "block";
                        oMess.innerHTML = data;
                        if (data == "Log in successfully!") {
                            location.href = "cloudnote"
                        }
                    }
                )
            }
            else {
                if (oUsername.style.display != "block") {
                    oUsernameInput.value = "";
                    oPwInput.value = "";
                    oUsername.style.display = "block";
                    oPw.style.display = "block"
                } else {
                    if (oRPw.style.display == "block") {
                        oUsernameInput.value = "";
                        oPwInput.value = "";
                        oMess.style.display = "none";
                        oRPw.style.display = "none";
                    } else {
                        oMess.style.display = "block";
                        oMess.innerHTML = "请完善信息"
                    }
                }
            }
        };
            oRegisterBtn.onclick = function () {
                if (oUsernameInput.value && oPwInput.value && oRPwInput.value) {
                    if (oRPwInput.value != oPwInput.value) {
                        oMess.style.display = "block";
                        oMess.innerHTML = "两次输入密码不一致";
                        return
                    }
                    oMess.style.display = "none";
                    $.post("register",
                        {
                            "username": oUsernameInput.value,
                            "pw": oPwInput.value
                        },
                        function (data) {
                            oMess.style.display = "block";
                            oMess.innerHTML = data;
                        }
                    )
                } else {
                    if (oRPw.style.display != "block") {
                        oUsernameInput.value = "";
                        oPwInput.value = "";
                        oRPwInput.value = "";
                        oUsername.style.display = "block";
                        oPw.style.display = "block";
                        oRPw.style.display = "block";
                        oMess.style.display = "none";
                    } else {
                        oMess.style.display = "block";
                        oMess.innerHTML = "请完善信息"
                    }
                }
            }
    }
</script>
</body>
</html>
