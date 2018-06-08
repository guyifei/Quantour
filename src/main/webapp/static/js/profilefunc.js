/**
 * Created by 35930 on 2017/6/2.
 */
$(document).ready(function(){
   $("#navpill a").click(function(){
       $(this).tab("show");
       return false;
   })
});

//static区域
{
    var tel;
    var email;
}
function init(str){
    $("#input-id").text(str.user_id);
    $("#input-us").attr("value",str.userName);
    $("#input-tel").attr("value",str.telephone);
    $("#input-email").attr("value",str.email);
    if(str.graph!=null){
        $("#profile-avatar").attr("src", str.graph);
    }
}


function startEdit(){
    var con=document.getElementById("button-confirm");
    con.className="active";
    con.onclick=confirmEdit;

    var can=document.getElementById("button-cancel");
    can.style.backgroundImage= "url('../static/images/icon/cancel.png')";
    can.onclick=cancelEdit;

    var edit=document.getElementById("button-changepw");
    edit.className="";

    tel=document.getElementById("input-tel").value;
    email=document.getElementById("input-email").value;
    document.getElementById("input-tel").removeAttribute("disabled");
    document.getElementById("input-email").removeAttribute("disabled")
}
function cancelEdit(){
    var con=document.getElementById("button-confirm");
    con.className="";

    var can=document.getElementById("button-cancel");
    can.style.backgroundImage= "url('../static/images/icon/edit.png')";
    can.onclick=startEdit;

    var edit=document.getElementById("button-changepw");
    edit.className="active";

    document.getElementById("input-tel").value=tel;
    document.getElementById("input-email").value=email;
    document.getElementById("input-tel").setAttribute("disabled","disabled");
    document.getElementById("input-email").setAttribute("disabled","disabled");

}
function confirmEdit(){
    //TODO
    var tel=$("#input-tel").val();
    var email=$("#input-email").val();
    $.ajax({
        url:"userinfo",
        method:"post",
        data:{tel:tel,email:email},
        success:function(){
            var con=document.getElementById("button-confirm");
            con.className="";

            var can=document.getElementById("button-cancel");
            can.style.backgroundImage= "url('../static/images/icon/edit.png')";
            can.onclick=startEdit;

            var edit=document.getElementById("button-changepw");
            edit.className="active";
            document.getElementById("input-tel").setAttribute("disabled","disabled");
            document.getElementById("input-email").setAttribute("disabled","disabled");
        }
    })
}
function delFavor(thisform){
    var stockId=thisform.children["stockId"];
    $.ajax({
        url:"/stockFunc/removeFavor",
        data:{stockId:stockId.value},
        success:function(result){
            if(result=="success"){
                $("#"+stockId.value).css("transition","none");
                $("#"+stockId.value).animate({height:"1px"},"linear",function(){
                    $("#"+stockId.value).css("display","none");
                    $("#"+stockId.value).css("transition","all ease 0.3s");
                })
            }
        }
    })
}
function cancelAvatar(){
    $("#path-input").change=setPath();
    $("#avatar-cancel").attr("class","");
    $("#avatar-confirm").attr("class","");
    $("#path").text("");
}