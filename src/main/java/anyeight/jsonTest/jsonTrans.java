package anyeight.jsonTest;

import com.google.gson.Gson;
import com.sun.xml.internal.ws.developer.Serialization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */
public class jsonTrans {
    public static void main(String[] args){
        Gson gson=new Gson();
        String[] categories={
                "2012-2-2","2012-2-3","2012-2-4"
        };
        int[] date={
                123141,142124,12442
        };

        TestVO vo=new TestVO(categories,date);
//        TestVO vo2=new TestVO("2012-2-2",10,10);
//        ArrayList<TestVO> arr=new ArrayList<TestVO>();
//        arr.add(vo);
//        arr.add(vo2);

        String s1=gson.toJson(vo);
        System.out.println(s1);

        Object[] obj1={
          "2012-2-2",1231,4343,1234,2445,1234.3
        };
        Object[] obj2={
                "2012-2-2",1231,4343,1234,2445,1234.3
        };
        Object[] objs={
                obj1,obj2
        };

        String s2=gson.toJson(objs);
        System.out.println(s2);

        treeItem treeA=new treeItem("A",null);
        treeItem treeB=new treeItem("B",null);
        treeItem treeC=new treeItem("C",null);
        List<treeItem> list = new ArrayList<treeItem>();
        list.add(treeA);
        list.add(treeB);
        list.add(treeC);

        treeItem tree1=new treeItem("1111",list);
        treeItem tree2=new treeItem("2222",list);
        List<treeItem> treeItems=new ArrayList<treeItem>();
        treeItems.add(tree1);
        treeItems.add(tree2);
        String s3=gson.toJson(treeItems);
        System.out.print(s3);
    }


}
