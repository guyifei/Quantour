package anyeight.service;

import anyeight.vo.SearchProperVO;
import anyeight.vo.SelectVO;

import java.util.Date;
import java.util.List;

/**
 * Created by 啊 on 2017/5/11.
 */
public interface FindProperSectionService {
    /*给定开始日期，结束日期和形成期确定最佳持有期*/
    public SearchProperVO findPropperHold(Date startTime, Date endTime, int formative, List<SelectVO> selectVOs);
    /*给定开始日期，结束日期和持有其确定最佳形成期*/
    public SearchProperVO findProperFormative(Date startTime, Date endTime, int hold, List<SelectVO>selectVOs);
}
