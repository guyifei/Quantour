package anyeight.service;

import anyeight.vo.QuantitativeVO;
import anyeight.vo.SelectVO;

import java.util.Date;
import java.util.List;

/**
 * Created by 啊 on 2017/5/11.
 */
public interface QuantitativeService {
    /*得到大盘内所有股票的量化交易信息*/
    public QuantitativeVO getAll(Date startTime, Date endTime, int formative, int hold,  List<SelectVO>selectVOs);
    /*得到某个板块内所有股票的量化交易信息*/
    public QuantitativeVO getAmongBlock(Date startTime, Date endTime, int formative, int hold, String blockName,  List<SelectVO>selectVOs);
    /*得到选定的某些股票的量化交易信息*/
    public QuantitativeVO getAmongStocks(Date startTime, Date endTime, int formative, int hold, String blockName, List<String> blocks, List<SelectVO>selectVOs, int type) ;
    /*得到用户自选股票的分类类名*/
    public List<String> getBlockNameList();
    /*得到某一类中的所有股票*/
    public List<String> getBlockInClass(String className);
    /*保存某个用户自定义策略*/
    public Boolean saveStrategy(List<SelectVO>selectVOs,String strategyName,String user_id);
    /*对用户自定义策略的评估*/
    public String pairedSymbolicTest(QuantitativeVO quantitativeVO);
}
