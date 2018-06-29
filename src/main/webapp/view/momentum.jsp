<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/29
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script src="../static/treeview/js/bootstrap-treeview.js"></script>
    <script src="../static/echarts/echarts.js"></script>
    <script src="../static/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <script type="text/javascript" src="../static/js/momentum.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/treeview/css/bootstrap-treeview.css"/>
    <link rel="stylesheet" href="../static/css/jquery-ui.min.css"/>
    <link rel="stylesheet" href="../static/css/animate.min.css">
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/computeStyle.css"/>
    <link rel="stylesheet" href="https://cdn.bootcss.com/hint.css/2.5.0/hint.base.css">
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
                        <li class="dropdown active">
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
                        <form action="/stockFunc/search"  class="searchwrap">
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
    <div class="container masking" >
        <form action="/backCompute" onsubmit="return toValid()" method="post" >
            <div class="row" id="inputPanel" style="margin-top: 60px">
                <div class="col-md-12">
                    <ul class="nav nav-tabs aw-nav-tabs">
                        <li id="momentumTab" class="active" onclick="toMomentum()"><a href="#">动量策略</a></li>
                        <li id="meanTab" onclick="toMean()"><a href="#">均值回归</a></li>
                    </ul>
                </div>
                <div class="col-md-4">

                    <h3 id="momTitle" style="color: #2aabd2; margin-bottom: 20px">动量策略
                        <a class="status-icon  hint--bottom-right  hint--info" style="background:lightblue" aria-label="动量交易策略，即预先对股票收益和交易量设定过滤准则，当股票收益或股票收益和交易量同时满足过滤准则就买入或卖出股票的投资策略。">
                        <svg style="width:24px;height:24px" viewBox="0 0 24 24">
                            <path fill="#ffffff" d="M12,2A7,7 0 0,1 19,9C19,11.38 17.81,13.47 16,14.74V17A1,1 0 0,1 15,18H9A1,1 0 0,1 8,17V14.74C6.19,13.47 5,11.38 5,9A7,7 0 0,1 12,2M9,21V20H15V21A1,1 0 0,1 14,22H10A1,1 0 0,1 9,21M12,4A5,5 0 0,0 7,9C7,11.05 8.23,12.81 10,13.58V16H14V13.58C15.77,12.81 17,11.05 17,9A5,5 0 0,0 12,4Z" />
                        </svg>
                    </a></h3>
                    <h3 id="meanTitle" style="color: #2aabd2; margin-bottom: 20px;display: none;">均值回归
                        <a class="status-icon  hint--bottom-right  hint--info" style="background:lightblue" aria-label="均值回归，无论是股票的高和低价格暂时的，股票的价格往往会在一段时间内的平均价格。于是确定一个股票的交易范围，然后计算平均价格使用的分析技术。">
                            <svg style="width:24px;height:24px" viewBox="0 0 24 24">
                                <path fill="#ffffff" d="M12,2A7,7 0 0,1 19,9C19,11.38 17.81,13.47 16,14.74V17A1,1 0 0,1 15,18H9A1,1 0 0,1 8,17V14.74C6.19,13.47 5,11.38 5,9A7,7 0 0,1 12,2M9,21V20H15V21A1,1 0 0,1 14,22H10A1,1 0 0,1 9,21M12,4A5,5 0 0,0 7,9C7,11.05 8.23,12.81 10,13.58V16H14V13.58C15.77,12.81 17,11.05 17,9A5,5 0 0,0 12,4Z" />
                            </svg>
                        </a></h3>
                    <div class="seperator" style="background-color:black;margin-bottom: 20px"></div>
                    <div style="margin-top: 10px; margin-bottom: 10px;">
                        <span >开始日期</span>
                        <span><input class="property" id="startDate" type="text" name="startDate"></span>
                    </div>
                    <div style="margin-top: 10px; margin-bottom: 10px;">
                        <span >结束日期</span>
                        <span><input class="property" id="endDate" type="text" name="endDate"></span>
                    </div>
                    <script type="text/javascript" language="JavaScript">
                        $("#startDate").datepicker({
                            dateFormat:"yy-mm-dd",
                        })
                        $("#endDate").datepicker({
                            dateFormat:"yy-mm-dd",
                        })
                    </script>
                    <div style="margin-top: 10px; margin-bottom: 10px;">
                        <span >形成日期</span>
                        <span><input class="property" id="form" type="text" name="form"></span>
                    </div>
                    <div style="margin-top: 10px; margin-bottom: 10px;">
                        <span >持有天数</span>
                        <span><input class="property" id="hold" type="text" name="hold"></span>
                    </div>
                </div>
                <div class="col-md-4">
                    <h3 style="color: #2aabd2; margin-bottom: 20px">寻找最佳形成/持有期</h3>
                    <div class="seperator" style="background-color:black;margin-bottom: 20px"></div>
                    <ul class="nav nav-tabs aw-nav-tabs">
                        <li id="formTab" class="active" onclick="toForm()"><a href="#">按形成期绘制</a></li>
                        <li id="holdTab" onclick="toHold()"><a href="#">按持有期绘制</a></li>
                    </ul>
                    <div id="relationForm" style="margin-top: 10px;">
                        <span >形成日期</span>
                        <span><input class="property"  type="text" name="relationForm"></span>
                    </div>
                    <div id="relationHold" style="margin-top: 10px;display: none;">
                        <span >持有天数</span>
                        <span><input class="property"  type="text" name="relationHold"></span>
                    </div>

                </div>
                <div class="col-md-4" style="padding-left: 20px">
                    <h3 style="color: #2aabd2;margin-bottom: 20px">选股</h3>
                    <div class="seperator" style="background-color:black;margin-bottom: 20px"></div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="1" onclick="noneDisplay()" checked> 大盘
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="2" onclick="showPlate()">板块

                        </label>
                    </div>
                    <%--<div class="radio">--%>
                        <%--<label>--%>
                            <%--<input type="radio" name="optionsRadios" id="optionsRadios3" value="3" onclick="showList()">自选股票--%>

                        <%--</label>--%>
                    <%--</div>--%>
                    <button type="submit" class="btn btn-primary" onclick="doCompute()">下一步</button>
                    <div><label id="tip" style="margin-top: 20px;color: red"></label></div>
                </div>
            </div>
            <div class="row" style="display: none;">
                <input type="text" value="1" name="relationNumber" id="relationNumber">
                <input type="text" value="1" name="strategyNumber" id="strategyNumber">
                <input type="text" value="0" name="plateNumber" id="plateChecked">
                <input type="text" value="0" name="nodes"  id="nodesChecked">
            </div>
        </form>


        <div class="row" id="platePanel" style="display: none" align="center">
            <div class="seperator" style="background-color:black"></div>
            <div class="col-sm-4">
                <div class="plate1-button" id="main-plate" onclick="mainPlate()" align="center">
                    <span>主板</span>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="plate2-button" id="enterprise-plate" onclick="enterprisePlate()" align="center">
                    <span>创业板</span>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="plate3-button" id="medium-plate" onclick="mediumPlate()" align="center">
                    <span>中小板</span>
                </div>
            </div>
        </div>

        <div class="row" id="treePanel" style="display: none">
            <div class="seperator" style="background-color:black"></div>
            <div class="col-md-12">
                <h4 id="loadingMsg">loading.....</h4>
                <div id="tree"></div>
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

<script type="text/javascript" language="JavaScript">

</script>
</body>
</html>
