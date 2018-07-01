/**
 * Created by 35930 on 2017/5/31.
 */
function regist(username, password, email){
    var info = {username: username, password: password, email: email};
    $.ajax({
        url:"/add_user",
        data:info,
        method:"post",

        success:function(result){

            if(result=="success"){
                // alert("okkkk");
                // $("#tochange").style.height="30%";
                $("#tohide").hide();
                // $("#tochange").style.height="30%";
                $("#info").show();
                // alert("注册成功，正在转至登录界面");
                $("body").animate({opacity:1},3000,function(){
                    location.href = "/login";

                })
            }else{
                alert("用户名已存在");
            }
        }
    })
}
function check_detail(username, password, repeat, email){

    if(username.value==""||username.value==null){
        $("#username-tip").text("用户名不能为空");
    }
    var patt=/^[\w]{6,16}$/
    if(patt.test(username.value)){
        //alert("通过测试");
        $("#username-tip").text("");
    }else{
        $("#username-tip").text("用户名必须由6-16位字母或数字组成");
        username.focus();
        return false
    }
    if(patt.test(password.value)){
        //alert("密码通过测试");
        $("#password-tip").text("");
    }else{
        $("#password-tip").text("密码必须由6-16位字母或数字组成");
        password.focus();
        return false;
    }
    if(repeat.value==password.value){
        //alert("密码重复测试");
        $("#repeat-tip").text("");
    }else{
        $("#repeat-tip").text("两次输入密码不一致");
        repeat.focus();
        return false;
    }
    var epatt=/^[\w]+@([\w]+\.)+[\w]+$/;
    if(epatt.test(email.value)){
        //alert("电邮通过测试");
        $("#email-tip").text("")
    }else{
        $("#email-tip").text("电邮格式非法");
        email.focus();
        return false;
    }
    var pw = $.md5(password.value);
    regist(username.value,pw,email.value);
    return true;
}
function check(thisform){
    with(thisform){
        check_detail(username, password, repeat, email);
    }
}
function login(thisform){
    with(thisform){
        var us=username.value;
        var pw=$.md5(password.value);

        _login(us,pw,true, 3);
    }
}
function _login(username, pw, iskeep, keeplength){
    $.ajax({
        url:"/user_login",
        data:{username:username, password:pw, iskeep:iskeep, keeplength:keeplength},
        method:"post",
        success:function(result){
            if(result=="fail"){
                alert("密码或用户名错误");
            }else{
                //alert(result);
                var k=$.parseJSON(result);
                $("#notlogged").hide();
                $("#username").text(k.userName);
                $("#logged").show();
                $("body").animate({opacity:1},1500,function(){
                    location.href = "/index";
                })
            }
        }
    })
}
function checkIsLogged(){
    $.ajax({
        url: "/cookiecheck",
        success: function (result) {
            if(result==="_fail"){
                $("#notlogged").show();
                $("#logged").hide();
            }
            else{
                //alert(result);
                var k=$.parseJSON(result);
                $("#notlogged").hide();
                $("#username").text(k.userName);
                $("#logged").show();
                $("body").animate({opacity:1},3000,function(){
                    location.href = "/index";
                })
            }
        }
    });
}