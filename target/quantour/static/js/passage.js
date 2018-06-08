/**
 * Created by 35930 on 2017/6/8.
 */

function checkPassage(thisform){
    with(thisform){
        if(title.value.length==0){
            alert("标题不能为空");
            preventDefault();
            return false;
        }
        if(content.values.length==0){
            alert("内容不能为空");
            preventDefault();
            return false;
        }
        return true;
    }
}