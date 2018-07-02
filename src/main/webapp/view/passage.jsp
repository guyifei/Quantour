<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--作为模板使用-->
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${post.title}-AnyQuantour</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/passageStyle.css"/>
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
                        <li class=""><a href="/bbs">论坛</a></li>
                    </ul>
                    <div class="header-right">
                        <form action="/stockFunc/search" class="searchwrap">
                            <input oninput="ac(this)" autocomplete="off" class="searchbox" type="text" name="key" placeholder="搜索股票" required>
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
    <div class="container masking" style="background-color: transparent; box-shadow: none;">
        <div class="passage-wrap">
            <div class="row passage-content">
                <span class="passage-title">${post.title}</span>
                <div class="col-md-2 passage-userinfo-wrap" align="center">
                    <div class="passage-avatar">
                        <c:choose>
                            <c:when test="${author.graph!=null}">
                                <img src="${author.graph}"/>
                            </c:when>
                            <c:otherwise>
                                <img src="../data/avatar/default.png"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="passage-user">
                        <div class="passage-user-name">${author.userName}</div>
                    </div>
                </div>
                <div class="col-md-10">
                    <pre class="passage-text">${post.text}</pre>
                    <p align="right">发表于<fmt:formatDate value="${post.date}"/></p>
                </div>
            </div>
        </div>
        <div class="passage-comments-wrap">
            <c:set var="commentPerPage" value="20"/>
            <c:set var="size" value="${answers.size()}"/>
            <c:forEach var="nowPage" begin="0" end="${(size-size%commentPerPage)/commentPerPage}">
                <div class="comment-page ${nowPage==0?"active":""}" id="page${nowPage+1}">
                    <c:choose>
                        <c:when test="${size>0}">
                            <c:forEach var="index" begin="0" end="${commentPerPage-1}">
                                <c:set var="i" value="${(nowPage*commentPerPage+index)}"/>
                                <c:if test="${answers.size()>nowPage*commentPerPage+index}">
                                    <c:set var="comment" value="${answers.get(i)}"/>
                                    <c:if test="${index>0}">
                                        <div class="comment-seperator"></div>
                                    </c:if>
                                    <div class="row passage-comment-single">
                                        <div class="col-sm-2 comment-user-wrap" align="center">
                                            <div class="comment-avatar">
                                                <c:choose>
                                                    <c:when test="${answerAuthors.get(i).graph!=null}">
                                                        <img src="${answerAuthors.get(i).graph}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="../data/avatar/default.png"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="comment-name">
                                                    ${answerAuthors.get(i).userName}
                                            </div>
                                        </div>
                                        <div class="col-sm-10 comment-content-wrap">
                                            <p class="floor"><i>#${answers.get(i).floor}</i></p>
                                            <pre>${answers.get(i).text}</pre>
                                            <div align="right">
                                                发表于<fmt:formatDate value="${answers.get(i).date}"/>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div align="center" style="padding: 30px;">
                                <p>暂无评论, 快来第一个评论吧~</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </div>
        <div class="pagination-wrap" align="center">
            <ul class="pagination">
                <c:forEach var="j" begin="1" end="${(size-size%commentPerPage)/commentPerPage+1}">
                    <li class="${j==1?"active":""}"><a href="#page${j}">${j}</a></li>
                </c:forEach>
            </ul>
            <script type="text/javascript" language="JavaScript">
                jQuery(".pagination a").click(function(){
                    jQuery(this).tab("show");
                })
            </script>
        </div>
        <div class="passage-comments-create-wrap passage-input-wrap">
            <span>评论</span>
            <div>
                <form action="/passageFunc/comment" method="post">
                    <input name="passage_id" value="${post.post_id}" style="display: none">
                    <textarea name="comment" ${islogged?"":"disabled"} class="passage-input passage-input-content passage-input-content-wrap" required>
${islogged?"":"没有登录,无法评论"}
                    </textarea>
                    <div align="right">
                        <button align="right" type="submit" class="button">评论</button>
                    </div>
                </form>
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

