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
    <script src="../static/echarts/echarts.js"></script>
    <script src="../static/shCircleLoader/jquery.shCircleLoader.js" type="text/javascript"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/ae-animate.css"/>
    <script type="text/javascript" language="JavaScript">
        window.onload=function(){
            pageLogCheck();
        }
    </script>
</head>
<body>
<div id="loading-wrap" class="loading-wrap">
    <div class="spinner">
        <div class="rect1"></div>
        <div class="rect2"></div>
        <div class="rect3"></div>
        <div class="rect4"></div>
        <div class="rect5"></div>
        <div class="rect6"></div>
        <div class="rect7"></div>
        <div class="rect8"></div>
        <div class="rect9"></div>
        <div class="rect10"></div>
        <div class="rect11"></div>
        <div class="rect12"></div>
    </div>
</div>
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
                        <form action="/stock" class="searchwrap">
                            <input  oninput="ac(this)" autocomplete="off" class="searchbox" type="text" name="stockname" placeholder="搜索股票" required>
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

        <div class="row">
                <div class="col-md-12">
                    <h4 class="title">累计收益率比较图</h4>
                    <div class="seperator" style="background-color:black"></div>
                </div>
            </div>
        <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>年化收益率</th>
                            <th>基准年化收益率</th>
                            <th>阿尔法</th>
                            <th>贝塔</th>
                            <th>夏普比率</th>
                            <th>最大回撤</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td id="td_annual">0</td>
                            <td id="td_standard">0</td>
                            <td id="td_alpha">0</td>
                            <td id="td_beta">0</td>
                            <td id="td_sharp">0</td>
                            <td id="td_drawdown">0</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        <div class="row">
                <div class="col-md-12">
                    <%--用于添加图表--%>
                    <div id="line_chart" style="width: 100%;height: 400px;"></div>
                </div>
            </div>

        <div class="row">
                <div class="col-md-12">
                    <h4 class="title">收益率分布直方图</h4>
                    <div class="seperator" style="background-color:black"></div>
                </div>
            </div>
        <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>正收益周期数</th>
                            <th>负收益周期数</th>
                            <th>赢率</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td id="positive">0</td>
                            <td id="negative">0</td>
                            <td id="winrate">0</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        <div class="row">
                <div class="col-md-12">
                    <%--用于添加图表--%>
                    <div id="bar_chart" style="width: 100%;height: 400px;"></div>
                </div>
            </div>

        <%--<div class="row">--%>
                <%--<div class="col-md-12">--%>
                    <%--<h4 class="title">关系图</h4>--%>
                    <%--<div class="seperator" style="background-color:black"></div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--<div class="row" id="relationPanel">--%>
                <%--<div class="col-md-6">--%>
                    <%--<table class="table table-hover">--%>
                        <%--<thead>--%>
                        <%--<tr>--%>
                            <%--<th>相对强弱计算周期</th>--%>
                            <%--<th>超额收益</th>--%>
                            <%--<th>一年内胜率</th>--%>
                        <%--</tr>--%>
                        <%--</thead>--%>
                        <%--<tbody id="tbody">--%>
                        <%--</tbody>--%>
                    <%--</table>--%>
                <%--</div>--%>
                <%--<div class="col-md-6">--%>
                    <%--&lt;%&ndash;用于添加图表&ndash;%&gt;--%>
                    <%--<div id="area_chartA" style="width: 100%;height: 400px; "></div>--%>
                    <%--<div id="area_chartB" style="width: 100%;height: 400px; "></div>--%>
                <%--</div>--%>
            <%--</div>--%>


    </div>
    <div class="loading-wrap">

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

<script type="text/javascript" language="JavaScript">
//TODO begin
$(".container.masking").css("filter","blur(5px)");
//    $('#relationPanel').shCircleLoader({color: "cornflowerblue"});

    var info={startDate:"<%=request.getAttribute("startDate")%>",endDate:"<%=request.getAttribute("endDate")%>",
        form:"<%=request.getAttribute("form")%>",hold:"<%=request.getAttribute("hold")%>",
        optionsRadios:"<%=request.getAttribute("optionsRadios")%>",plateNumber:"<%=request.getAttribute("plateNumber")%>",
        nodes:eval(<%=request.getAttribute("nodes")%>),strategyNumber:"<%=request.getAttribute("strategyNumber")%>"};


    var relation={startDate:"<%=request.getAttribute("startDate")%>",endDate:"<%=request.getAttribute("endDate")%>"
        ,relationNumber:"<%=request.getAttribute("relationNumber")%>",relationForm:"<%=request.getAttribute("relationForm")%>",
    relationHold:"<%=request.getAttribute("relationHold")%>",strategyNumber:"<%=request.getAttribute("strategyNumber")%>"};

    $.ajax({
        url:"/computeFunc/lineChart",
        type:"post",
        data:info,
        dataType:"json",
        traditional: true,
//        async: false,
        success:function(result){
//TODO over

            fillLineChart(result);

            fillBarChart(result);
            $("#loading-wrap").css("display", "none");
            $(".container.masking").css("filter","none");

//            doNext();
        }
    });

    function doNext(){
        $.ajax({
            url:"/computeFunc/areaChart",
            data:relation,
            dataType:"json",
//        async: false,
            success:function(result){
//            $('#relationPanel').shCircleLoader('destroy');
                fillAreaChart(result);
                fillTable(result);
            }
        });
    }





    function toPercent(point){
        var str=Number(point*100).toFixed(3);
        str+="%";
        return str;
    }

    function fix(str) {
        if(str=="NaN"||str=="Infinity"||str=="-Infinity"){
            return str;
        }else{
            var result=Number(str).toFixed(3);
            return result;
        }
    }

    function fillLineChart(data){
        document.getElementById("td_annual").innerHTML=toPercent(data.annualizedReturn);
        document.getElementById("td_standard").innerHTML=toPercent(data.standardAnnualizedReturn);
        document.getElementById("td_alpha").innerHTML=fix(data.Alpha);
        document.getElementById("td_beta").innerHTML=fix(data.Beta);
        document.getElementById("td_sharp").innerHTML=fix(data.sharpeRatie);
        document.getElementById("td_drawdown").innerHTML=toPercent(data.maximumDrawdown);



        var myChart = echarts.init(document.getElementById('line_chart'));

        option = {
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                data:['策略','基准'],
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
            },

            yAxis : {
                name : '收益率',
                type : 'value',
            },
            series :[
                {
                    name:'策略',
                    type:'line',
                    smooth:true,
                    data:data.strategyEarningList
                },
                {
                    name:'基准',
                    type:'line',
                    smooth:true,
                    data:data.standardEarningList
                },
            ]
        };
        myChart.setOption(option);
    }

    function fillBarChart(data){
        document.getElementById("positive").innerHTML=data.positive;
        document.getElementById("negative").innerHTML=data.negative;
        document.getElementById("winrate").innerHTML=toPercent(data.rate);


        var myChart = echarts.init(document.getElementById('bar_chart'));
        option = {
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                data:['正收益次数','负收益次数'],
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
                data: data.cateAxis
            },

            yAxis : {
                name : '收益率',
                type : 'value',
            },
            series :[
                {
                    name:'正收益次数',
                    type:'bar',
                    data:data.pAxis
                },
                {
                    name:'负收益次数',
                    type:'bar',
                    data:data.nAxis
                },
            ]
        };

        myChart.setOption(option);
    }

    function fillAreaChart(data) {

        var myChart1=echarts.init(document.getElementById('area_chartA'));
        var myChart2=echarts.init(document.getElementById('area_chartB'));


        option1 = {
            tooltip: {
                trigger: 'axis',
            },
            title: {
                left: 'center',
                text: '超额收益-不同计算周期',
            },
//            toolbox: {
//                feature: {
//                    dataZoom: {
//                        yAxisIndex: 'none'
//                    },
//                    restore: {},
//                    saveAsImage: {}
//                }
//            },
            xAxis: {
                type: 'category',
                data: data.excessEarning_x,
            },
            yAxis: {
                type: 'value',
            },
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 100
            }, {
                show:true,
                start: 0,
                end: 100,
            }],
            series: [
                {
                    name:'超额收益',
                    type:'line',
                    smooth:true,
                    symbol: 'none',
                    sampling: 'average',
                    itemStyle: {
                        normal: {
                            color: 'blue'
                        }
                    },
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: 'blue'
                            }, {
                                offset: 1,
                                color: 'blue'
                            }])
                        }
                    },
                    data: data.excessEarning_y,
                }
            ]
        };

        option2 = {
            tooltip: {
                trigger: 'axis',
            },
            title: {
                left: 'center',
                text: '策略胜率%-不同计算周期',
            },
//            toolbox: {
//                feature: {
//                    dataZoom: {
//                        yAxisIndex: 'none'
//                    },
//                    restore: {},
//                    saveAsImage: {}
//                }
//            },
            xAxis: {
                type: 'category',
                data: data.strategyWin_x,
            },
            yAxis: {
                type: 'value',
            },
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 100
            }, {
                show:true,
                start: 0,
                end: 100,
            }],
            series: [
                {
                    name:'策略胜率',
                    type:'line',
                    smooth:true,
                    symbol: 'none',
                    sampling: 'average',
                    itemStyle: {
                        normal: {
                            color: 'blue'
                        }
                    },
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: 'blue'
                            }, {
                                offset: 1,
                                color: 'cornflowerblue'
                            }])
                        }
                    },
                    data: data.strategyWin_y,
                }
            ]
        };

        myChart1.setOption(option1);
        myChart2.setOption(option2);
    }

    function fillTable(data) {
        var tbody=document.getElementById("tbody");
        var length=data.excessEarning_x.length;
        for(var i=0;i<length;i++){
            var tr=document.createElement("tr");
            var td1=document.createElement("td");
            td1.innerHTML=data.excessEarning_x[i];
            var td2=document.createElement("td");
            td2.innerHTML=toPercent(data.excessEarning_y[i]);
            var td3=document.createElement("td");
            td3.innerHTML=toPercent(data.strategyWin_y[i]);
            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tbody.appendChild(tr);

        }
    }


</script>
</body>
</html>

