<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/11
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour</title>
    <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script src="../static/js/jquery-ui.min.js"></script>
    <script src="../static/echarts/echarts.js"></script>
    <script src="../static/js/stlist.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/jquery-ui.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/stock.css"/>

    <script type="text/javascript" language="JavaScript">
        window.onload=function(){
            pageLogCheck();
        };
        $(document).ready(function(){
            fillCharts(dataStr);
        });
        var info={stockname:"<%=request.getAttribute("stockname")%>",startDate:"2018-03-01",endDate:"2018-06-10"};
        //alert(info.stockname);
        var dataStr=${dataStr};


//        getData();

        function doSearch(){
            if($("#startDate").val()=="") {
                alert("起始日期不能为空");
                return false;
            }
            if($("#endDate").val()==""){
                alert("结束日期不能为空");
                return false;
            }
            if($("#newStock").var()==""){
                alert("股票名称不能为空");
                return false;
            }
            var startDate=$("#startDate").val();
            var endDate=$("#endDate").val();
            var newStock=$("#newStock").val();

            info={stockname:newStock,startDate:startDate,endDate:endDate};
            getData();


        }


        function getData(){
            $.ajax({
                url:"/stockFunc/stock",
                data:info,
                dataType:"json",
                success:function(result){
//                alert(result);
                    fillCharts(result);
                }
            });
        }
        function splitData(rawData) {
            var categoryData = [];
            var values = [];
            var volumns = [];
            for (var i = rawData.length-1; i >= 0; i--) {
                categoryData.push(rawData[i].splice(0, 1)[0]);
                values.push(rawData[i]);
                volumns.push(rawData[i][4]);
            }
            return {
                categoryData: categoryData,
                values: values,
                volumns: volumns
            };
        }

        function calculateMA(dayCount, data) {

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
                result.push(+(sum / dayCount).toFixed(3));
            }
            return result;
        }

        function fillCharts (rawData) {
            var myChart = echarts.init(document.getElementById('monthly-k-chart'));

            window.onresize=function(){
                myChart.resize;
            };
            var data = splitData(rawData);

            myChart.setOption(option = {
                backgroundColor: '#eee',
                animation: false,
                legend: {
                    bottom: 10,
                    left: 'center',
                    data: ['K line', 'MA5', 'MA10', 'MA20', 'MA30']
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross'
                    },
                    backgroundColor: 'rgba(245, 245, 245, 0.8)',
                    borderWidth: 1,
                    borderColor: '#ccc',
                    padding: 10,
                    textStyle: {
                        color: '#000'
                    },
//                position: function (pos, params, el, elRect, size) {
//                    var obj = {top: 10};
//                    obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
//                    return obj;
//                },
                    extraCssText: 'width: 170px'
                },
                axisPointer: {
                    link: {xAxisIndex: 'all'},
                    label: {
                        backgroundColor: '#777'
                    }
                },
                toolbox: {
                    feature: {
                        dataZoom: {
                            yAxisIndex: false
                        },
                        brush: {
                            type: ['lineX', 'clear']
                        }
                    }
                },
                brush: {
                    xAxisIndex: 'all',
                    brushLink: 'all',
                    outOfBrush: {
                        colorAlpha: 0.1
                    }
                },
                grid: [
                    {
                        left: '10%',
                        right: '8%',
                        height: '50%'
                    },
                    {
                        left: '10%',
                        right: '8%',
                        top: '63%',
                        height: '16%'
                    }
                ],
                xAxis: [
                    {
                        type: 'category',
                        data: data.categoryData,
                        scale: true,
                        boundaryGap : false,
                        axisLine: {onZero: false},
                        splitLine: {show: false},
                        splitNumber: 20,
                        min: 'dataMin',
                        max: 'dataMax',
                        axisPointer: {
                            z: 100
                        }
                    },
                    {
                        type: 'category',
                        gridIndex: 1,
                        data: data.categoryData,
                        scale: true,
                        boundaryGap : false,
                        axisLine: {onZero: false},
                        axisTick: {show: false},
                        splitLine: {show: false},
                        axisLabel: {show: false},
                        splitNumber: 20,
                        min: 'dataMin',
                        max: 'dataMax',
                        axisPointer: {
                            label: {
                                formatter: function (params) {
                                    var seriesValue = (params.seriesData[0] || {}).value;
                                    return params.value
                                        + (seriesValue != null
                                                ? '\n' + echarts.format.addCommas(seriesValue)
                                                : ''
                                        );
                                }
                            }
                        }
                    }
                ],
                yAxis: [
                    {
                        scale: true,
                        splitArea: {
                            show: true
                        }
                    },
                    {
                        scale: true,
                        gridIndex: 1,
                        splitNumber: 2,
                        axisLabel: {show: false},
                        axisLine: {show: false},
                        axisTick: {show: false},
                        splitLine: {show: false}
                    }
                ],
                dataZoom: [
                    {
                        type: 'inside',
                        xAxisIndex: [0, 1],
                        start: 80,
                        end: 100
                    },
                    {
                        show: true,
                        xAxisIndex: [0, 1],
                        type: 'slider',
                        top: '85%',
                        start: 80,
                        end: 100
                    }
                ],
                series: [
                    {
                        name: 'K line',
                        type: 'candlestick',
                        data: data.values,
                        itemStyle: {
                            normal: {
                                borderColor: null,
                                borderColor0: null
                            }
                        },
                        tooltip: {
                            formatter: function (param) {
                                param = param[0];
                                return [
                                    'Date: ' + param.name + '<hr size=1 style="margin: 3px 0">',
                                    'Open: ' + param.data[0] + '<br/>',
                                    'Close: ' + param.data[1] + '<br/>',
                                    'Lowest: ' + param.data[2] + '<br/>',
                                    'Highest: ' + param.data[3] + '<br/>'
                                ].join('');
                            }
                        }
                    },
                    {
                        name: 'MA5',
                        type: 'line',
                        data: calculateMA(5, data),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'MA10',
                        type: 'line',
                        data: calculateMA(10, data),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'MA20',
                        type: 'line',
                        data: calculateMA(20, data),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'MA30',
                        type: 'line',
                        data: calculateMA(30, data),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'Volumn',
                        type: 'bar',
                        xAxisIndex: 1,
                        yAxisIndex: 1,
                        data: data.volumns
                    }
                ]
            }, true);
            window.onresize = function () {
                //重置容器高宽
                //resizeWorldMapContainer();
                myChart.resize();
            };
            // myChart.on('brushSelected', renderBrushed);

            // function renderBrushed(params) {
            //     var sum = 0;
            //     var min = Infinity;
            //     var max = -Infinity;
            //     var countBySeries = [];
            //     var brushComponent = params.brushComponents[0];

            //     var rawIndices = brushComponent.series[0].rawIndices;
            //     for (var i = 0; i < rawIndices.length; i++) {
            //         var val = data.values[rawIndices[i]][1];
            //         sum += val;
            //         min = Math.min(val, min);
            //         max = Math.max(val, max);
            //     }

            //     panel.innerHTML = [
            //         '<h3>STATISTICS:</h3>',
            //         'SUM of open: ' + (sum / rawIndices.length).toFixed(4) + '<br>',
            //         'MIN of open: ' + min.toFixed(4) + '<br>',
            //         'MAX of open: ' + max.toFixed(4) + '<br>'
            //     ].join(' ');
            // }

//        myChart.dispatchAction({
//            type: 'brush',
//            areas: [
//                {
//                    brushType: 'lineX',
//                    coordRange: ['2013-06-02', '2013-06-20'],
//                    xAxisIndex: 0
//                }
//            ]
//        });
        }
    </script>
</head>
<body>
<div class="header">
    <div class="headerimage" style="">
        <nav>
            <div class="navbar navbar-inverse">
                <div>
                    <div class="navbar-hedaer">
                        <a class="navbar-brand" href="#">AnyQuantour</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li class=""><a href="/index">首页</a></li>
                        <li class=""><a href="/market">市场</a></li>
                        <li class="active"><a href="/stocklist">股票</a></li>
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
        <div class="row">
            <input placeholder="起始日期" class="compare_input property property-white" type="text" id="startDate" required>
            <input placeholder="结束日期" class="compare_input property property-white" type="text" id="endDate" required>
            <input class="compare_input property property-white" type="text" id="newStock" value="${stockname}" required>
            <script type="text/javascript" language="JavaScript">
                $("#startDate").datepicker({
                    dateFormat:"yy-mm-dd",
                });
                $("#endDate").datepicker({
                    dateFormat:"yy-mm-dd",
                });
            </script>
            <button class="compare_button" type="submit" onclick="doSearch()">搜索</button>
        </div>
        <div class="stock-info" data-spm="2">
            <div>
                <div class="row" style="position: relative;">
                    <div class="col-md-12 clu">
                        <div class="stock-info-detail">
                            <span style="font-size: 28px;">${stockname}</span>
                            <span style="font-size: 28px;margin: 0 5px 0 20px; color:${rise?"#f03740":"#009087"}">${recentPrice}</span>
                            <img style="vertical-align: middle" src="../static/images/icon/${rise?"rise":"decrease"}.png"/>
                            <span style="margin: 0 5px 0 5px; color:${rise?"#f03740":"#009087"}; font-size: 16px;"><fmt:formatNumber value="${changeRatio*100}" pattern="#0.00"/>%</span>
                            <button onclick="favorite(this)" class="button" style="background:url('../static/images/icon/star_${favor?"filled":"blank"}.png') no-repeat center center;">
                                <input type="text" name="stockId" style="display: none" value="${stockId}"/>
                                <input type="text" name="favor" style="display: none" value="${favor}"/>
                            </button>
                        </div>
                        <div id="stockInfo_id">${stockId}</div>
                        <div><span style="background-color: #555555;color: white; border-radius: 5px; padding: 2px 4px;">${stockinfo.market}</span></div>

                        <h4>最近交易日情况</h4>
                        <div class="info-wrap">

                            <table>
                                <tr>
                                    <th>开盘价</th>
                                    <th>收盘价</th>
                                    <th>最高价</th>
                                    <th>最低价</th>
                                    <th>复权收盘价</th>
                                    <th>总成交量</th>
                                </tr>
                                <tr>
                                    <td>${stockinfo.open}</td>
                                    <td>${stockinfo.close}</td>
                                    <td>${stockinfo.high}</td>
                                    <td>${stockinfo.low}</td>
                                    <td>${stockinfo.adjClose}</td>
                                    <td>${stockinfo.volome}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="chartpanel">
            <div class="row">
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="charts" id="main-chart" data-spm="2">
                        <div  class="chart-wrapper" style="display:none">
                            <p>日K</p>
                            <div id="daily-k-chart"  style="width: 100%;height: 600px;"></div>
                        </div>
                        <div  class="chart-wrapper" style="display:none">
                            <p>周K</p>
                            <div id="weekly-k-chart"  style="width: 100%;height: 600px;"></div>
                        </div>
                        <div class="chart-wrapper">
                            <div id="monthly-k-chart" style="width: 100%;height: 600px;"></div>
                            <!--<div id="main" style="width: 600px;height:400px;"></div>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="seperator" style="background-color: #3d3d3d"></div>
        <div class="row">
            <h4>近期公告</h4>
            <c:set var="col" value="0"/>
            <c:set var="newsPerCol" value="9"/>
            <c:set var="size" value="${newsList.size()}"/>
            <c:forEach var="nowCol" begin="0" end="${(size-size%newsPerCol)/newsPerCol}">
                <div class="col-md-6">
                    <ul class="news-single">
                        <c:forEach var="i" begin="0" end="${newsPerCol-1}">
                            <c:set var="index"
                                   value="${nowCol*newsPerCol+i}"/>
                            <c:if test="${index<size}">
                                <li>
                                    <%--<a href="${newsList.get(index).url}"><span class="title" title="${newsList.get(index).title}">${newsList.get(index).title}</span><br/><span class="date">${newsList.get(index).time}</span></a>--%>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
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
    // 基于准备好的dom，初始化echarts实例



</script>

</body>
</html>