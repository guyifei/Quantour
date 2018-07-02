<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="anyeight.model.UserInfo" %>
<!--作为模板使用-->
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    UserInfo info = (UserInfo) request.getAttribute("result");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/js/jquery.form.min.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <script src="../static/js/profilefunc.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/profileStyle.css"/>
    <script type="text/javascript" language="JavaScript">
        window.onload=function(){
            pageLogCheck();
            <%--init(<%=forjson%>);--%>
            var k=document.getElementsByClassName("masking")[0];
            $(k).css("min-height", document.body.clientHeight-170);
        };

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
                                <a class="account-wrap-button" title="个人主页" align="center" href="/profile">
                                    <c:if test="${result.graph!=null}">
                                        <img id="avatar" src="<%=info.getGraph()%>">
                                    </c:if>
                                    
                                </a>
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
        <div class="row">
            <div class="col-md-12">
                <h2>个人信息</h2>
                <h4>Profile</h4>
            </div>
        </div>
        <div class="seperator" style="background-color: rgba(160,160,160,1);width:100%;"></div>
        <div class="row">
            <div class="col-md-2">
                <ul class="nav anyeight-nav" id="navpill">
                    <li class="active" ><a href="#profile">个人信息</a></li>
                    <li><a href="#favorite">股票收藏</a></li>
                    <li><a href="#message">我的动态</a></li>
                </ul>
            </div>
            <div class="col-md-10">
                <div class="sub_page active" id="profile">
                    <div class="avatar-wrap">
                        <c:choose>
                            <c:when test="${result.graph!=null}">
                                <img id="profile-avatar" src="${result.graph}">
                            </c:when>
                            <c:otherwise>
                                <img id="profile-avatar" src="../data/avatar/default.png">
                            </c:otherwise>
                        </c:choose>
                        <form class="upload-wrap" id="upload-form" target="faker" action="/modify_avatar" method="post" enctype="multipart/form-data">
                            <textarea id="faker" style="display: none;"></textarea>
                            <div class="upload-button" style="width: 100px;">修改头像
                                <script type="text/javascript" language="JavaScript">
                                    function note(result) {
                                        var str = result == "fail" ? "失败. 服务器可能抽风了,请稍后再试 QwQ" : "成功.";
                                        if(result=="sizeOverflow"){
                                            str="失败, 文件尺寸过大, 请保持在5mb内"
                                            return false;
                                        }
                                        //alert(result);
                                        $("#path").text("上传" + str);
                                        $("#avatar-cancel").attr("class", "");
                                        $("#avatar-confirm").attr("class", "");
                                        if (result != "fail") {
                                            $("#profile-avatar").attr("src", "../data/avatar/default.png");
                                            $("#avatar").attr("src", "../data/avatar/default.png");
                                            $("#profile-avatar").attr("src", result);
                                            $("#avatar").attr("src", result);
                                        }
                                    }
                                    function setPathOnly(){
                                        $("#path").text($("#path-input").val());
                                    }
                                    function setPath(){
                                        $("#path-input").change=setPathOnly();
                                        $("#path").text($("#path-input").val());
                                        $("#avatar-cancel").attr("class","active");
                                        $("#avatar-confirm").attr("class","active");

                                        var option={
                                            target:"faker",
                                            success:note,
                                            dataType:"text"
                                        };
                                        $("#upload-form").ajaxForm(option);
                                    }
                                </script>
                                <input type="file" id="path-input" name="file" accept="image/jpeg,image/png,image/bmp,image/gif" onchange="setPath()"/>
                            </div>
                            <span id="path"></span>
                            <span class="avatar-button-wrap">
                                <button class="" id="avatar-cancel" onclick="cancelAvatar()" style="background: url('../static/images/icon/cancel.png') no-repeat center center;"></button>
                                <button class="" id="avatar-confirm" type="submit" style="background: url('../static/images/icon/confirm.png') no-repeat center center;"></button>
                            </span>
                        </form>
                    </div>
                    <div class="seperator" style="background-color: rgba(160,160,160,1); width: 80%; margin-right:20%; height:1px;"></div>
                    <div class="profile-wrap">
                        <div class="profile-single">
                            <span class="title">用户ID</span>
                            <span id="input-id">${result.user_idId}</span>
                        </div>

                        <div class="profile-single">
                            <span class="title">用户名</span>
                            <span><input id="input-us" type="text" title="用户名" value="${result.userName}" disabled></span>
                        </div>
                        <div class="profile-single">
                            <span class="title">电话号码</span>
                            <span><input id="input-tel" type="tel" title="电话号码" value="${result.telephone}" disabled></span>
                        </div>
                        <div class="profile-single">
                            <span class="title">电子邮箱</span>
                            <span><input id="input-email" type="email" title="电子邮箱" value="${result.email}" disabled></span>
                        </div>
                    </div>
                    <div class="seperator" style="background-color: rgba(160,160,160,1); width: 80%; margin-right:20%; height:1px;"></div>
                    <div class="option-wrap" align="right">
                        <table>
                            <tr>
                                <td><button id="button-confirm" onclick="confirmEdit()" style="background: url('../static/images/icon/confirm.png') no-repeat center center;"></button></td>
                                <td><button class="active" id="button-cancel" onclick="startEdit()" style="background: url('../static/images/icon/edit.png') no-repeat center center;"></button></td>
                                <td><button class="active" id="button-changepw" style="background: url('../static/images/icon/password.png') no-repeat center center;"></button></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="sub_page" id="favorite">
                    <h3 class="sub_page-title">股票收藏</h3>
                    <div class="seperator" style="height:1px; background-color: rgb(160,160,160); width: 80%; margin-right:20%;"></div>
                    <div class="stocks-wrap" style="width:80%;margin-right:20%;">
                        <c:set var="perPage" value="7" scope="page"/>
                        <c:set var="totalPage" value="${(favorites.size()-(favorites.size()%perPage))/perPage}"/>
                        <c:if test="${totalPage>0}">
                            <ul class="pagination" id="pageination">
                                <c:forEach var="i" begin="1" end="${totalPage+1}">
                                    <li class="${i==1?"active":""}"><a href="#page${i}" >${i}</a></li>
                                </c:forEach>

                            </ul>
                            <script type="text/javascript" language="JavaScript">
                                jQuery("#pageination a").click(function(){
                                    jQuery(this).tab("show");
                                });
                            </script>

                        </c:if>
                        <c:choose>
                            <c:when test="${favorites.size()!=0}">
                                <c:forEach begin="0" end="${totalPage}" var="i">
                                    <div class="page${i==0?" active":""}" id="page${i+1}">
                                        <c:forEach begin="0" end="${perPage-1}" var="j">
                                            <c:if test="${favorites.size()>i*perPage+j}">
                                                <div class="stock-single-wrap" id="${favorites.get(i*perPage+j)}">
                                                    <a href="/stock?stockname=${favorites.get(i*perPage+j)}">
                                                        <div class="single-title" align="center">
                                                            <span class="stock-name">${favoritesName.get(i*perPage+j)}</span>
                                                            <span class="stock-id">${favorites.get(i*perPage+j)}</span>
                                                        </div>
                                                    </a>
                                                    <div class="profile-single-info">
                                                        <button onclick="delFavor(this)" class="button" style="background:url('../static/images/icon/delete.png') no-repeat center center;">
                                                            <input type="text" style="display: none" name="stockId" value="${favorites.get(i*perPage+j)}"/>
                                                        </button>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div>
                                    暂无股票收藏
                                </div>
                            </c:otherwise>

                        </c:choose>


                    </div>
                </div>
                <div class="sub_page" id="message">
                    <div>
                        <ul class="nav nav-tabs" id="activity-tab">
                            <li class="active"><a href="#posts-page" target="_parent">我的主题</a></li>
                            <li><a href="#answers-page">我的回复</a></li>
                        </ul>
                    </div>
                    <div class="activity-page active" id="posts-page">
                        <c:set var="perPage" value="6"/>
                        <c:set var="totalPage" value="${(postList.size()-postList.size()%perPage)/perPage}"/>
                        <c:forEach var="nowPage" begin="0" end="${totalPage}">
                            <div id="postSub${nowPage+1}" class="activity-page-sub ${nowPage==0?"active":""}">
                                <ul>
                                    <c:forEach var="i" begin="0" end="${perPage-1}">
                                        <c:set var="index" value="${nowPage*perPage+i}"/>
                                        <c:if test="${index<postList.size()}">
                                            <li>
                                                <div class="message-single-wrap">
                                                    <div class="message-single-part message-single-avatar"><img src=${result.graph==null?"../data/avatar/default.png":result.graph}></div>
                                                    <div class="message-single-part message-single-detail">
                                                        <div class="message-single-from_to">
                                                            <span>${postList.get(index).date}</span><br/>
                                                            <span><strong>${postList.get(index).user_name}</strong>发表了帖子:<a href="/passage/${postList.get(index).post_id}"><strong>${postList.get(index).title}</strong></a></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:forEach>
                        <ul class="pagination" id="postPagenation">
                            <c:forEach var="i" begin="1" end="${totalPage}">
                                <li class="${i==1?"active":""}"><a href="#postSub${i}" >${i}</a></li>
                            </c:forEach>
                        </ul>
                        <script type="text/javascript" language="JavaScript">
                            jQuery("#postPagenation a").click(function(){
                                jQuery(this).tab("show");
                                return false;
                            });
                        </script>
                    </div>
                    <div class="activity-page" id="answers-page">
                        <c:set var="perPage" value="6"/>
                        <c:set var="totalPage" value="${(answerList.size()-answerList.size()%perPage)/perPage}"/>
                        <c:forEach var="nowPage" begin="0" end="${totalPage}">
                            <div id="answerSub${nowPage+1}" class="activity-page-sub ${nowPage==0?"active":""}">
                                <ul>
                                    <c:forEach var="i" begin="0" end="${perPage-1}">
                                        <c:set var="index" value="${nowPage*perPage+i}"/>
                                        <c:if test="${index<answerList.size()}">
                                            <li>
                                                <div class="message-single-wrap">
                                                    <div class="message-single-part message-single-avatar"><img src=${result.graph==null?"../data/avatar/default.png":result.graph}></div>
                                                    <div class="message-single-part message-single-detail">
                                                        <div class="message-single-from_to">
                                                            <span>${answerList.get(index).date}</span><br/>
                                                            <span><strong>${result.userName}</strong>评论了<a href="/passage/${answerList.get(index).origin_id}"><strong>${answerList.get(index).origin_id}</strong></a></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:forEach>
                        <ul class="pagination" id="answerPagenation">
                            <c:forEach var="i" begin="1" end="${totalPage}">
                                <li class="${i==1?"active":""}"><a href="#answerSub${i}" >${i}</a></li>
                            </c:forEach>
                        </ul>
                        <script type="text/javascript" language="JavaScript">
                            jQuery("#answerPagenation a").click(function(){
                                jQuery(this).tab("show");
                                return false;
                            });
                        </script>
                    </div>
                    <script type="text/javascript" language="JavaScript">
                        $("#activity-tab a").click(function(){
                            $(this).tab("show");
                            return false;
                        })
                    </script>
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

