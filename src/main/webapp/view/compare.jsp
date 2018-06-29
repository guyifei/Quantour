<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/16
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour-股票比较</title>
    <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script src="../static/js/stlist.js"></script>
    <script src="../static/echarts/echarts.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/jquery-ui.min.css"/>
    <link rel="stylesheet" href="../static/css/stock.css"/>
    <script type="text/javascript">
        window.onload=function(){
            pageLogCheck();
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
                        <li class="active"><a href="#">比较</a></li>
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
        <div class="row compare_wrap">
            <form action="" class="col-md-12" style="display: inline" target="thisform">
                <iframe name="thisform" style="display: none"></iframe>
                <input class="compare_input property property-white" type="text" id="stockA" placeholder="输入股票名称/代码" required oninput="ac(this)">
                <input class="compare_input property property-white" type="text" id="stockB" placeholder="输入股票名称/代码" required oninput="ac(this)">
                <script type="text/javascript" language="JavaScript">
                    fetchNameList();
                </script>
                <%--<input class="compare_input property property-white" type="text" id="startDate" placeholder="开始日期">--%>
                <%--<input class="compare_input property property-white" type="text" id="endDate" placeholder="结束日期">--%>
                <input type="date" id="startDate">
                <input type="date" id="endDate">
                <script type="text/javascript" language="JavaScript">
                    $("#startDate").datepicker({
                        dateFormat:"yy-mm-dd",
                    });
                    $("#endDate").datepicker({
                        dateFormat:"yy-mm-dd",
                    });
                </script>
                <button class="compare_button" type="submit" id="compare_button" onclick="doCompare()">比较</button>
                <div><label id="tip" style="margin-top: 20px;color: red"></label></div>
            </form>
        </div>
        <div class="seperator" style="background-color: #1d3c54"></div>
        <div class="row">
            <div class="col-md-8">
                <div id="main" class="compare_leftbox"></div>
            </div>
            <div class="col-md-4" style="">
                <div class="" style="height: 400px; ">
                    <h3>股票信息</h3>
                    <h4 id="stockNameA"></h4>
                    <h5 id="stockIDA"></h5>
                    <div class="seperator" style="background-color: lightgrey"></div>
                    <h4>对数收益率方差</h4>
                    <h5 id="logarithmicA"></h5>
                    <h4>较昨日涨跌幅</h4>
                    <h5> </h5>
                    <h4>二十日均价</h4>
                    <h5> </h5>
                    <h4>二十日均量</h4>
                    <h5> </h5>
                </div>
                <div class="seperator" style="background-color: grey;width: 100%;"></div>
                <div class="" style="height: 400px;">
                    <h3>股票信息</h3>
                    <h4 id="stockNameB">  </h4>
                    <h5 id="stockIDB">  </h5>
                    <div class="seperator" style="background-color: lightgrey;"></div>
                    <h4>对数收益率方差</h4>
                    <h5 id="logarithmicB"></h5>
                    <h4>较昨日涨跌幅</h4>
                    <h5> </h5>
                    <h4>二十日均价</h4>
                    <h5> </h5>
                    <h4>二十日均量</h4>
                    <h5> </h5>
                </div>
            </div>
        </div>
        <div class="row">
            <%--<div class="seperator" style="background-color:black"></div>--%>

                <div id="log" style="width: 100%; height: 400px;"></div>


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

    $(document).ready(function(){
        stockA = document.getElementById("stockA");
        stockA.value = "000043/中航地产"
        stockB = document.getElementById("stockB");
        stockB.value = "000050/深天马Ａ"
        startDate = document.getElementById("startDate");
        startDate.value = "2018/03/03"
        endDate = document.getElementById("endDate");
        endDate.value = "2018/06/09"
        doCompare()
    });

    var myChart = echarts.init(document.getElementById('main'));
    var myLog=echarts.init(document.getElementById('log'));
    window.onresize=function(){
        myChart.resize();
        myLog.resize();
    }
    option = {
        title: {
            text: '股票比较图',
            x: 'left'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data:['日K', 'MA5', 'MA10', 'MA20', 'MA30'],
            x: 'center'
        },
        toolbox: {
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                }
            }
        },
        axisPointer: {
            link: {xAxisIndex: 'all'}
        },
        dataZoom: [
            {
                type: 'inside',
                start: 80,
                end: 100,
                xAxisIndex: [0, 1]
            },
            {
                show: true,
                type: 'slider',
                start: 80,
                end: 100,
                xAxisIndex: [0, 1]
            }
        ],
        grid: [{
            left: 50,
            right: 50,
            height: '35%'
        }, {
            left: 50,
            right: 50,
            top: '55%',
            height: '35%'
        }],
        xAxis : [
            {
                type: 'category',
                data: [],
                scale: true,
                boundaryGap : false,
                axisLine: {onZero: false},
                splitLine: {show: false},
                splitNumber: 20,
                min: 'dataMin',
                max: 'dataMax'
            },
            {
                gridIndex: 1,
                type: 'category',
                data: [],
                scale: true,
                boundaryGap : false,
                axisLine: {onZero: false},
                splitLine: {show: false},
                splitNumber: 20,
                min: 'dataMin',
                max: 'dataMax',
//                position: 'top'
            }
        ],
        yAxis : [
            {
                scale: true,
                splitArea: {
                    show: true
                }
            },
            {
                gridIndex: 1,
                scale: true,
                splitArea: {
                    show: true
                },
//                inverse: true
            }
        ],
        series : [
            {
                name: '日K',
                type: 'candlestick',
                data: []
            },
            {
                name: 'MA5',
                type: 'line',
                data: [],
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA10',
                type: 'line',
                data: [],
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA20',
                type: 'line',
                data: [],
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA30',
                type: 'line',
                data: [],
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: '日K',
                type: 'candlestick',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data: []
            },
            {
                name: 'MA5',
                type: 'line',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data: [],
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA10',
                type: 'line',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data: [],
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA20',
                type: 'line',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data: [],
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA30',
                type: 'line',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data: [],
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            }
        ]
    };
    myChart.setOption(option);


    var dataA=[];
    var dataB=[];

    var infoA=[];
    var infoB=[];

    function splitData(rawData) {
        var categoryData = [];
        var values = []
        for (var i = rawData.length-1; i >=0; i--) {
            categoryData.push(rawData[i].splice(0, 1)[0]);
            values.push(rawData[i]);
        }
        return {
            categoryData: categoryData,
            values: values
        };
    }

    function doCompare(){
        var stockAName=$("#stockA").val();
        var stockBName=$("#stockB").val();
        var startDate=$("#startDate").val();
        var endDate=$("#endDate").val();

        if(startDate == ""||endDate==""||stockAName==""||stockBName==""){
            $("#tip").text("请完成输入");
            return;
        }
        myChart.showLoading();
        $("#tip").text("");
        $.ajax({
            url:"/stockFunc/compare",
            data:{stockname:stockAName,startDate:startDate,endDate:endDate},
            dataType:"json",
            async: false,
            success:function(result){
//                alert(result);
                dataA=splitData(result);
            }
        });
        $.ajax({
            url:"/stockFunc/compare",
            data:{stockname:stockBName,startDate:startDate,endDate:endDate},
            dataType:"json",
            async: false,
            success:function(result){
                dataB=splitData(result);
            }
        });
        $.ajax({
            url:"/stockFunc/compareInfo",
            data:{stockname:stockAName,startDate:startDate,endDate:endDate},
            dataType:"json",
            async: false,
            success:function(result){
                infoA=result;
            }
        });
        $.ajax({
            url:"/stockFunc/compareInfo",
            data:{stockname:stockBName,startDate:startDate,endDate:endDate},
            dataType:"json",
            async: false,
            success:function(result){
                infoB=result;
            }
        });


        fillCharts();
        fillInfo();
        fillLog();

    }



    function calculateMA(dayCount,data) {
        var result = [];
        for (var i = 0, len = data.values.length; i < len; i++) {
            if (i < dayCount) {
                result.push('-');
                continue;
            }
            var sum = 0;
            for (var j = 0; j < dayCount; j++) {
                sum += data.values[i - j][1];
            }
            result.push(sum / dayCount);
        }
        return result;
    }
    function fillInfo() {


        document.getElementById("stockNameA").innerHTML=infoA.name;
        document.getElementById("stockIDA").innerHTML=infoA.stockID;
        document.getElementById("logarithmicA").innerHTML=infoA.logarithmicYieldVariance;
        document.getElementById("stockNameB").innerHTML=infoB.name;
        document.getElementById("stockIDB").innerHTML=infoB.stockID;
        document.getElementById("logarithmicB").innerHTML=infoB.logarithmicYieldVariance;

    }

    function fillCharts () {

        window.onresize=function(){
            myChart.resize();
        }
        option = {
            title: {
                text: '股票比较图',
                x: 'left'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    animation: false
                }
            },
            legend: {
                data:['日K', 'MA5', 'MA10', 'MA20', 'MA30'],
                x: 'center'
            },
            toolbox: {
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    }
                }
            },
            axisPointer: {
                link: {xAxisIndex: 'all'}
            },
            dataZoom: [
                {
                    type: 'inside',
                    start: 80,
                    end: 100,
                    xAxisIndex: [0, 1]
                },
                {
                    show: true,
                    type: 'slider',
                    start: 80,
                    end: 100,
                    xAxisIndex: [0, 1]
                }
            ],
            grid: [{
                left: 50,
                right: 50,
                height: '35%'
            }, {
                left: 50,
                right: 50,
                top: '55%',
                height: '35%'
            }],
            xAxis : [
                {
                    type: 'category',
                    data: dataA.categoryData,
                    scale: true,
                    boundaryGap : false,
                    axisLine: {onZero: false},
                    splitLine: {show: false},
                    splitNumber: 20,
                    min: 'dataMin',
                    max: 'dataMax'
                },
                {
                    gridIndex: 1,
                    type: 'category',
                    data: dataB.categoryData,
                    scale: true,
                    boundaryGap : false,
                    axisLine: {onZero: false},
                    splitLine: {show: false},
                    splitNumber: 20,
                    min: 'dataMin',
                    max: 'dataMax',
//                position: 'top'
                }
            ],
            yAxis : [
                {
                    scale: true,
                    splitArea: {
                        show: true
                    }
                },
                {
                    gridIndex: 1,
                    scale: true,
                    splitArea: {
                        show: true
                    },
//                inverse: true
                }
            ],
            series : [
                {
                    name: '日K',
                    type: 'candlestick',
                    data: dataA.values
                },
                {
                    name: 'MA5',
                    type: 'line',
                    data: calculateMA(5,dataA),
                    smooth: true,
                    lineStyle: {
                        normal: {opacity: 0.5}
                    }
                },
                {
                    name: 'MA10',
                    type: 'line',
                    data: calculateMA(10,dataA),
                    smooth: true,
                    lineStyle: {
                        normal: {opacity: 0.5}
                    }
                },
                {
                    name: 'MA20',
                    type: 'line',
                    data: calculateMA(20,dataA),
                    smooth: true,
                    lineStyle: {
                        normal: {opacity: 0.5}
                    }
                },
                {
                    name: 'MA30',
                    type: 'line',
                    data: calculateMA(30,dataA),
                    smooth: true,
                    lineStyle: {
                        normal: {opacity: 0.5}
                    }
                },
                {
                    name: '日K',
                    type: 'candlestick',
                    xAxisIndex: 1,
                    yAxisIndex: 1,
                    data: dataB.values
                },
                {
                    name: 'MA5',
                    type: 'line',
                    xAxisIndex: 1,
                    yAxisIndex: 1,
                    data: calculateMA(5,dataB),
                    smooth: true,
                    lineStyle: {
                        normal: {opacity: 0.5}
                    }
                },
                {
                    name: 'MA10',
                    type: 'line',
                    xAxisIndex: 1,
                    yAxisIndex: 1,
                    data: calculateMA(10,dataB),
                    smooth: true,
                    lineStyle: {
                        normal: {opacity: 0.5}
                    }
                },
                {
                    name: 'MA20',
                    type: 'line',
                    xAxisIndex: 1,
                    yAxisIndex: 1,
                    data: calculateMA(20,dataB),
                    smooth: true,
                    lineStyle: {
                        normal: {opacity: 0.5}
                    }
                },
                {
                    name: 'MA30',
                    type: 'line',
                    xAxisIndex: 1,
                    yAxisIndex: 1,
                    data: calculateMA(30,dataB),
                    smooth: true,
                    lineStyle: {
                        normal: {opacity: 0.5}
                    }
                }
            ]
        };

        myChart.hideLoading();
        myChart.setOption(option);

    }

    function fillLog(){
//        document.getElementById("logPanel").style="display:";

        window.onresize=function(){
            myLog.resize();
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
                    start: 80,
                    end: 100
                },
                {
                    type: 'inside',
                    start: 80,
                    end: 100
                }
            ],
            xAxis : {
                type : 'category',
                data: dataA.categoryData,
            },

            yAxis : {
                name : '对数收益率',
                type : 'value',
            },
            series :[
                {
                    name:infoA.name,
                    type:'line',
                    smooth:true,
                    data:infoA.logarithmicYield
                },
                {
                    name:infoB.name,
                    type:'line',
                    smooth:true,
                    data:infoB.logarithmicYield
                },
            ]
        };
        myLog.setOption(option);
    }

</script>

</body>
</html>