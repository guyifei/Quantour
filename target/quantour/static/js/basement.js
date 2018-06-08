/**
 *
 * Created by 35930 on 2017/5/11.
 */
//jQuery实现的动画部分
var inshow=0;
var dataStr;

$(document).ready(function(){


    $('#navbar').mouseover(function(){
        if(document.body.scrollTop==0){
            document.getElementById('navbar').style.backgroundColor = 'rgba(77, 74, 77, 0.7)';
        }
    });
    $('#navbar').mouseout(function(){
        if(document.body.scrollTop==0){
            document.getElementById('navbar').style.backgroundColor = 'rgba(77, 74, 77, 0)';
        }
    });
    i(0);

});


function ac(input){
    if(input.value==""||input.value==" ")
        return;
    $.ajax({
        url:"/function/getList",
        data:{key:input.value},
        dataType:'json',
        success(result){
            dataStr = result;

            $(input).autocomplete({
                source:dataStr,
                submit:function(){
                    alert("submit");
                }
            });
        }
    });
}

var ontop=1;//1=在顶部 0=不在顶部 2=动画中
function i(flag){
    if(inshow==1)
        return;
    else
        inshow=1;
    var x=$('#titles');
    var a=$('#title1');
    var b=$('#title2');
    var c=$('#title3');
    inshow=1;
    if(flag==0){
        x.animate({opacity:'0'},0);
        x.animate({opacity:'1'},700);
        a.animate({left:'-=150px',opacity:'0'},0);
        a.animate({left:'+=150px',opacity:'1'},1000);
        b.animate({left:'+=150px',opacity:'0'},0);
        b.animate({left:'-=150px',opacity:'1'},1000);
        c.animate({left:'-=150px',opacity:'0'},0);
        c.animate({left:'+=150px',opacity:'1'},1000,function(){inshow=0;});
    }else{
        x.animate({opacity:'1'},0);
        x.animate({opacity:'0'},700);
        a.animate({left:'-=150px',opacity:'0'},400);
        a.animate({left:'+=150px',opacity:'0'},0);
        b.animate({left:'+=150px',opacity:'0'},400);
        b.animate({left:'-=150px',opacity:'0'},0);
        c.animate({left:'-=150px',opacity:'0'},400);
        c.animate({left:'+=150px',opacity:'0'},0,function(){inshow=0;});
    }
}
function checkTop(){
    //alert("teset");

    if(document.body.scrollTop>0&&document.body.scrollTop<document.documentElement.clientHeight&&ontop==1){
        ontop=2;
        i(1);
        $('#body').animate({scrollTop: $('#con').offset().top-50},function(){
            ontop=0;
        });
        document.getElementById('navbar').style.backgroundColor='rgba(77, 74, 77, 0.7)';
    }else if(document.body.scrollTop<=document.documentElement.clientHeight-200&&ontop==0){
        ontop=2;
        i(0);
        $('#body').animate({scrollTop: $('#body').offset().top},function(){
            ontop=1;
        });
        document.getElementById('navbar').style.backgroundColor='rgba(77, 74, 77, 0)';
    }
}
function logout(){
    setCookie("logincode", "", -1);
    location.href = "/index";
}
//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
function pageLogCheck(){
    var k=document.getElementsByClassName("masking")[0];
    $(k).css("min-height", document.body.clientHeight-170);
    $.ajax({
        url:"/cookiecheck",
        method:"post",
        contentType:"text/html;charset=UTF-8",
        success:function(result){
            if(result=="_fail"){
                $("#logged").hide();
                $("#not-logged").show();
                $("#not-logged").css("opacity","1");
            }else{
                var k=$.parseJSON(result);
                $("#logged").show();
                $("#logged").css("opacity","1");
                if(k.graph!=null){
                    var img=document.getElementById("avatar");
                    img.setAttribute("src",k.graph);
                    img.style="width:25px;height:25px;border-radius:100px;"
                }

                $("#username").text(k.userName);
                $("#not-logged").hide();
            }
        }
    })
}