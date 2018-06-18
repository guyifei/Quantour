<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="zh-cn" id="html">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AnyQuantour</title>

    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="../static/bootstrap/js/bootstrap.min.js"></script>
    <script src="../static/echarts/echarts.js"></script>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <script src="../static/js/stlist.js"></script>
    <script src="../static/js/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/indexStyle.css"/>
    <link rel="stylesheet" href="../static/css/jquery-ui.min.css"/>
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <script type="text/javascript" language="JavaScript">
        window.onload=function(){
            pageLogCheck();
        }
    </script>
    <script type="text/javascript" language="JavaScript">


        function fillCharts (rawData) {
            var myChart = echarts.init(document.getElementById('kchart'));
            var data = splitData(rawData);
            myChart.setOption(option = {
                backgroundColor: '#eee',
                animation: false,
                legend: {
                    bottom: 10,
                    left: 'center',
                    data: ['Dow-Jones index', 'MA5', 'MA10', 'MA20', 'MA30']
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
                        name: 'Dow-Jones index',
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
//            window.onresize = function () {
//                //重置容器高宽
//                //resizeWorldMapContainer();
//                myChart.resize();
//            };
        }
        function splitData(rawData) {
            var categoryData = [];
            var values = [];
            var volumns = [];
            for (var i = 0; i < rawData.length; i++) {
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
        function fetchK(string,id){

            var info={boardname:string};
            //alert(info.stockname);
            var a=document.getElementsByClassName('pi');
            for(var i=0;i<a.length;i++){
                a[i].className='pi';
            }
            document.getElementById(id).className = 'pi active';
            $.ajax({
                url:"/stockFunc/board",
                data:info,
                dataType:"json",
                success:function(result){
                    //alert(result);
                    fillCharts(result);
                }
            });
        }
    </script>
</head>
<body onscroll="checkTop()" id="body">

<div class="header-index">
    <div class="headerimage" style="background-image: url('../static/images/headerbackground/header_background_<%=(int)(Math.random()*4)%>.jpg');">
        <nav>
            <div class="navbar navbar-inverse navbar-fixed-top" id="navbar" style="background-color: transparent;">
                <div>
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#" style="color:white">AnyQuantour</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="">首页</a></li>
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
        <div id="titles" style="top:30%;position:relative;background-color:rgba(255,255,255,0.7);">
            <div class="header-title" id="title1" style="top:33%;"><p class="p1">欢迎使用</p></div>
            <div class="header-title" id="title2" style="top:35%;"><p class="p1">AnyQuantour</p></div>
            <div class="header-title" id="title3" style="top:37%;"><p class="p1">股票查询系统</p></div>
            <div class="header-title" id="title4" style="top:40%;">
                <form class="searchwrap-title" action="/stockFunc/search" >
                    <input oninput="ac(this)" autocomplete="off" class="searchbox-title" type="text" name="key" placeholder="搜索股票">
                    <button class="searchbutton-title" type="submit" id="search_button_header"></button>
                </form>
            </div>
        </div>

    </div>
</div>
<div class="content" id="con">
    <div class="container masking">
        <div class="row">
            <div class="col-md-2 clu">
                <div class="modulelistwrap">
                    <div class="list-group block">
                        <div class="list-group-item table-head columnleft">热门板块</div>
                        <ul id="market-toggle">
                            <li class=""><a href="#industry1" onclick="return false" class="list-group-item columnleft">${marketList.get(0)}</a></li>
                            <li class=""><a href="#industry2" onclick="return false" class="list-group-item columnleft">${marketList.get(1)}</a></li>
                            <li class=""><a href="#industry3" onclick="return false" class="list-group-item columnleft">${marketList.get(2)}</a></li>
                        </ul>
                        <a class="list-group-item" href="/market" style="background-color: #9b9b9b; color:white;">更多</a>
                    </div>

                </div>
            </div>
            <div class="col-md-10 clu">
                <div class="stocklistwrap" align="center">
                    <div id="industry1" class="table-wrap active">
                        <table class="list-group stocktable" align="center" style="width:100%;" >
                        <tr class="list-group-item table-head tline">
                            <th class="column">股票</th>
                            <th class="column">前一交易日涨跌幅</th>
                            <th class="column">前一交易日开盘价</th>
                            <th class="column">前一交易日收盘价</th>
                            <th class="column">加入收藏</th>
                        </tr>
                        <c:forEach var="i" begin="0" end="2">

                            <tr class="list-group-item tline">

                                <td class="column">
                                    <a href="/stock?stockname=${stockList[0].get(i).name}">${stockList[0].get(i).name}<br/>${stockList[0].get(i).code}</a>
                                </td>
                                <td class="column"><fmt:formatNumber value="${(stockList[0].get(i).close-stockList[0].get(i).open)/stockList[0].get(i).open}" pattern="#0.0000"/></td>
                                <td class="column">${stockList[0].get(i).open}</td>
                                <td class="column">${stockList[0].get(i).close}</td>
                                <td class="column">
                                    <button onclick="favorite(this)" class="button" style="background:url('../static/images/icon/star_${favorList[0].get(i)?"filled":"blank"}.png') no-repeat center center;">
                                        <input type="text" name="stockId" style="display: none" value="${stockList[0].get(i).code}"/>
                                        <input type="text" name="favor" style="display: none" value="${favorList[0].get(i)}"/>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table></div>
                    <div id="industry2" class="table-wrap"><table class="list-group stocktable" align="center" style="width:100%;" >
                        <tr class="list-group-item table-head tline">
                            <th class="column">股票</th>
                            <th class="column">前一交易日涨跌幅</th>
                            <th class="column">前一交易日开盘价</th>
                            <th class="column">前一交易日收盘价</th>
                            <th class="column">加入收藏</th>
                        </tr>
                        <c:forEach var="i" begin="0" end="2">
                            <tr class="list-group-item tline">
                                <td class="column">
                                    <a href="/stock?stockname=${stockList[0].get(i).name}">${stockList[0].get(i).name}<br/>${stockList[0].get(i).code}</a>
                                </td>
                                <td class="column"><fmt:formatNumber value="${(stockList[1].get(i).close-stockList[1].get(i).open)/stockList[1].get(i).open}" pattern="#0.0000"/></td>
                                <td class="column">${stockList[1].get(i).open}</td>
                                <td class="column">${stockList[1].get(i).close}</td>
                                <td class="column">
                                    <button onclick="favorite(this)" class="button" style="background:url('../static/images/icon/star_${favorList[1].get(i)?"filled":"blank"}.png') no-repeat center center;">
                                        <input type="text" name="stockId" style="display: none" value="${stockList[1].get(i).code}"/>
                                        <input type="text" name="favor" style="display: none" value="${favorList[1].get(i)}"/>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table></div>
                    <div id="industry3" class="table-wrap"><table class="list-group stocktable" align="center" style="width:100%;" >
                        <tr class="list-group-item table-head tline">
                            <th class="column">股票</th>
                            <th class="column">前一交易日涨跌幅</th>
                            <th class="column">前一交易日开盘价</th>
                            <th class="column">前一交易日收盘价</th>
                            <th class="column">加入收藏</th>
                        </tr>
                        <c:forEach var="i" begin="0" end="2">
                            <tr class="list-group-item tline">
                                <td class="column">
                                    <a href="/stock?stockname=${stockList[0].get(i).name}">${stockList[0].get(i).name}<br/>${stockList[0].get(i).code}</a>
                                </td>
                                <td class="column"><fmt:formatNumber value="${(stockList[2].get(i).close-stockList[2].get(i).open)/stockList[2].get(i).open}" pattern="#0.0000"/></td>
                                <td class="column">${stockList[2].get(i).open}</td>
                                <td class="column">${stockList[2].get(i).close}</td>
                                <td class="column" align="center">
                                    <button onclick="favorite(this)" class="button" style="background:url('../static/images/icon/star_${favorList[2].get(i)?"filled":"blank"}.png') no-repeat center center;">
                                        <input type="text" name="stockId" style="display: none" value="${stockList[2].get(i).code}"/>
                                        <input type="text" name="favor" style="display: none" value="${favorList[2].get(i)}"/>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table></div>
                </div>

            </div>
            <script type="text/javascript" language="JavaScript">
                jQuery("#market-toggle a").mouseenter(function(){
                    jQuery(this).tab("show");
                })
            </script>
        </div>
        <div class="seperator" style="background-color: #5d5d5d;"></div>
        <div class="row">
            <div class="col-md-2 clu">
                <ul class="nav anyeight-nav">
                    <li class="pi active" id="sz"><a onclick="fetchK('上证指数','sz')">上证指数</a></li>
                    <li class="pi" id="a"><a  onclick="fetchK('A股指数','a')">A股指数</a></li>
                    <li class="pi" id="zh"><a  onclick="fetchK('综合指数','zh')">综合指数</a></li>
                </ul>
            </div>
            <div class="col-md-10 clu">
                <div id="kchart" style="height: 450px;width:100%;"></div>
            </div>
        </div>
        <div class="seperator" style="background-color: #5d5d5d;"></div>
        <div class="row">
            <h4>近期新闻</h4>
            <c:set var="col" value="0"/>
            <c:set var="newsPerCol" value="10"/>
            <c:forEach var="nowCol" begin="0" end="${(newsList.size()-newsList.size()%newsPerCol)/newsPerCol}">
                <div class="col-md-6">
                    <ul class="news-single">
                        <c:forEach var="i" begin="0" end="${newsPerCol-1}">
                            <c:set var="index" value="${nowCol*newsPerCol+i}"/>
                            <c:if test="${index<newsList.size()}">
                                <li>
                                    <a href="${newsList.get(index).url}" title="${newsList.get(index).title}"><span class="title">${newsList.get(index).title}</span><br/><span class="date">
                                        <fmt:formatDate value="${newsList.get(index).time}" pattern="yyyy-MM-dd"/>
                                    </span></a>
                                </li>
                            </c:if>

                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
        </div>

    </div>
    <script type="text/javascript" language="JavaScript">
        fetchK("上证指数","sz");
    </script>
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
