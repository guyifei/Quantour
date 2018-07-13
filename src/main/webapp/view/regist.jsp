<%--
  Created by IntelliJ IDEA.
  User: 35930
  Date: 2017/5/25
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script src="../static/js/jquery.md5.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <script type="text/javascript" src="../static/js/logcheck.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/logcss.css"/>
    <script type="text/javascript" language="JavaScript">

    </script>
</head>
<body>
<div class="header">
    <div class="headerimage" style="">
        <nav>
            <div class="navbar navbar-inverse navbar-fixed-top">
                <div>
                    <div class="navbar-hedaer">
                        <a class="navbar-brand" href="#">AnyQuantour</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li class=""><a href="/index">首页</a></li>
                        <li class=""><a href="/market">市场</a></li>
                        <li class=""><a href="/stocklist">股票</a></li>
                        <li class=""><a href="/compare">比较</a></li>
                        <li class="dropdown">
                            <a href="" class="dropdown-toggle" data-toggle="dropdown">策略
                                <%--<b class="caret"></b>--%>
                            </a>
                            <ul class="dropdown-menu" style="min-width: 120px">
                                <li><a href="/momentum">回测</a></li>
                                <li><a href="/strategy">自定义策略</a></li>
                            </ul>
                        </li>

                        <li class=""><a href="/bbs">论坛</a></li>
                    </ul>
                    <div class="header-right">
                        <form action="/stockFunc/search" class="searchwrap">
                            <input oninput="ac(this)" autocomplete="off" class="searchbox" type="text" name="key" placeholder="搜索股票" required>
                            <button align="center" class="searchbutton" type="submit" id="search_button"></button>
                        </form>
                    </div>
                </div>
            </div>
        </nav>
    </div>
</div>
<div class="content" style="height:80%;">
    <div class="container masking" style="height:100%; background-color: rgba(255,255,255,0);box-shadow: none">
        <div id="tochange" style="height: 100%;">
            <div class="row" style="height:5%;"></div>
            <div   class="row" style="height:90%; width: 100%; max-width: 400px; margin:auto auto;">
                <div id="tohide" class="col-md-12 loginwrap">
                    <h2 style="text-align: center">注册</h2>
                    <div class="seperator" style="background-color: #1d3c54"></div>
                    <form id="form" target="faker">
                        <iframe name="faker" style="display: none;"></iframe>
                        <table class="log-table" style="width: 100%;">
                            <tr>
                                <td><h4 align="right">用&nbsp&nbsp户&nbsp名</h4></td>
                                <td align="right"><input type="text" class="log-input" name="username" title="regist"></td>
                            </tr>
                            <tr>
                                <td  class="form-tip" colspan="2" id="username-tip"> </td>
                            </tr>
                            <tr>
                                <td><h4 align="right">密&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp码</h4></td>
                                <td align="right"><input type="password" class="log-input" name="password" title="regist"></td>
                            </tr>
                            <tr>
                                <td class="form-tip"  colspan="2" id="password-tip"> </td>
                            </tr>
                            <tr>
                                <td><h4 align="right">密码确认</h4></td>
                                <td align="right"><input type="password" class="log-input" title="regist" name="repeat"></td>
                            </tr>
                            <tr>
                                <td class="form-tip"  colspan="2" id="repeat-tip"> </td>
                            </tr>
                            <tr>
                                <td><h4 align="right">电子邮箱</h4></td>
                                <td align="right"><input type="email" class="log-input" name="email" title="regist"></td>
                            </tr>
                            <tr>
                                <td class="form-tip" colspan="2" id="email-tip"> </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center" valign="center">
                                    <button class="log-button" onclick="check(form)">确认</button>
                                </td>
                            </tr>
                            <tr align="right" >

                                <td colspan="2" style="padding: 50px 0;margin-bottom: 10px">已有账号?<a href="/login">登录</a></td>
                            </tr>
                        </table>

                    </form>
                    <%--<div id="info" style="display: none;height: 100%">--%>
                        <%--注册成功, 即将跳转至登录界面.--%>
                    <%--</div>--%>
                </div>
                <div class="col-md-12 loginwrap" id="info" style="height: 200px;display: none">
                    <h2 style="text-align: center">注册</h2>
                    <div class="seperator" style="background-color: #1d3c54"></div>
                    <div class="" style="margin-left: 70px">注册成功, 即将跳转到登录界面<div>
                    </div>
                    </div>
                    <div class="row" style="height:20%;"></div>
                </div>
            </div>
            <%--<div id="info" style="display: none;height: 6%">--%>
                <%--注册成功, 即将跳转至登录界面.--%>
            <%--</div>--%>
            <div class="row" style="height:20%;"></div>
        </div>
    </div>

</div>
<footer class="footer">
    <div class="container">
        <div class="row seperator"></div>
        <div class="row">
            <div class="clu">
                <p style="font-size: 16px;margin-top: 10px">由AnyEight小组制作 联系我们:943045598@qq.com 814335296@qq.com 1226550411@qq.com</p>
                <p style="font-size: 16px;">此网站为课程学习项目, 不允许用于商业用途.</p>
                <p style="font-size: 16px;">NJU Software Institute 2017.5</p>
            </div>
        </div>
    </div>
</footer>
</body>
</html>

