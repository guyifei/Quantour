<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour</title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script src="../static/js/jquery-ui.min.js"></script>
    <script src="../static/echarts/echarts.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/jquery-ui.min.css"/>
    <link rel="stylesheet" href="../static/css/stock.css"/>
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
                        <li class="active"><a href="/neuralLearn">预测</a></li>
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
    <div class="container masking">
        <div class="row" style="padding-left: 10px">
            <input placeholder="起始日期" class="compare_input property property-white" type="text" id="startDate" required>
            <input placeholder="中间日期" class="compare_input property property-white" type="text" id="midDate" required>
            <input placeholder="结束日期" class="compare_input property property-white" type="text" id="endDate" required>
            <input class="compare_input property property-white" type="text" oninput="ac(this)" id="newStock" placeholder="请输入股票" required>
            <script type="text/javascript" language="JavaScript">
                $("#startDate").datepicker({
                    dateFormat:"yy-mm-dd",
                });
                $("#midDate").datepicker({
                    dateFormat:"yy-mm-dd",
                });
                $("#endDate").datepicker({
                    dateFormat:"yy-mm-dd",
                });
            </script>
            <button class="compare_button" type="submit" onclick="run()">运行</button>
            <div><label id="tip" style="margin-top: 20px;color: red"></label></div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h4 class="title">股票走势图</h4>
                <div class="seperator" style="background-color:black"></div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div id="trendChart" style="width: 100%;height:400px;"></div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <h4 class="title">算法收益曲线图</h4>
                <div class="seperator" style="background-color:black"></div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div id="earnChart" style="width: 100%;height:400px;"></div>
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

<script type="text/javascript">
    var trendChart=echarts.init(document.getElementById("trendChart"));
    var earnChart=echarts.init(document.getElementById("earnChart"));

    function run(){
        var startDate=$("#startDate").val();
        var midDate=$("#midDate").val();
        var endDate=$("#endDate").val();
        var newStock=$("#newStock").val();

        if(startDate == ""||endDate==""||midDate==""||newStock==""){
            $("#tip").text("请完成输入");
            return;
        }
        trendChart.showLoading();
        earnChart.showLoading();
        $("#tip").text("");
        $.ajax({
            url:"/stockFunc/neural",
            data:{stockname:newStock,startDate:startDate,midDate:midDate,endDate:endDate},
            dataType:"json",
//            async: false,
            success:function(result){
                if(result=="wrong"){
                    alert("时间过短，无法完成训练！")
                }else{
                    fillTrend(result);
                    fillEarn(result);
                }

            }
        });
    }

    function fillTrend(data){

        trendChart.hideLoading();
        window.onresize=function(){
            trendChart.resize();
        }

        option = {
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                data:['拟合', '真实'],
                x: 'left'
            },
            toolbox: {
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    restore: {},
                    saveAsImage: {}
                }
            },
            dataZoom: [
                {
                    show: true,
                    start: 0,
                    end: 100
                },
                {
                    type: 'inside',
                    start: 0,
                    end: 100
                }
            ],
            xAxis : {
                type : 'category',
                data: data.dateList,
                show:false,
                axisLine: {onZero: false},
            },

            yAxis : {
                type : 'value',
                axisLine: {onZero: false},
            },
            series :[
                {
                    name:'拟合',
                    type:'line',
                    smooth:true,
                    data:data.expect
                },
                {
                    name:'真实',
                    type:'line',
                    smooth:true,
                    data:data.target
                },
            ]
        };
        trendChart.setOption(option);
    }

    function fillEarn(data) {
        earnChart.hideLoading();
        window.onresize=function(){
            earnChart.resize();
        }

        option = {
            tooltip: {
                trigger: 'axis',
            },
            toolbox: {
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    restore: {},
                    saveAsImage: {}
                }
            },
            dataZoom: [
                {
                    show: true,
                    start: 0,
                    end: 100
                },
                {
                    type: 'inside',
                    start: 0,
                    end: 100
                }
            ],
            xAxis : {
                type : 'category',
                data: data.dateList,
                show:false,
                axisLine: {onZero: false},
            },

            yAxis : {
                type : 'value',
                axisLine: {onZero: false},
            },
            series :[
                {
                    name:'年化收益率',
                    type:'line',
                    smooth:true,
                    data:data.earn
                },

            ]
        };
        earnChart.setOption(option);
    }
</script>

</body>
</html>

