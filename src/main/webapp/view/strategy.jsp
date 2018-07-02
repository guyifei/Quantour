<!--作为模板使用-->
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
    <script type="text/javascript" src="../static/js/basement.js"></script>
    <script src="../static/js/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../static/treeview/css/bootstrap-treeview.css"/>
    <link rel="stylesheet" href="../static/css/base-style.css"/>
    <link rel="stylesheet" href="../static/css/inputStyle.css"/>
    <link rel="stylesheet" href="../static/css/jquery-ui.min.css"/>
    <link rel="stylesheet" href="../static/css/strategy.css"/>
    <link rel="stylesheet" href="../static/css/computeStyle.css"/>
    <script type="text/javascript" language="JavaScript">
        window.onload=function(){
            pageLogCheck();
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
                            <a href="" class="dropdown-toggle" data-toggle="dropdown">回测
                                <%--<b class="caret"></b>--%>
                            </a>
                            <ul class="dropdown-menu" style="min-width: 120px">
                                <li><a href="/momentum">动量策略</a></li>
                                <li><a href="/strategy">自定义策略</a></li>
                            </ul>
                        </li>
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
        <form action="/strategyCompute" method="post" onsubmit="return toValid()">
            <div class="row">
            <div class="col-md-12">
                <h3>选择股票池</h3>
                <div>
                    <div class="radio-inline">
                        <label>
                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="1" onclick="noneDisplay()"checked> 大盘
                        </label>
                    </div>
                    <div class="radio-inline">
                        <label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="2" onclick="showPlate()">板块

                        </label>
                    </div>
                    <%--<div class="radio-inline">--%>
                        <%--<label>--%>
                            <%--<input type="radio" name="optionsRadios" id="optionsRadios3" value="3" onclick="showList()">自选股票--%>
                        <%--</label>--%>
                    <%--</div>--%>
                    <span id="chosenPlate"></span>
                </div>
            </div>
        </div>
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
            <div class="row">
                <div class="seperator" style="background-color:black"></div>
                <td class="col-md-8">
                    <ul class="nav nav-tabs aw-nav-tabs">
                        <li id="filterTab" class="active" onclick="toFilter()"><a href="#">筛选条件</a></li>
                        <li id="rankTab" onclick="toRank()"><a href="#">排名条件</a></li>
                    </ul>
                    <table id="filterPanel" class="active" cellspacing="5">
                        <tr class="rank-header">
                            <th class="property condition">指标</th>
                            <th class="property condition">条件</th>
                            <th class="property condition">值</th>
                            <th class="property condition">选择</th>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">开盘价</span>
                            </td>
                            <td class="condition">
                                <select name="filterSelect" class="form-control">
                                    <option value="gt" selected="selected">大于</option>
                                    <option value="des">小于</option>
                                </select>
                            </td>
                            <td class="condition">

                                <input type="text" class="input_value" name="filterValue">

                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="fi1" type="checkbox" name="filterItems" value="1">
                                    <label for="fi1"></label>
                                </div>
                            </td>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">收盘价</span>
                            </td>
                            <td class="condition">
                                <select name="filterSelect" class="form-control">
                                    <option value="gt" selected="selected">大于</option>
                                    <option value="lt">小于</option>
                                </select>
                            </td>
                            <td class="condition">

                                <input type="text" class="input_value" name="filterValue">

                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="fi2" type="checkbox" name="filterItems" value="2">
                                    <label for="fi2"></label>
                                </div>
                            </td>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">最高价</span>
                            </td>
                            <td class="condition">
                                <select name="filterSelect" class="form-control">
                                    <option value="gt" selected="selected">大于</option>
                                    <option value="lt">小于</option>
                                </select>
                            </td>
                            <td class="condition">

                                <input type="text" class="input_value" name="filterValue">

                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="fi3" type="checkbox" name="filterItems" value="3">
                                    <label for="fi3"></label>
                                </div>

                            </td>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">复权收盘价</span>
                            </td>
                            <td class="condition">
                                <select name="filterSelect" class="form-control">
                                    <option value="gt" selected="selected">大于</option>
                                    <option value="lt">小于</option>
                                </select>
                            </td>
                            <td class="condition">

                                <input type="text" class="input_value" name="filterValue">

                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="fi4" type="checkbox" name="filterItems" value="4">
                                    <label for="fi4"></label>
                                </div>

                                <%--<input type="checkbox" name="filterItems" value="4"> 选择--%>

                            </td>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">成交量</span>
                            </td>
                            <td class="condition">
                                <select name="filterSelect" class="form-control">
                                    <option value="gt" selected="selected">大于</option>
                                    <option value="lt">小于</option>
                                </select>
                            </td>
                            <td class="condition">

                                <input type="text" class="input_value" name="filterValue">

                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="fi5" type="checkbox" name="filterItems" value="5">
                                    <label for="fi5"></label>
                                </div>
                                <%--<input type="checkbox" name="filterItems" value="5"> 选择--%>

                            </td>
                        </tr>
                    </table>
                    <table id="rankPanel" style="">
                        <tr class="rank-header">
                            <th class="property condition">指标</th>
                            <th class="property condition">次序</th>
                            <th class="property condition">选择</th>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">开盘价</span>
                            </td>
                            <td class="condition">
                                <select name="rankSelect" class="form-control">
                                    <option value="asc" selected="selected">从小到大</option>
                                    <option value="des">从大到小</option>
                                </select>
                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="ri1" type="checkbox" name="rankItems" value="1">
                                    <label for="ri1"></label>
                                </div>
                            </td>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">收盘价</span>
                            </td>
                            <td class="condition">
                                <select name="rankSelect" class="form-control">
                                    <option value="asc" selected="selected">从小到大</option>
                                    <option value="des">从大到小</option>
                                </select>
                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="ri2" type="checkbox" name="rankItems" value="2">
                                    <label for="ri2"></label>
                                </div>
                            </td>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">最高价</span>
                            </td>
                            <td class="condition">
                                <select name="rankSelect" class="form-control">
                                    <option value="asc" selected="selected">从小到大</option>
                                    <option value="des">从大到小</option>
                                </select>
                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="ri3" type="checkbox" name="rankItems" value="3">
                                    <label for="ri3"></label>
                                </div>
                            </td>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">复权收盘价</span>
                            </td>
                            <td class="condition">
                                <select name="rankSelect" class="form-control">
                                    <option value="asc" selected="selected">从小到大</option>
                                    <option value="des">从大到小</option>
                                </select>
                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="ri4" type="checkbox" name="rankItems" value="4">
                                    <label for="ri4"></label>
                                </div>
                            </td>
                        </tr>
                        <tr class="condition-item" data-field="pb">
                            <td class="condition">
                                <span class="condition">成交量</span>
                            </td>
                            <td class="condition">
                                <select name="rankSelect" class="form-control">
                                    <option value="asc" selected="selected">从小到大</option>
                                    <option value="des">从大到小</option>
                                </select>
                            </td>
                            <td class="condition" align="right">

                                <div class="input-checkbox-wrap" >
                                    <input id="ri5" type="checkbox" name="rankItems" value="5">
                                    <label for="ri5"></label>
                                </div>
                            </td>
                        </tr>
                    </table>


                </td>
            </div>
        <div class="row">
            <div class="col-md-4">
                <div style="margin-top: 20px; margin-bottom: 10px;">
                    <span >开始日期</span>
                    <span><input class="property" id="startDate"  required type="text" name="startDate"></span>
                </div>
                <div style="margin-top: 10px; margin-bottom: 10px;">
                    <span >结束日期</span>
                    <span><input class="property" id="endDate"  required type="text" name="endDate"></span>
                </div>
                <script type="text/javascript" language="JavaScript">
                    $("#startDate").datepicker({
                        dateFormat:"yy-mm-dd"
                    })
                    $("#endDate").datepicker({
                        dateFormat:"yy-mm-dd"
                    })
                </script>


                <div><label id="tip" style="margin-top:10px;margin-left: 20px;color: red"></label></div>
                <div style="display: none">
                    <input type="text" value="0" name="plateNumber" id="plateChecked">
                    <input type="text" value="0" name="nodes"  id="nodesChecked">
                    <input type="text" name="filterNames" id="filterNames">
                    <input type="text" name="filterSelects" id="filterSelects">
                    <input type="text" name="filterValues" id="filterValues">
                    <input type="text" name="rankNames" id="rankNames">
                    <input type="text" name="rankSelects" id="rankSelects">
                </div>
            </div>
            <div class="col-md-4">
                <div style="margin-top: 20px; margin-bottom: 10px;">
                    <span>形成日期</span>
                    <span><input class="property" id="form" type="text" name="form"></span>
                </div>
                <div style="margin-top: 10px; margin-bottom: 10px;">
                    <span >持有天数</span>
                    <span><input class="property" id="hold" type="text" name="hold"></span>
                </div>
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-primary" style="float: left;margin-top: 60px;" onclick="docompute()">下一步</button>
            </div>
        </div>
        </form>

        <%--<div class="row" id="platePanel" style="display: none">--%>
            <%--<div class="seperator" style="background-color:black"></div>--%>
            <%--<div class="col-md-4">--%>
                <%--<div class="thumbnail">--%>
                    <%--<img src="../static/images/icon/plate.png">--%>
                    <%--<div class="caption">--%>
                        <%--<p style="text-align: center">--%>
                            <%--<a href="#" class="btn btn-primary" role="button" onclick="mainPlate()">主板</a>--%>
                        <%--</p>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="col-md-4">--%>
                <%--<div class="thumbnail">--%>
                    <%--<img src="../static/images/icon/plate.png">--%>
                    <%--<div class="caption">--%>
                        <%--<p style="text-align: center">--%>
                            <%--<a href="#" class="btn btn-primary" role="button" onclick="mediumPlate()">中小板</a>--%>
                        <%--</p>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="col-md-4">--%>
                <%--<div class="thumbnail">--%>
                    <%--<img src="../static/images/icon/plate.png">--%>
                    <%--<div class="caption">--%>
                        <%--<p style="text-align: center">--%>
                            <%--<a href="#" class="btn btn-primary" role="button" onclick="enterprisePlate()">创业板</a>--%>
                        <%--</p>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

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
            <div class="clu">
                <p style="font-size: 16px;margin-top: 10px">由AnyEight小组制作 联系我们:943045598@qq.com 814335296@qq.com 1226550411@qq.com</p>
                <p style="font-size: 16px;">此网站为课程学习项目, 不允许用于商业用途.</p>
                <p style="font-size: 16px;">NJU Software Institute 2017.5</p>
            </div>
        </div>
    </div>
</footer>

<script type="text/javascript" language="JavaScript">
    $.ajax({
        url:"/computeFunc/tree",
        data:{},
        dataType:"json",
//        async: false,
        success:function(result){
            document.getElementById("loadingMsg").style="display: none";
            fillTree(result);
        }
    });
    function fillTree(tree) {
        $('#tree').treeview({
            data: tree,         // data is not optional
            multiSelect: true,
            hierarchicalCheck:true,
            propagateCheckEven:true,
            showCheckbox:true,
//            checkboxFirst:true,
            selectedBackColor:null,
            selectedColor:null,
            levels:1,


        });
    }
    function getCheckedNodes() {
        var checkedNodes=$('#tree').treeview('getChecked' );
        var nodes=[];
        for(var i=0;i<checkedNodes.length;i++){
            if(checkedNodes[i].nodes==null){
                nodes.push(checkedNodes[i].text);
            }

        }
        return nodes;
    }
    function noneDisplay(){
        $("#chosenPlate").text("");
        document.getElementById("platePanel").style="display:none";
        document.getElementById("treePanel").style="display:none";
    }

    function showPlate(){

        document.getElementById("platePanel").style="display:";
        document.getElementById("treePanel").style="display:none";

    }
    function showList(){
        $("#chosenPlate").text("");
        document.getElementById("treePanel").style="display:";
        document.getElementById("platePanel").style="display:none";
    }

    function mainPlate(){
        $("#chosenPlate").text("当前选择: 主板");
        document.getElementById("plateChecked").value=1;
        document.getElementById("platePanel").style="display:none";
    }
    function mediumPlate(){
        $("#chosenPlate").text("当前选择: 中小板");
        document.getElementById("plateChecked").value=2;
        document.getElementById("platePanel").style="display:none";
    }
    function enterprisePlate(){
        $("#chosenPlate").text("当前选择: 创业板");
        document.getElementById("plateChecked").value=3;
        document.getElementById("platePanel").style="display:none";
    }
    function toFilter() {
        document.getElementById("filterTab").className="active";
        document.getElementById("rankTab").className="";
        document.getElementById("filterPanel").className="active";
        document.getElementById("rankPanel").className="";
    }

    function toRank() {
        document.getElementById("filterTab").className="";
        document.getElementById("rankTab").className="active";
        document.getElementById("filterPanel").className="";
        document.getElementById("rankPanel").className="active";
    }

    function filterCheck(){
        var arr = new Array();
        var items = document.getElementsByName("filterItems");
        for (var i = 0; i < items.length; i++) {
            if (items[i].checked) {
                arr.push(items[i].value);
            }
        }
        alert("选择的个数为：" + arr.length);
    }
    
    function rankCheck() {
        var arr = new Array();
        var items = document.getElementsByName("rankItems");
        for (var i = 0; i < items.length; i++) {
            if (items[i].checked) {
                arr.push(items[i].value);
            }
        }
        alert("选择的个数为：" + arr.length);
    }

    function docompute() {
        var nodes=getCheckedNodes();
        document.getElementById("nodesChecked").value=nodes;


        var filterNames = new Array();
        var filterSelects=new Array();
        var filterValues=new Array();
        var filterItems = document.getElementsByName("filterItems");
        var filterSelect=document.getElementsByName("filterSelect");
        var filterValue=document.getElementsByName("filterValue");
        for (var i = 0; i < filterItems.length; i++) {
            if (filterItems[i].checked) {
                filterNames.push(i);
                filterSelects.push(filterSelect[i].value);
                filterValues.push(filterValue[i].value);
            }
        }
        document.getElementById("filterNames").value=filterNames;
        document.getElementById("filterSelects").value=filterSelects;
        document.getElementById("filterValues").value=filterValues;


        var rankNames=new Array();
        var rankSelects=new Array();
        var rankItems = document.getElementsByName("rankItems");
        var rankSelect=document.getElementsByName("rankSelect");
        var rankValue=document.getElementsByName("rankValue");
        for (var i = 0; i < rankItems.length; i++) {
            if (rankItems[i].checked) {
                rankNames.push(i);
                rankSelects.push(rankSelect[i].value);
            }
        }
        document.getElementById("rankNames").value=rankNames;
        document.getElementById("rankSelects").value=rankSelects;
    }

    function toValid(){
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        var form = document.getElementById("form").value;
        var hold = document.getElementById("hold").value;
        var rankNames=document.getElementsByName("rankNames").value;
        var filterNames=document.getElementsByName("filterNames").value;

        if(startDate == ""||endDate==""||form==""||hold==""){
            $("#tip").text("请完成输入");
            return false;
        }
        return true;
    }

</script>
</body>
</html>


