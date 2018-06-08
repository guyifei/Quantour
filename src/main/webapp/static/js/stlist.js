/**
 *
 * @param vo should be a stockVO
 */
function favorite(thisform){
    var favor = thisform.children["favor"];
    var stockId=thisform.children["stockId"];
        if(favor.value=="true"){
            $.ajax({
                url:"/stockFunc/removeFavor",
                data:{stockId:stockId.value},
                success:function(result){
                    if(result=="success"){
                        $(favor).attr("value","false");
                        $(thisform).css("background-image","url('../static/images/icon/star_blank.png')");
                    }
                }
            })
        }else{
            $.ajax({
                url:"/stockFunc/addFavor",
                data:{stockId:stockId.value},
                success:function(result){
                    if(result=="success"){
                        $(favor).attr("value","true");
                        $(thisform).css("background-image","url('../static/images/icon/star_filled.png')");
                    }else if(result=="没有登录"){
                        location.href = "/login";
                    }
                }
            })
        }

}

function fetchNameList(){
    var list;
    $.ajax({
        url:"/stockFunc/getList",
        dataType:'json',
        success:function(result){
            list=result;
            $("#stockA").autocomplete({source:list});
            $("#stockB").autocomplete({source:list});
        }
    })


}