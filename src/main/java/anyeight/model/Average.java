package anyeight.model;

/**
 * Created by 啊 on 2017/5/14.
 */
public class Average {
    /**
     * 股票ID
     */
    private String id;
    /**
     * 五日均值
     */
    private double average5;
    /**
     * 十日均值
     */
    private double average10;
    /**
     * 二十日均值
     */
    private double average20;
    public Average(String id,double average5,double average10,double average20){
        this.id=id;
        this.average5=average5;
        this.average10=average10;
        this.average20=average20;
    }
    public Average(){

    }

    public double getAverage5() {
        return average5;
    }

    public double getAverage10() {
        return average10;
    }

    public double getAverage20() {
        return average20;
    }
}
