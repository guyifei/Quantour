package anyeight.controller;

import anyeight.jsonTrans.jsonTrans;
import anyeight.service.FindProperSectionService;
import anyeight.service.QuantitativeService;
import anyeight.util.SelectType;
import anyeight.util.StrategyType;
import anyeight.vo.QuantitativeVO;
import anyeight.vo.SearchProperVO;
import anyeight.vo.SelectVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/1.
 */
@Controller
@RequestMapping("/computeFunc")
public class BackComputeController
{
    @Qualifier("meanReverting")
    @Autowired
    private QuantitativeService meanService;

    @Qualifier("momentumStrategy")
    @Autowired
    private QuantitativeService momentumService;

    @Qualifier("quantitativeServiceImpl")
    @Autowired
    private QuantitativeService QuantitativeService;

    @Qualifier("customStrategy")
    @Autowired
    private QuantitativeService customStrategy;

    @Autowired
    private FindProperSectionService findProperSectionService;

    @RequestMapping(value="/lineChart",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getLineChart(String startDate,String endDate,int form,int hold,int optionsRadios,int plateNumber,String[] nodes,int strategyNumber) throws ParseException {
        List<String> stockNodes=new ArrayList<String>();
        if(nodes!=null){
            stockNodes=new ArrayList<String>(Arrays.asList(nodes));
        }
        QuantitativeVO vo = null;
        List<SelectVO> selectVOs=new ArrayList<>();
        String blockName="";
        switch(plateNumber){
            case 1:blockName="主板";break;
            case 2:blockName="中小板";break;
            case 3:blockName="创业板";break;
            default:break;
        }
//        if(strategyNumber==1){
//            SelectVO selectVO=new SelectVO(StrategyType.MOMENTUM,SelectType.MAXTOMIN,0.05,0,0,0);
//            selectVOs.add(selectVO);
//            if(optionsRadios==1){//大盘
//                vo=momentumService.getAll(strToDate(startDate),strToDate(endDate),form,hold, selectVOs);
//            }else if(optionsRadios==2){//板块
//
//                vo=momentumService.getAmongBlock(strToDate(startDate),strToDate(endDate),form,hold, blockName,selectVOs);
//            }else if(optionsRadios==3){//自定义股票
//                vo=momentumService.getAmongStocks(strToDate(startDate),strToDate(endDate),form,hold, "",stockNodes,selectVOs,3);
//            }
//
//        }else if(strategyNumber==2){
//            SelectVO selectVO=new SelectVO(StrategyType.MEAN_REVERTING,SelectType.MAXTOMIN,0.05,0,0,0);
//            selectVOs.add(selectVO);
//            if(optionsRadios==1){//大盘
//                vo=meanService.getAll(strToDate(startDate),strToDate(endDate),form,hold, selectVOs);
//            }else if(optionsRadios==2){//板块
//
//                vo=meanService.getAmongBlock(strToDate(startDate),strToDate(endDate),form,hold, blockName,selectVOs);
//            }else if(optionsRadios==3){//自定义股票
//                vo=meanService.getAmongStocks(strToDate(startDate),strToDate(endDate),form,hold, "",stockNodes,selectVOs,3);
//            }
//        }


        jsonTrans trans=new jsonTrans();
//        String result=trans.computeTrans(vo);
        String result="{\"annualizedReturn\":-1.3313276506382588,\"standardAnnualizedReturn\":0.32320478906162475,\"maximumDrawdown\":0.07237750520771485,\"Beta\":\"-0.18925536800731207\",\"Alpha\":\"-1.5063276506382588\",\"sharpeRatie\":\"-15.025205469305394\",\"dateList\":[\"三月 12, 2018\",\"三月 13, 2018\",\"三月 14, 2018\",\"三月 15, 2018\",\"三月 16, 2018\",\"三月 19, 2018\",\"三月 20, 2018\",\"三月 21, 2018\",\"三月 22, 2018\",\"三月 23, 2018\",\"三月 26, 2018\",\"三月 27, 2018\",\"三月 28, 2018\",\"三月 29, 2018\",\"三月 30, 2018\",\"四月 2, 2018\",\"四月 3, 2018\",\"四月 4, 2018\",\"四月 9, 2018\",\"四月 10, 2018\",\"四月 11, 2018\",\"四月 12, 2018\",\"四月 13, 2018\",\"四月 16, 2018\",\"四月 17, 2018\",\"四月 18, 2018\",\"四月 19, 2018\",\"四月 20, 2018\",\"四月 23, 2018\",\"四月 24, 2018\",\"四月 25, 2018\",\"四月 26, 2018\",\"四月 27, 2018\",\"五月 2, 2018\",\"五月 3, 2018\",\"五月 4, 2018\",\"五月 7, 2018\",\"五月 8, 2018\",\"五月 9, 2018\",\"五月 10, 2018\",\"五月 11, 2018\",\"五月 14, 2018\",\"五月 15, 2018\",\"五月 16, 2018\",\"五月 17, 2018\",\"五月 18, 2018\",\"五月 21, 2018\",\"五月 22, 2018\",\"五月 23, 2018\",\"五月 24, 2018\",\"五月 25, 2018\",\"五月 28, 2018\",\"五月 29, 2018\",\"五月 30, 2018\"],\"strategyEarningList\":[0.0,-0.06988627238624233,-0.07809313376047342,-0.07386942705942402,-0.039501023548732944,-0.034998766955706626,-0.02709620266179712,-0.03448308114195186,-0.03691081108319381,-0.04928831629090865,-0.05928831629090865,-0.05176939712711842,-0.03874436228338155,-0.03984551837047007,-0.03069722678831014,-0.02769014223979183,-0.04562281820836703,-0.02923619829452349,-0.03160715504392484,-0.02720738945933063,-0.02720738945933063,-0.0341232000894567,-0.06197798894681198,-0.0562284905223686,-0.07286514455217883,-0.0561161509046147,-0.0645181518345433,-0.05611783946190342,-0.05227905661731513,-0.06855051805143334,-0.06855051805143334,-0.08583168019286775,-0.0955164010605354,-0.0648847568958017,-0.01363051119216678,0.00050134693381863,0.00947794931470468,0.00370267839346526,0.00539341184802212,0.00796636397210008,0.00796636397210008,0.00872287447464074,0.0144514919477423,0.03530248803532147,0.03913067602763236,0.03704727733473718,0.0238683487509637,0.01832512494127201,0.02619796174351164,0.00921551941349448,0.00921551941349448,0.0208494052115772,0.0360277118218977,0.09544531425123],\"standardEarningList\":[0.0,-0.0014792676570629319,0.00618821948596201,0.012563230407235402,0.008737018748437127,0.01635328888500583,0.01867613076605585,0.005868866386881221,0.011433443500109912,0.04411893191452185,0.060824714314550714,0.046520105687799164,0.05683280057604065,0.05783002581939673,0.047426947978585286,0.0450197486751365,0.057001515420838034,0.051867763714859814,0.05837834906498835,0.05270832517376117,0.03670752980377865,0.034926985638148784,0.03831333359443966,0.05010830890482981,0.06213526712681639,0.06848015039723312,0.06776913783701545,0.06439785370615297,0.07705749259612979,0.07515643782707332,0.06230699473669938,0.06016793152587514,0.0713422772286177,0.06983589468578372,0.0737193488812097,0.06811560582186729,0.06757933363661833,0.05540776269051969,0.04792405421772053,0.04523968052639027,0.0420009580592972,0.0458452463086096,0.041814166623985814,0.04187140916061353,0.044950455078166134,0.0506536193853357,0.03405328376330519,0.03252581186487147,0.03427622837964455,0.04557409745089946,0.05145802766320907,0.054952835162583896,0.05711901325917919,0.07172489839449754],\"positive\":5,\"negative\":15,\"rate\":0.25,\"cateAxis\":[-0.1,-0.06,-0.04,-0.02,0.0,0.02,0.06],\"pAxis\":[0,0,0,0,0,3,2],\"nAxis\":[1,2,4,5,3,0,0],\"analyze\":\"此策略可能有副作用，请谨慎使用\"}";
        return result;
    }

    @RequestMapping("areaChart")
    @ResponseBody
    public String getAreaChart(String startDate,String endDate,int relationNumber,String relationForm,String relationHold,int strategyNumber) throws ParseException {
        List<SelectVO> selectVOs=new ArrayList<>();
        int form=0;
        int hold=0;
        if(!relationForm.equals("")){
            form=Integer.parseInt(relationForm);
        }
        if(!relationHold.equals("")){
            hold=Integer.parseInt(relationHold);
        }
        SearchProperVO vo=null;
        if(strategyNumber==1){
            SelectVO selectVO=new SelectVO(StrategyType.MOMENTUM,SelectType.MAXTOMIN,0.05,0,0,0);
            selectVOs.add(selectVO);
        }else if(strategyNumber==2){
            SelectVO selectVO=new SelectVO(StrategyType.MEAN_REVERTING,SelectType.MAXTOMIN,0.05,0,0,0);
            selectVOs.add(selectVO);
        }
        if(relationNumber==1){//Form
            vo=findProperSectionService.findPropperHold(strToDate(startDate),strToDate(endDate),form,selectVOs);
        }else if(relationNumber==2){
            vo=findProperSectionService.findProperFormative(strToDate(startDate),strToDate(endDate),hold,selectVOs);
        }

        return new Gson().toJson(vo);
    }

    @RequestMapping(value="/tree",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getTree(){
        List<String> classes=QuantitativeService.getBlockNameList();
        List<treeItem> treeItems=new ArrayList<treeItem>();
        int size=classes.size();
        for(int i=0;i<size;i++){
            List<String> stockNamesStr=QuantitativeService.getBlockInClass(classes.get(i));
            List<treeItem> stockNames=new ArrayList<treeItem>();
            for(int j=0;j<stockNamesStr.size();j++){
                stockNames.add(new treeItem(stockNamesStr.get(j),null));
            }
            treeItem treeItem=new treeItem(classes.get(i),stockNames);
            treeItems.add(treeItem);
        }


        return new Gson().toJson(treeItems);
    }

    @RequestMapping(value="/strategyCompute",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String strategyCompute(String startDate, String endDate,int form,int hold,int optionsRadios,int plateNumber,String[] nodes,int[] filterNames,
                                  String[] filterSelects,String[] filterValues,int[] rankNames,String[] rankSelects) throws ParseException {
        List<String> stockNodes=new ArrayList<String>();
        if(nodes!=null){
            stockNodes=new ArrayList<String>(Arrays.asList(nodes));
        }
        QuantitativeVO vo = null;
        List<SelectVO> selectVOs=new ArrayList<>();
        if(filterNames!=null){
            int filterSize=filterNames.length;
            for(int i=0;i<filterSize;i++){
                StrategyType strategyType=null;
                SelectType selectType=null;
                double max=0;
                double min=0;
                switch(filterNames[i]){
                    case 0:strategyType=StrategyType.OPEN;break;
                    case 1:strategyType=StrategyType.CLOSE;break;
                    case 2:strategyType=StrategyType.HIGH;break;
                    case 3:strategyType=StrategyType.ADJ_CLOSE;break;
                    case 4:strategyType=StrategyType.VOLUMN;break;
                    default:break;
                }
                if(filterSelects[i].equals("gt")){
                    selectType=SelectType.MIN;
                    min=Double.parseDouble(filterValues[i]);
                }else if(filterSelects[i].equals("lt")){
                    selectType=SelectType.MAX;
                    max=Double.parseDouble(filterValues[i]);
                }
                SelectVO tempVO=new SelectVO(strategyType,selectType,0.05,0,max,min);
                selectVOs.add(tempVO);
            }
        }
        if(rankNames!=null){
            int rankSize=rankNames.length;
            for(int i=0;i<rankSize;i++){
                StrategyType strategyType=null;
                SelectType selectType=null;
                switch(rankNames[i]){
                    case 0:strategyType=StrategyType.OPEN;break;
                    case 1:strategyType=StrategyType.CLOSE;break;
                    case 2:strategyType=StrategyType.HIGH;break;
                    case 3:strategyType=StrategyType.ADJ_CLOSE;break;
                    case 4:strategyType=StrategyType.VOLUMN;break;
                    default:break;
                }
                if(rankSelects[i].equals("asc")){
                    selectType=SelectType.MINTOMAX;
                }else if(rankSelects[i].equals("des")){
                    selectType=SelectType.MAXTOMIN;
                }
                SelectVO tempVO=new SelectVO(strategyType,selectType,0.05,0,0,0);
                selectVOs.add(tempVO);
            }
        }

        String blockName="";
        switch(plateNumber){
            case 1:blockName="主板";break;
            case 2:blockName="中小板";break;
            case 3:blockName="创业板";break;
            default:break;
        }
//        if(optionsRadios==1){//大盘
//            vo=customStrategy.getAll(strToDate(startDate),strToDate(endDate),form,hold, selectVOs);
//        }else if(optionsRadios==2){//板块
//
//            vo=customStrategy.getAmongBlock(strToDate(startDate),strToDate(endDate),form,hold, blockName,selectVOs);
//        }else if(optionsRadios==3){//自定义股票
//            vo=customStrategy.getAmongStocks(strToDate(startDate),strToDate(endDate),form,hold, "",stockNodes,selectVOs,3);
//        }

        jsonTrans trans=new jsonTrans();
//        String result=trans.computeTrans(vo);
        String result="{\"annualizedReturn\":-1.3313276506382588,\"standardAnnualizedReturn\":0.32320478906162475,\"maximumDrawdown\":0.07237750520771485,\"Beta\":\"-0.18925536800731207\",\"Alpha\":\"-1.5063276506382588\",\"sharpeRatie\":\"-15.025205469305394\",\"dateList\":[\"三月 12, 2018\",\"三月 13, 2018\",\"三月 14, 2018\",\"三月 15, 2018\",\"三月 16, 2018\",\"三月 19, 2018\",\"三月 20, 2018\",\"三月 21, 2018\",\"三月 22, 2018\",\"三月 23, 2018\",\"三月 26, 2018\",\"三月 27, 2018\",\"三月 28, 2018\",\"三月 29, 2018\",\"三月 30, 2018\",\"四月 2, 2018\",\"四月 3, 2018\",\"四月 4, 2018\",\"四月 9, 2018\",\"四月 10, 2018\",\"四月 11, 2018\",\"四月 12, 2018\",\"四月 13, 2018\",\"四月 16, 2018\",\"四月 17, 2018\",\"四月 18, 2018\",\"四月 19, 2018\",\"四月 20, 2018\",\"四月 23, 2018\",\"四月 24, 2018\",\"四月 25, 2018\",\"四月 26, 2018\",\"四月 27, 2018\",\"五月 2, 2018\",\"五月 3, 2018\",\"五月 4, 2018\",\"五月 7, 2018\",\"五月 8, 2018\",\"五月 9, 2018\",\"五月 10, 2018\",\"五月 11, 2018\",\"五月 14, 2018\",\"五月 15, 2018\",\"五月 16, 2018\",\"五月 17, 2018\",\"五月 18, 2018\",\"五月 21, 2018\",\"五月 22, 2018\",\"五月 23, 2018\",\"五月 24, 2018\",\"五月 25, 2018\",\"五月 28, 2018\",\"五月 29, 2018\",\"五月 30, 2018\"],\"strategyEarningList\":[0.0,-0.06988627238624233,-0.07809313376047342,-0.07386942705942402,-0.039501023548732944,-0.034998766955706626,-0.02709620266179712,-0.03448308114195186,-0.03691081108319381,-0.04928831629090865,-0.05928831629090865,-0.05176939712711842,-0.03874436228338155,-0.03984551837047007,-0.03069722678831014,-0.02769014223979183,-0.04562281820836703,-0.02923619829452349,-0.03160715504392484,-0.02720738945933063,-0.02720738945933063,-0.0341232000894567,-0.06197798894681198,-0.0562284905223686,-0.07286514455217883,-0.0561161509046147,-0.0645181518345433,-0.05611783946190342,-0.05227905661731513,-0.06855051805143334,-0.06855051805143334,-0.08583168019286775,-0.0955164010605354,-0.0648847568958017,-0.01363051119216678,0.00050134693381863,0.00947794931470468,0.00370267839346526,0.00539341184802212,0.00796636397210008,0.00796636397210008,0.00872287447464074,0.0144514919477423,0.03530248803532147,0.03913067602763236,0.03704727733473718,0.0238683487509637,0.01832512494127201,0.02619796174351164,0.00921551941349448,0.00921551941349448,0.0208494052115772,0.0360277118218977,0.09544531425123],\"standardEarningList\":[0.0,-0.0014792676570629319,0.00618821948596201,0.012563230407235402,0.008737018748437127,0.01635328888500583,0.01867613076605585,0.005868866386881221,0.011433443500109912,0.04411893191452185,0.060824714314550714,0.046520105687799164,0.05683280057604065,0.05783002581939673,0.047426947978585286,0.0450197486751365,0.057001515420838034,0.051867763714859814,0.05837834906498835,0.05270832517376117,0.03670752980377865,0.034926985638148784,0.03831333359443966,0.05010830890482981,0.06213526712681639,0.06848015039723312,0.06776913783701545,0.06439785370615297,0.07705749259612979,0.07515643782707332,0.06230699473669938,0.06016793152587514,0.0713422772286177,0.06983589468578372,0.0737193488812097,0.06811560582186729,0.06757933363661833,0.05540776269051969,0.04792405421772053,0.04523968052639027,0.0420009580592972,0.0458452463086096,0.041814166623985814,0.04187140916061353,0.044950455078166134,0.0506536193853357,0.03405328376330519,0.03252581186487147,0.03427622837964455,0.04557409745089946,0.05145802766320907,0.054952835162583896,0.05711901325917919,0.07172489839449754],\"positive\":5,\"negative\":15,\"rate\":0.25,\"cateAxis\":[-0.1,-0.06,-0.04,-0.02,0.0,0.02,0.06],\"pAxis\":[0,0,0,0,0,3,2],\"nAxis\":[1,2,4,5,3,0,0],\"analyze\":\"此策略可能有副作用，请谨慎使用\"}";
        return result;
    }

    /**
     * String转Date
     * @param dateStr
     * @return
     */
    private Date strToDate(String dateStr) throws ParseException {
        Date date;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.parse(dateStr);

        return date;
    }


    class treeItem{
        private String text;
        private List<treeItem> nodes;

        treeItem(String className,List<treeItem> stockList){
            this.text=className;
            this.nodes=stockList;
        }
    }



}
