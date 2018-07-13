<!--作为模板使用-->
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/js/jquery.md5.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script src="../static/js/logcheck.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/logcss.css"/>
    <script type="text/javascript" language="JavaScript">
        window.onload=function(){
            checkIsLogged();
        }
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
                        <li class=""><a href="/neuralLearn">预测</a></li>
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
    <div class="container masking" style="height:100%; background-color: rgba(255,255,255,0);box-shadow:none;">
        <div style="height: 100%;">
            <div class="row" style="height:15%;"></div>
            <div class="row" style="height:65%; width: 100%;max-width: 400px; margin:auto auto;">
                <div class="col-md-12 loginwrap" id="notlogged">
                    <h2 style="text-align: center">登录</h2>
                    <div class="seperator" style="background-color: #1d3c54"></div>
                    <form target="thisform" onsubmit="login(this)">
                        <iframe name="thisform" style="display: none;"></iframe>
                        <table class="log-table" style="width: 100%;">
                            <tr>
                                <td><h4 align="right">用&nbsp&nbsp户&nbsp名</h4></td>
                                <td align="right"><input type="text" class="log-input" name="username" title="login"></td>
                            </tr>
                            <tr>
                                <td><h4 align="right">密&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp码</h4></td>
                                <td align="right"><input type="password" class="log-input" name="password" title="login"></td>
                            </tr>
                            <tr>
                                <td><h4 align="right">保持登录</h4></td>
                                <td align="right">
                                    <div class="" style="margin-right:20px;margin-top: 4px">
                                        <input type="radio" name="RememberPass" id="oneDay" style="margin-left: 8px" />
                                        <label for="oneDay" style="margin-left: 5px">1&nbsp天</label>

                                        <input type="radio" name="RememberPass" id="threeDay"  style="margin-left: 8px"/>
                                        <label for="threeDay" style="margin-left: 5px">3&nbsp天</label>

                                        <input type="radio" name="RememberPass" id="sevenDay"  style="margin-left: 8px"/>
                                        <label for="sevenDay" style="margin-left: 5px">7&nbsp天</label>


                                        <!--<input type="checkbox" id="keeplen" name="keeplength">-->
                                        <!--<label for="keeplen"></label>-->
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="2" align="center">
                                    <button class="log-button" type="submit">登录</button>
                                </td>
                            </tr>
                            <tr align="right" >

                                <td colspan="2" style="padding: 50px 0;">没有账号?<a href="/regist">注册一个</a></td>
                            </tr>
                        </table>

                    </form>
                </div>
                <div class="col-md-12 loginwrap" id="logged" style="height: 200px">
                    <h2 style="text-align: center">登录</h2>
                    <div class="seperator" style="background-color: #1d3c54"></div>
                    <div class="" style="margin-left: 70px">已作为<span id="username"></span>登录, 即将跳转到首页.....<div>
                </div>
            </div>
            <div class="row" style="height:20%;"></div>
        </div>
    </div>

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

