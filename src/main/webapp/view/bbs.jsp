<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/bbs.css"/>
    <link rel="stylesheet" href="../static/css/inputStyle.css"/>
    <script type="text/javascript" language="JavaScript">
        window.onload=function(){
            pageLogCheck();
            var k=document.getElementsByClassName("masking")[0];
            $(k).css("min-height", document.body.clientHeight-170);
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
                        <li class="active"><a href="#">论坛</a></li>
                    </ul>
                    <div class="header-right">
                        <form action="/stockFunc/search" class="searchwrap">
                            <input oninput="ac(this)" autocomplete="off" class="searchbox" type="text" name="stockname" placeholder="搜索股票" required>
                            <button align="center" class="searchbutton" type="submit" id="search_button"></button>
                        </form>
                        <div class="log-switch" id="logged">
                            <div style="display: inline-block">
                                <a class="account-wrap-button" title="个人主页" align="center" href="/profile"><img id="avatar" src="../static/images/icon/login.png"></a>
                            </div>

                            <div class="username-label">
                                <div id="username" ></div>
                            </div>
                            <div style="display: inline-block">
                                <a class="account-wrap-button" title="登出" align="center" onclick="logout()"><img style="margin-top:2px;margin-left:1px;" src="../static/images/icon/logout.png"></a>
                            </div>

                        </div>
                        <div class="log-switch" id="not-logged">
                            <a class="account-wrap-button" title="登录" align="center" href="/login"><img src="../static/images/icon/login.png"></a>
                            <a class="account-wrap-button" title="注册" align="center" href="/regist"><img src="../static/images/icon/register.png"></a>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </div>
</div>
<div class="content">
    <div class="container masking">
        <div class="col-md-12">

            <div>
                <ul class="nav nav-tabs aw-nav-tabs">
                    <li class="active"><a href="#newest">最新</a></li>
                    <%--<li><a href="#">热门</a></li>--%>
                    <%--<li><a href="#">推荐</a></li>--%>
                </ul>
            </div>
            <div>
                <a href="/newpassage" class="bbs-input-button">发表帖子</a>
            </div>
            <div id="newest" class="passage-list-wrap">
                <c:set var="perPage" value="20"/>
                <c:set var="size" value="${passageList.size()}"/>
                <c:forEach var="nowPage" begin="0" end="${(size-size%perPage)/perPage}">
                    <div class="passage-list-page ${nowPage==0?"active":""}" id="new_page${nowPage+1}">
                        <c:forEach var="index" begin="0" end="${perPage-1}">
                            <c:if test="${size>nowPage*perPage+index}">
                                <a href="/passage/${passageList.get(nowPage*perPage+index).post_id}">
                                    <div class="passage-list-single">
                                        <span>${passageList.get(nowPage*perPage+index).title}</span>

                                        <span style="float: right;"><fmt:formatDate value="${passageList.get(nowPage*perPage+index).date}"/></span>
                                        <span style="color: gray;float: right;margin: 0 10px;"><i>作者:${passageList.get(nowPage*perPage+index).user_name}</i></span>
                                    </div>
                                </a>
                            </c:if>

                        </c:forEach>
                    </div>
                </c:forEach>
                <ul class="pagination">
                    <c:forEach var="j" begin="1" end="${(size-size%perPage)/perPage+1}">
                        <li class="${j==1?"active":""}"><a href="#new_page${j}">${j}</a></li>
                    </c:forEach>
                </ul>
                <script type="text/javascript" language="JavaScript">
                    jQuery(".pagination a").click(function(){
                        jQuery(this).tab("show");
                        return false;
                    })
                </script>
            </div>
            <div id="hottest" class="passage-list-wrap"></div>
            <div id="recommond" class="passage-list-wrap"></div>
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

