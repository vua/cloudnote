<html>
<head>
    <%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <title>CloudNote Editor Online</title>
    <link href="https://cdn.bootcss.com/quill/1.3.6/quill.snow.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/highlight.js/8.0/styles/monokai_sublime.min.css" rel="stylesheet">

    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="./css/page_common.css">
    <script src="./js/page_common.js"></script>
    <style>

        html, body {
            /*overflow: hidden;*/
            height: 100%;
            margin: 0;
        }

        @font-face {
            font-family: 'iconfont';  /* project id 1301184 */
            src: url('//at.alicdn.com/t/font_1301184_3jdj81vij26.eot');
            src: url('//at.alicdn.com/t/font_1301184_3jdj81vij26.eot?#iefix') format('embedded-opentype'),
            url('//at.alicdn.com/t/font_1301184_3jdj81vij26.woff2') format('woff2'),
            url('//at.alicdn.com/t/font_1301184_3jdj81vij26.woff') format('woff'),
            url('//at.alicdn.com/t/font_1301184_3jdj81vij26.ttf') format('truetype'),
            url('//at.alicdn.com/t/font_1301184_3jdj81vij26.svg#iconfont') format('svg');
        }

        .iconfont {
            font-family: "iconfont" !important;
            font-size: 25px;
            font-style: normal;
            color: #00FF7F;
            -webkit-font-smoothing: antialiased;
            -webkit-text-stroke-width: 0.2px;
            -moz-osx-font-smoothing: grayscale;
        }

        .editor .note-list {
            float: left;
            width: 13.5%;

        }

        .editor .edit-area {
            float: left;
            width: 86%;
        }

        .edit-area {
            margin: 5px 0;
        }

        .note-list {
            width: 100%;

        }

        .note-cont {
            height: 405px;
        }

        .note-cont div {
            display: block;
            width: 92%;
            margin: 2px 2%;
            height: 80px;
            border: 1px solid #000;
            padding: 3px 2%;
            overflow: hidden;
        }

        #add {
            margin-left: 5px;
        }

        .head {
            height: 30px;
            align-content: center;
            padding: 2px 3px 10px 3px;
            overflow: hidden;
            background: #fafafa;
            /*box-shadow: inset 0 -1px #eee;*/
        }

        .blog-title {
            margin-bottom: 5px;
            font-size: 30px;
            line-height: 30px;
        }

        #editor {
            margin-bottom: 30px;
        }

        #title {
            width: 93%;
            height: 30px;
            float: right;
            border: #bbb solid 2px;
            border-radius: 5px;
            outline: none;
            padding: 0 10px;

        }

        .message {
            display: none;
            position: fixed;
            width: 400px;
            height: 80px;
            background: #23241f;
            color: white;
            left: 50%;
            margin-left: -200px;
            top: 50%;
            margin-top: -40px;
            box-shadow: #ccc 4px 4px 4px;
            border: white 5px solid;
            text-align: center;
        }

        .message p {
            margin: 0;
            height: 80px;
            line-height: 80px;
        }

        .draw_save, .update_ {
            position: fixed;
            top: 100%;
            margin-top: -34px;
            display: none;
            width: 100%;
            background: rgba(255, 255, 255, 0.3);
            height: 30px;
            padding: 2px 0 2px 45%;
            border-top: #ccc 1px solid;

        }

        .page {
            position: fixed;
            top: 100%;
            margin-top: -30px;
            height: 30px;
            z-index: 999;
        }

        button {
            outline: none;
            border: none;
            background: #23241f;
            color: white;
            text-align: center;
            height: 30px;
            width: 80px;
            margin-right: 20px;
        }

        a {
            text-decoration-line: none;
            color: #23241f;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .delete, .edit {
            float: right;
            cursor: default;

        }

        .delete {
            color: #cccccc;
        }

        #add:hover {
            cursor: default;
            color: deeppink;
        }

        #sub {
            color: deeppink;
            display: none;
        }

        #sub:hover {
            cursor: default;
            color: #00FF7F;
        }

        .bar {

            height: 100%;
            float: left;
            background: #fafafa;
            position: relative;
            width: 0.5%;

        }

        .bar:hover {
            background: rgba(33, 33, 33, 0.5);
            color: white;
            cursor: w-resize;
        }

        .bar div {
            position: absolute;
            top: 50%;
            margin-top: -18px;
        }

        .bar p {
            margin: 0;
            padding: 0px;
            width: 10px;
            height: 6px;
            text-align: center;

        }

        .page {
            overflow: hidden;
        }
    </style>
</head>
<body>
<div style="box-shadow: rgba(0, 0, 0, 0.1) 0px 1px 20px, rgb(238, 238, 238) 0px 1px 0px, rgba(0, 0, 0, 0.08) 0px 1px 5px;height: 5px"></div>
<div class="editor">

    <div class="note-list">

        <div class="head"><b style="font-size: 20px">&nbsp&nbsp&nbspNOTE LIST&nbsp&nbsp&nbsp</b>
            <i class="iconfont" id="add">&#xe600;</i>
            <i class="iconfont" id="sub">&#xe713;</i>
        </div>

        <div class="note-cont">
            <c:forEach items="${sessionScope.blogs}" var="blog">
                <div>
                    <a href="cloudnote/${user.name}/article/${blog.id}">${blog.name} </a><br/>
                    <span style="font-size: 12px;color: #ccc">${blog.time}</span><br/>
                    <i class="iconfont edit" id="${blog.id}" name="${blog.name}">&#xe60b;</i>
                    <i class="iconfont delete" id="${blog.id}">&#xe695;</i>
                </div>
            </c:forEach>

        </div>

    </div>
    <div class="bar">
        <div>
            <p><b>.</b></p>
            <p><b>.</b></p>
            <p><b>.</b></p>
            <p><b>.</b></p>
            <p><b>.</b></p>
            <p><b>.</b></p>
        </div>
    </div>
    <div class="edit-area">
        <div class="blog-title"><span style="width:5%;font-family:'Microsoft YaHei UI';font-size: 20px">&nbsp&nbsp&nbspTitle :</span> <input type="text" id="title"></div>
        <div id="editor">

        </div>

    </div>
</div>
<div class="page"></div>
<div class="draw_save">

    <button class="draw">Draw</button>
    <button class="save">Save</button>
</div>
<div class="update_">
    <button class="draw">Draw</button>
    <button class="update">Update</button>
</div>
<div class="message">
    <p></p>
</div>


</body>
<script src="https://cdn.bootcss.com/quill/1.3.6/quill.js"></script>
<script src="https://cdn.bootcss.com/highlight.js/9.15.8/highlight.min.js"></script>


<%--<script src="https://cdn.bootcss.com/highlight.js/9.15.10/highlight.min.js"></script>--%>
<script>
    var uname = "${user.name}";
    var uid = "${user.id}";
    var start = "${start}";
    var total = "${total}";
    //var uid=document.cookie.split("=")[1];

</script>
<script src="./js/cloudnote.js"></script>
</html>
