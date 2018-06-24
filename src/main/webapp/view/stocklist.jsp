<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="anyeight.service.UserService" %>
<%@ page import="anyeight.controller.Tools" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--作为模板使用-->
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    String logins;
    if(true){//登录判断
        logins="<a class='account-wrap-button' align='center' href='/login'><img src='../static/images/icon/login.png'></a>\n" +
                "<a class='account-wrap-button' align='center' href='/regist'><img src='../static/images/icon/register.png'></a>";

    }else{

    }
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <script src="../static/js/stlist.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/stocklistStyle.css"/>
    <script type="text/javascript" language="JavaScript">
        window.onresize=function(){
            var k=document.getElementsByClassName("masking");
            var mh=document.body.clientHeight-170>600?document.body.clientHeight-170:600;
            for(var i=0;i<k.length;i++){
                $(k[i]).css("min-height", mh);
            }
        }
        window.onload=function(){
            pageLogCheck();
            var k=document.getElementsByClassName("masking");
            var mh=document.body.clientHeight-170>600?document.body.clientHeight-170:600;
            for(var i=0;i<k.length;i++){
                $(k[i]).css("min-height", mh);
            }
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
                        <li class="active"><a href="">股票</a></li>
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
    <div id="search-wrap" class="container masking">
        <div class="search-result">
            <div class="row" style="top: 300px;" >
                <div style="max-width: 500px;">
                    <form action="/stockFunc/search" style="margin-bottom: 5px;">
                        <iframe name="thisform" style="display: none"></iframe>
                        <input oninput="ac(this)" autocomplete="off" class="searchbox-solid" type="search" name="key" placeholder="搜索股票" required>

                        <button class="searchbutton-solid" type="submit"><img src="../static/images/icon/search.png" style="width: 20px;height:20px;padding:0;z-index: 0;position: relative;"></button>
                    </form>
                </div>
            </div>
            <div class="seperator" style="background-color: #535354; width: 100%;"></div>
            <div id="search-result" class="row page ${marketList==null?"active":""}" align="center">
                <div class="col-md-8 singles-wrap" id="result-wrap">
                    <c:set var="perPage" value="7" scope="page"/>
                    <c:set var="totalPage" value="${(list.size()-(list.size()%perPage))/perPage}"/>
                    <c:choose>
                        <c:when test="${list.size()!=0}">
                            <c:forEach begin="0" end="${totalPage}" var="i">
                                <div class="page${i==0?" active":""}" id="page${i+1}">
                                <c:forEach begin="0" end="${perPage-1}" var="j">
                                    <c:if test="${list.size()>i*perPage+j}">
                                        <div class="stock-single-wrap">
                                            <a href="../stock?stockname=${list.get(i*perPage+j).name}">
                                            <div class="single-title" align="left">
                                                <span class="stock-name">${list.get(i*perPage+j).name}</span>
                                                <span class="stock-id">${list.get(i*perPage+j).code}</span>
                                            </div>
                                            </a>
                                            <div class="single-info">
                                                <table style="width: 100%;padding: 3px; text-align: center;" align="center" class="info-table">
                                                    <tr>
                                                        <td>涨跌幅</td>
                                                        <c:choose>
                                                            <c:when test="${((list.get(i*perPage+j).close-list.get(i*perPage+j).open)/list.get(i*perPage+j).open)>0}">
                                                                <td style="color: red"><fmt:formatNumber value="${(list.get(i*perPage+j).close-list.get(i*perPage+j).open)/list.get(i*perPage+j).open}" pattern="#0.0000"/></td>
                                                            </c:when>
                                                            <c:when test="${((list.get(i*perPage+j).close-list.get(i*perPage+j).open)/list.get(i*perPage+j).open)<0}">
                                                                <td style="color: limegreen"><fmt:formatNumber value="${(list.get(i*perPage+j).close-list.get(i*perPage+j).open)/list.get(i*perPage+j).open}" pattern="#0.0000"/></td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td><fmt:formatNumber value="${(list.get(i*perPage+j).close-list.get(i*perPage+j).open)/list.get(i*perPage+j).open}" pattern="#0.0000"/></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td>行业</td>
                                                        <td>${list.get(i*perPage+j).market}</td>
                                                        <td rowspan="3">
                                                            <button onclick="favorite(this)" id="button" class="button" style="background:url('../static/images/icon/star_${favorList.get(i*perPage+j)?"filled":"blank"}.png') no-repeat center center;">
                                                                <input type="text" name="stockId" style="display: none" value="${list.get(i*perPage+j).code}"/>
                                                                <input type="text" name="favor" style="display: none" value="${favorList.get(i*perPage+j)}"/>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>昨开</td>
                                                        <td>${list.get(i*perPage+j).open}</td>
                                                        <td>昨收</td>
                                                        <td>${list.get(i*perPage+j).close}</td>
                                                    </tr>
                                                </table>
                                            </div>

                                        </div>


                                    </c:if>
                                </c:forEach>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:when test="${list.size()==0}">
                            <div>
                                未搜索到相关股票
                            </div>
                        </c:when>
                    </c:choose>
                    <c:if test="${totalPage>0}">
                        <ul class="pagination" id="pageination">
                           <c:forEach var="i" begin="1" end="${totalPage+1}">
                               <li class="${i==1?"active":""}"><a href="#page${i}" >${i}</a></li>
                           </c:forEach>

                        </ul>
                        <script type="text/javascript" language="JavaScript">
                            jQuery("#pageination a").click(function(){
                                jQuery(this).tab("show");
                                return false;
                            });
                        </script>

                    </c:if>
                </div>
                <div class="col-md-4"></div>
            </div>
            <div id="search-welcome" class="page ${marketList==null?"":"active"}">
                <div class="col-md-12 search-welcome-wrap" align="center">
                    <div style="color: #cccccc; font-size: 36px;">或许你可以试试这些板块?</div>
                    <%
                        int count=1;
                    %>
                    <c:forEach var="market" items="${marketList}" varStatus="stat">
                        <c:set var="size" value="<%=(int)(Math.random()*100+75)%>"/>
                        <a href="/stockFunc/search?key=${market}" id="cir1" class="circle" style="height:${size}px;width: ${size}px;top:<%=Math.random()*300+50%>px;left:<%=Math.random()*3+(count++)*9%>%;background-color: hsl(<%=(int)(Math.random()*360)%>,60%,60%);opacity:0.75"><div>${market}</div></a>
                    </c:forEach>
                    <%--<a href="/stockFunc/search?key=${marketList.get(0)}" id="cir1" class="circle" style="top:<%=Math.random()*400+50%>px;left:<%=Math.random()*800%>px;background-color: #adccc1"><div>${marketList.get(0)}</div></a>--%>
                    <%--<a href="/stockFunc/search?key=${marketList.get(1)}" id="cir2" class="circle" style="top:<%=Math.random()*400+50%>px;left:<%=Math.random()*800%>px;background-color: #e5db75"><div>${marketList.get(1)}</div></a>--%>
                    <%--<a href="/stockFunc/search?key=${marketList.get(2)}" id="cir3" class="circle" style="top:<%=Math.random()*400+50%>px;background-color: #ec807f"><div>${marketList.get(2)}</div></a>--%>
                </div>
            </div>

        </div>
    </div>
</div>
<footer class="footer">
    <div class="container">
        <div class="row seperator"></div>
        <div class="row">
            <div class="col-sm-5 clu">
                <p>由AnyEight小组制作</p>
                <p>此网站为课程学习项目, 不允许用于商业用途.</p>
                <p>NJU Software Institute 2017.5</p>
            </div>
            <div class="col-sm-7"></div>
        </div>
    </div>
</footer>
</body>
</html>

