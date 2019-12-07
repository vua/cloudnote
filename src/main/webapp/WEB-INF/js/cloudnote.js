$(document).ready(function() {
    hljs.configure({
        languages: ['javascript', 'java', 'python', 'cpp', 'c']
    });
    var page=createPage('.page');
    var page_cb=function(e){
        console.log(e);
    }
    setPage(page,{
        pageTotal: Number(total), // 数据总条数
        pageSize: 10,    // 每页显示条数
        pageCurrent: Number(start),  //  当前页
        maxBtnNum: 5, // 最多按钮个数  （最少5个）
        change:function(e){   // 页数变化回调函数（返回当前页码）
            //$.get("/?start="+e)
            location.href="?start="+e;
        },
    })
    var save_btn = document.getElementsByClassName("save")[0];
    var add=document.getElementById("add");
    var sub=document.getElementById("sub");
    var addnum=0;
    var title_ipt=document.getElementById("title");
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    var draw_save=document.getElementsByClassName("draw_save")[0];
    var edits=document.getElementsByClassName("edit");
    var update_=document.getElementsByClassName("update_")[0];
    function message(txt) {
        $(".message").css("display", "block");
        $(".message>p").text(txt);
        setTimeout(function () {
            $(".message").css("display", "none");
            $(".message>p").text("");
        }, 1000);
    }
    add.onclick=function (ev) {
        if(addnum==1){
            message("请完成当前页编辑(获或删除当前页)再添加新页")
            return;
        }
        update_.style.display="none"
        addnum=addnum+1;
        for(var j=0;j<edits.length;j++){
            edits[j].parentNode.style.background="#fff";
        }
        sub.style.display="inline-block";
        draw_save.style.display="block";
        var date=new Date();
        $(".note-cont").prepend('<div>\n' +
            '<a href="#">TEMP FILE</a><br/>' +
             //'<span style="font-size: 12px;color: #ccc"></span><br/>'+
             //'<i class="iconfont edit" id="" name=""></i>' +
            '</div>')
        /*<div>
        <a href="/${user.name}/article/${blog.id}">${blog.name} </a><br/>
            <span style="font-size: 12px;color: #ccc">${blog.time}</span><br/>
            <i class="iconfont edit" id="${blog.id}" name="${blog.name}">&#xe60b;</i>
        </div>*/
        title_ipt.value="CLOUD NOTE";
        var editor=document.getElementsByClassName("ql-editor")[0];
        editor.innerHTML="<pre class=\"ql-syntax hljs markdown\" spellcheck=\"false\"><span class=\"hljs-strong\">_____</span><span class=\"hljs-strong\">____ .__</span>                   .<span class=\"hljs-strong\">___             __</span>\n" +
            "\\<span class=\"hljs-emphasis\">_   _</span><span class=\"hljs-strong\">__ \\|  |   __</span><span class=\"hljs-strong\">__  __</span> <span class=\"hljs-strong\">__  __</span>| <span class=\"hljs-emphasis\">_/_</span><span class=\"hljs-strong\">___   __</span><span class=\"hljs-strong\">___/  |_  __</span>__\n" +
            "/    \\  \\/|  |  /  <span class=\"hljs-emphasis\">_ \\|  |  \\/ _</span><span class=\"hljs-emphasis\">_ |/    \\ /  _</span> \\   <span class=\"hljs-strong\">__\\/ __</span> \\\n" +
            "\\     \\<span class=\"hljs-strong\">___|  |_(  &lt;_&gt; )  |  / /_/ |   |  (  &lt;_&gt; )  | \\  __</span>_/\n" +
            " \\<span class=\"hljs-strong\">_____</span><span class=\"hljs-emphasis\">_  /_</span><span class=\"hljs-strong\">___/\\__</span><span class=\"hljs-strong\">__/|__</span><span class=\"hljs-strong\">__/\\__</span><span class=\"hljs-strong\">__ |__</span><span class=\"hljs-emphasis\">_|  /\\_</span><span class=\"hljs-strong\">___/|__</span>|  \\<span class=\"hljs-emphasis\">___</span>  &gt;\n" +
            "<span class=\"hljs-code\">        \\/                       \\/    \\/                 \\/</span>\n" +
            "\n" +
            "<span class=\"hljs-code\">                                                    2019@vua</span>\n" +
            "</pre>"

    }

    sub.onclick=function (ev) {
        addnum=addnum-1;
        $(".note-cont").children().first().remove();
        if(addnum<=0) {
            this.style.display = "none";
            draw_save.style.display="none";
            edits[0].onclick();
        }

    }
    save_btn.onclick =function () {

        var editor=document.getElementsByClassName("ql-editor")[0];
        var date=new Date();
        $.post("",
            {
                name:title_ipt.value,
                html:editor.innerHTML,
                time:date.getTime().toString()
            },
            function(result) {
                if(result=="SAVE SUCCESS") {
                    message("SAVE SUCCESS")
                    location.reload();
                }
                else
                    message("SAVE FAIL");
            })
    };

    var edit_id=0;
    var update=document.getElementsByClassName("update")[0];
    update.onclick=function () {
        var editor=document.getElementsByClassName("ql-editor")[0];
        var date=new Date();
        $.post("cloudnote/"+uid+"/update/"+edit_id,
            {
                name:title_ipt.value,
                html:editor.innerHTML,
                time:date.getTime().toString()
            },
            function (result) {

                if(result=="UPDATE SUCCESS") {
                    message("UPDATE SUCCESS");
                    location.reload();
                }
                else
                    message("UPDATE FAIL");
            }
        )
    }
    /*document.onkeyup = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 32 ) {
            document.querySelectorAll('pre').forEach((block) => {
                hljs.highlightBlock(block);
            var range = document.createRange();
            range.selectNodeContents(block);
            range.collapse(false);
            var sel = window.getSelection();
            sel.removeAllRanges();
            sel.addRange(range);
        }
        )}
    }*/
    var drag=function(obj){

        obj.bind("mousedown",start);

        function start(event){

            if(event.button==0){//判断是否点击鼠标左键


                //gapY=event.clientY-obj.offset().top;
                //movemove事件必须绑定到$(document)上，鼠标移动是在整个屏幕上的
                $(document).bind("mousemove",move);
                //此处的$(document)可以改为obj
                $(document).bind("mouseup",stop);

            }
            return false;//阻止默认事件或冒泡
        }
        function move(event){
            var left=obj.offset().left;
            gapX = event.clientX - left;
            if((left>200&&left<500)||(left<=200&&gapX>0)||(left>=500&&gapX<0)) {

                $(".note-list").css("width", function (index, value) {
                    return Number(value.slice(0, -2)) + Number(gapX) + "px";
                });
                $(".edit-area").css("width", function (index, value) {
                    return Number(value.slice(0, -2)) - Number(gapX) + "px";
                });
            }

            return false;//阻止默认事件或冒泡
        }
        function stop(){
            //解绑定，这一步很必要，前面有解释
            $(document).unbind("mousemove",move);
            $(document).unbind("mouseup",stop);

        }
    }
    drag($(".bar"))
    var deletes= document.getElementsByClassName("delete");

    for(var i=0;i<edits.length;i++){
        deletes[i].onclick=function(){
            if(confirm('是否确认要删除该页笔记?')){
                if(addnum>0){
                    message("请完成当前页编辑(获或删除当前页)再编辑此页");
                    return;
                }
                var delete_id=this.getAttribute("id");
                $.get("cloudnote/"+uid+"/delete/"+delete_id,
                    function (result) {
                    if(result=="DELETE SUCCESS") {
                        message("DELETE SUCCESS");
                        location.reload();
                    }else{
                        message("DELETE FAIL");
                    }
                })
            }
        };
        edits[i].onclick=function () {
            if(addnum>0){
                message("请完成当前页编辑(获或删除当前页)再编辑此页");
                return;
            }
            edit_id=this.getAttribute("id");
            for(var j=0;j<edits.length;j++){
                edits[j].parentNode.style.background="#fff";
            }
            var editor=document.getElementsByClassName("ql-editor")[0];
            var name=this.getAttribute("name");
            title_ipt.value=name;
            this.parentNode.style.background="#00FF7F";
            $.get("cloudnote/"+uid+"/edit/"+edit_id,
                function (result) {
                    editor.innerHTML=result;
            })
            update_.style.display="block"
        };
    }

    document.getElementsByClassName('draw')[0].onclick = function () {
        document.querySelectorAll('pre').forEach((block) => {
            hljs.highlightBlock(block);
    })

    };
    document.getElementsByClassName('draw')[1].onclick = function () {
        document.querySelectorAll('pre').forEach((block) => {
            hljs.highlightBlock(block);
    })

    };
    var quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: [
                ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
                ['blockquote', 'code-block'],

                [{'header': 1}, {'header': 2}],               // custom button values
                [{'list': 'ordered'}, {'list': 'bullet'}],
                [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
                [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
                [{'direction': 'rtl'}],                         // text direction

                [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
                [{'header': [1, 2, 3, 4, 5, 6, false]}],

                [{'color': []}, {'background': []}],          // dropdown with defaults from theme
                [{'font': []}],
                [{'align': []}],

                ['clean']
            ]
        }
    });
    edits[0].onclick();

});