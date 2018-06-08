package anyeight.jsonTest;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */
public class treeItem {

        private String text;
        private List<treeItem> nodes;

        public treeItem(String className,List<treeItem> stockList){
            this.text=className;
            this.nodes=stockList;
        }


}
