
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
    <script src="../static/js/jquery-ui.min.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script src="../static/echarts/echarts.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/jquery-ui.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/ae-animate.css"/>
    <script type="text/javascript">
        fetchLine();
        var pieChart1;
        var pieChart2;
        var pieChart3;
        var myChart;
        function fetchLine() {
            $.ajax({
                url:"/function/hot",
                data:{startDate:"2018-03-01",endDate:"2018-06-10"},
                dataType:"json",
                success:function(result){
                    if(result=="empty"){

                    }else{
                        fillLine(result);
                    }

                }
            })
        }

        function fillLine(data){
            myChart=echarts.init(document.getElementById('linechart'));
            option = {
                tooltip: {
                    trigger: 'axis',
                },
                legend: {
                    data:['涨停数','跌停数'],
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
                    type : 'value',
                },
                series :[
                    {
                        name:'涨停数',
                        type:'line',
                        smooth:true,
                        data:data.hardenStock
                    },
                    {
                        name:'跌停数',
                        type:'line',
                        smooth:true,
                        data:data.dropStock
                    },
                ]
            };
            myChart.setOption(option);

        }
        function fillCharts(result){
            // 基于准备好的dom，初始化echarts实例
            pieChart1 = echarts.init(document.getElementById('ratiochart'));
            pieChart2 = echarts.init(document.getElementById('stopchart'));
            // 绘制图表
            var rad='50%';
            pieChart1.setOption({
                title:{text:'股票涨跌停情况',padding:[0,0,0,0],textStyle:{fontSize:13, fontWeight:'normal'}},
                tooltip: {},
                series:[
                    {
                        type:'pie',
                        radius:50,
                        data:[
                            {value:result.hardenStock,name:'涨停股票数'},
                            {value:result.dropStock,name:'跌停股票数'}
                        ]
                    }
                ]
            });
            pieChart2.setOption({
                title:{text:'股票涨跌情况',padding:[0,0,0,0],textStyle:{fontSize:13, fontWeight:'normal'}},
                tooltip: {},
                series:[
                    {
                        type:'pie',
                        radius:50,
                        data:[
                            {value:result.over5Stock,name:'上涨超5%股票数'},
                            {value:result.down5Stock,name:'下跌超5%股票数'}
                        ]
                    }
                ]
            });

        }
        function fillTable(data) {
            $("#tbody").html("");
            var tbody=document.getElementById("tbody");
            var length=data.length;
            for(var i=0;i<length;i++){
                var tr=document.createElement("tr");
                var td1=document.createElement("td");
                td1.innerHTML=data[i].code;
                var td2=document.createElement("td");
                td2.innerHTML=data[i].name;
                var td3=document.createElement("td");
                td3.innerHTML=data[i].pchange;
                var td4=document.createElement("td");
                td4.innerHTML=data[i].amount;
                var td5=document.createElement("td");
                td5.innerHTML=data[i].buy;
                var td6=document.createElement("td");
                td6.innerHTML=data[i].bratio;
                var td7=document.createElement("td");
                td7.innerHTML=data[i].sell;
                var td8=document.createElement("td");
                td8.innerHTML=data[i].sratio;
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);
                tbody.appendChild(tr);

            }
        }
        function fetchTemp(){
            var data={date:$("#tempDate").val()};
            //alert(data);
            //fillCharts(data);
            $.ajax({
                url:"/function/market",
                data:data,
                dataType:"json",
                success:function(result){
                    if(result=="empty"){
                        var list=document.getElementsByClassName("charts-pie");
                        for(var i=0;i<list.length;i++){
                            list[i].innerHTML="<div align='center' style='padding-top:35%;'>非交易日或无当日数据</div>";
                        }
                    }else if(result.totalVolume<=0){
                        var list=document.getElementsByClassName("charts-pie");
                        for(var i=0;i<list.length;i++){
                            list[i].innerHTML="<div align='center' style='padding-top:35%;'>非交易日或无当日数据</div>";
                        }
                    }else{
                        fillCharts(result);
                    }

                }
            })
            $.ajax({
                url:"/function/rank",
                data:data,
                dataType:"json",
                success:function(result){
                    if(result=="empty"){
                    } else{
                        fillTable(result);
                    }
                }
            })
        }
        window.onload=function(){
            pageLogCheck();
        };
        window.onresize=function(){
            pieChart1.resize();
            pieChart2.resize();
            pieChart3.resize();
            myChart.resize();
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
                        <li class="active"><a href="">市场</a></li>
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
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h4 class="title">市场温度计</h4>
                    <div class="seperator" style="background-color:black"></div>

                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <span style="display: inline-block" class="property">时间选择</span>
                    <span><input class="property" id="tempDate" type="text" name="date" title="date" onchange="fetchTemp()" formaction=""></span>
                </div>
                <script type="text/javascript" language="JavaScript">
                    $("#tempDate").datepicker({
                        dateFormat:"yy-mm-dd",
                    })
                    $("#tempDate").attr("value",new Date().toLocaleDateString());
                    fetchTemp();
                </script>
                <div class="col-md-8"></div>
            </div>
            <div class="row" style="margin-top: 20px;">
                <div class="col-md-6" align="center">
                    <div class="charts-pie" id="ratiochart" ></div>

                </div>
                <div class="col-md-6" align="center">
                    <div class="charts-pie" id="stopchart"></div>

                </div>
                    <%--<div class="col-md-4" align="center">--%>
                        <%--<div class="charts-pie" id="changechart"></div>--%>

                    <%--</div>--%>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h4 class="title">市场热度历史总览</h4>
                    <div class="seperator" style="background-color:black"></div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div id="linechart" style="width:100%;height: 400px"></div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h4 class="title">热门股票</h4>
                    <div class="seperator" style="background-color:black"></div>

                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>股票代码</th>
                            <th>股票名称</th>
                            <th>当日涨跌幅</th>
                            <th>成交额(万)</th>
                            <th>买入额(万)</th>
                            <th>买入占总成交比例</th>
                            <th>卖出额(万)</th>
                            <th>卖出占总成交比例</th>
                        </tr>
                        </thead>
                        <tbody id="tbody">
                        </tbody>
                    </table>
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
