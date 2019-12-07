<%--
  Created by IntelliJ IDEA.
  User: 53121
  Date: 2019/7/25
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${blog.name}</title>
    <link href="https://cdn.bootcss.com/quill/1.3.6/quill.snow.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/highlight.js/8.0/styles/monokai_sublime.min.css" rel="stylesheet">
    <style>
        .container{
            width: 80%;
            margin: auto;
            /*overflow: hidden;*/
        }
        .head_mess{
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="head_mess">
        <h1>${blog.name}</h1>
        <p>${blog.user.name}&nbsp;&nbsp; &nbsp;  |&nbsp;&nbsp; &nbsp;  ${blog.time}</p>
    </div>
    <div id="editor" class="ql-container ql-snow">
        <div class="ql-editor" data-gramm="false" contenteditable="true">

            ${blog.html}
        </div>
    </div>
</div>
    <script src="https://cdn.bootcss.com/highlight.js/9.15.8/highlight.min.js"></script>

</body>
<script>
    document.addEventListener('DOMContentLoaded', (event) => {
        document.querySelectorAll('pre').forEach((block) => {
            hljs.highlightBlock(block);
        });
    });
</script>
</html>
