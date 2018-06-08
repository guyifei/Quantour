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
        if(strategyNumber==1){
            SelectVO selectVO=new SelectVO(StrategyType.MOMENTUM,SelectType.MAXTOMIN,0.05,0,0,0);
            selectVOs.add(selectVO);
            if(optionsRadios==1){//大盘
                vo=momentumService.getAll(strToDate(startDate),strToDate(endDate),form,hold, selectVOs);
            }else if(optionsRadios==2){//板块

                vo=momentumService.getAmongBlock(strToDate(startDate),strToDate(endDate),form,hold, blockName,selectVOs);
            }else if(optionsRadios==3){//自定义股票
                vo=momentumService.getAmongStocks(strToDate(startDate),strToDate(endDate),form,hold, "",stockNodes,selectVOs,3);
            }

        }else if(strategyNumber==2){
            SelectVO selectVO=new SelectVO(StrategyType.MEAN_REVERTING,SelectType.MAXTOMIN,0.05,0,0,0);
            selectVOs.add(selectVO);
            if(optionsRadios==1){//大盘
                vo=meanService.getAll(strToDate(startDate),strToDate(endDate),form,hold, selectVOs);
            }else if(optionsRadios==2){//板块

                vo=meanService.getAmongBlock(strToDate(startDate),strToDate(endDate),form,hold, blockName,selectVOs);
            }else if(optionsRadios==3){//自定义股票
                vo=meanService.getAmongStocks(strToDate(startDate),strToDate(endDate),form,hold, "",stockNodes,selectVOs,3);
            }
        }


        jsonTrans trans=new jsonTrans();
        String result=trans.computeTrans(vo);

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
        if(optionsRadios==1){//大盘
            vo=customStrategy.getAll(strToDate(startDate),strToDate(endDate),form,hold, selectVOs);
        }else if(optionsRadios==2){//板块

            vo=customStrategy.getAmongBlock(strToDate(startDate),strToDate(endDate),form,hold, blockName,selectVOs);
        }else if(optionsRadios==3){//自定义股票
            vo=customStrategy.getAmongStocks(strToDate(startDate),strToDate(endDate),form,hold, "",stockNodes,selectVOs,3);
        }

        jsonTrans trans=new jsonTrans();
        String result=trans.computeTrans(vo);

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
