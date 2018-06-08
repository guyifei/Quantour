package anyeight.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by T5-SK on 2017/6/14.
 */
public interface NeuralLearn {
    /**神经网络
     *
     * @param code 预测股票code
     * @param date1 建模开始日期
     * @param date2 建模结束日期&检测期开始时间
     * @param date3 检测期结束时间
     * @return
     */
    public List<ArrayList<Double>> NeuralLearn(String code,Date date1, Date date2, Date date3);

    /**
     * 获得收益率
     * 参数相同
     */
    public List<ArrayList<Double>> MoneyGet(List<ArrayList<Double>> resultList);

}
