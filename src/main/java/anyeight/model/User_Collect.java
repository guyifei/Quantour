package anyeight.model;

import java.util.List;

/**
 * Created by 啊 on 2017/5/25.
 */
public class User_Collect{
    private String user_id;                         //用户ID
    private List<String> collectStockId;            //收藏股票ID列表
    private List<String> collectStockName;          //收藏股票名称列表
    private List<String> collectStockIndustry;      //收藏股票行业列表
    private List<String> collectStockArea;          //收藏股票板块列表
    public User_Collect(){}

    public User_Collect(String user_id,List<String>collectStockId,List<String>collectStockName,List<String>collectStockIndustry,List<String>collectStockArea){
        this.user_id=user_id;
        this.collectStockId=collectStockId;
        this.collectStockName=collectStockName;
        this.collectStockIndustry=collectStockIndustry;
        this.collectStockArea=collectStockArea;
    }

    public String getUser_id() {
        return user_id;
    }

    public List<String> getCollectStockId() {
        return collectStockId;
    }

    public List<String> getCollectStockName() {
        return collectStockName;
    }

    public List<String> getCollectStockIndustry() {
        return collectStockIndustry;
    }

    public List<String> getCollectStockArea() {
        return collectStockArea;
    }
}
