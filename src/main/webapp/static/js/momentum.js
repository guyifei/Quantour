/**
 * Created by Administrator on 2017/6/14.
 */

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
    $("#inputPanel").animate({marginTop:'60px'});
    document.getElementById("platePanel").style="display:none";
    document.getElementById("treePanel").style="display:none";
}

function showPlate(){
    $("#inputPanel").animate({marginTop:'0px'});
    document.getElementById("platePanel").style="display:";
    document.getElementById("treePanel").style="display:none";

}

function mainPlate(){
    document.getElementById("plateChecked").value=1;
    $("#main-plate").addClass("active");
    $("#medium-plate").removeClass("active");
    $("#enterprise-plate").removeClass("active");
}
function mediumPlate(){
    document.getElementById("plateChecked").value=2;
    $("#main-plate").removeClass("active");
    $("#medium-plate").addClass("active");
    $("#enterprise-plate").removeClass("active");
}
function enterprisePlate(){
    document.getElementById("plateChecked").value=3;
    $("#main-plate").removeClass("active");
    $("#medium-plate").removeClass("active");
    $("#enterprise-plate").addClass("active");
}

function showList(){
    $("#inputPanel").animate({marginTop:'0px'});
    document.getElementById("treePanel").style="display:";
    document.getElementById("platePanel").style="display:none";
}

function toMomentum() {
    document.getElementById("strategyNumber").value=1;
    document.getElementById("momentumTab").className="active";
    document.getElementById("meanTab").className="";
    document.getElementById("momTitle").style="color: #2aabd2; margin-bottom: 20px";
    document.getElementById("meanTitle").style="color: #2aabd2; margin-bottom: 20px;display: none;";
}

function toMean() {
    document.getElementById("strategyNumber").value=2;
    document.getElementById("momentumTab").className="";
    document.getElementById("meanTab").className="active";
    document.getElementById("momTitle").style="color: #2aabd2; margin-bottom: 20px;display:none";
    document.getElementById("meanTitle").style="color: #2aabd2; margin-bottom: 20px;";
}

function toForm() {
    document.getElementById("relationNumber").value=1;
    document.getElementById("formTab").className="active";
    document.getElementById("holdTab").className="";
    document.getElementById("relationForm").style="margin-top: 10px;display:";
    document.getElementById("relationHold").style="margin-top: 10px;display:none";
}

function toHold() {
    document.getElementById("relationNumber").value=2;
    document.getElementById("formTab").className="";
    document.getElementById("holdTab").className="active";
    document.getElementById("relationForm").style="margin-top: 10px;display:none";
    document.getElementById("relationHold").style="margin-top: 10px;display:";
}

function doCompute() {
    var nodes=getCheckedNodes();
    document.getElementById("nodesChecked").value=nodes;

}

function toValid(){
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    var form = document.getElementById("form").value;
    var hold = document.getElementById("hold").value;
    var relationForm = document.getElementsByName("relationForm")[0].value;
    var relationHold = document.getElementsByName("relationHold")[0].value;

    if(startDate == ""||endDate==""||form==""||hold==""){
        $("#tip").text("请完成输入");
        return false;
    }
    if(relationForm==""&&relationHold==""){
        $("#tip").text("请完成输入");
        return false;
    }
    return true;
}