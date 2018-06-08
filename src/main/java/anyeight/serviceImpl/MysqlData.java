package anyeight.serviceImpl;

import anyeight.dao.CreateDataMap;
import anyeight.dao.StockMapper;
import anyeight.model.Market;
import anyeight.model.Stock;
import anyeight.service.MysqlDataService;
import anyeight.service.StockService;
import anyeight.serviceImpl.strategy.Strategy;
import anyeight.util.MathHelper;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.*;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//import org.python.util.PythonInterpreter;

//import org.python.util.PythonInterpreter;

/**
 * Created by T5-SK on 2017/6/7.
 */
@Service
public class MysqlData implements MysqlDataService {
    @Autowired
    private Strategy strategy;
    @Autowired
    private StockService stockService;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private CreateDataMap createDataMap;

//    public static void main(String[] args) {
//        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.execfile("F:\\python workspace\\tuShare\\KLine.py");
//
//    }

    public void marketData(){

//        java.sql.Date date = java.sql.Date.valueOf("2017-05-01");
//        java.sql.Date dateEnd = java.sql.Date.valueOf("2017-06-03");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date dateEnd = null;
        try {
            date = sdf.parse("2017-06-14");
            dateEnd = sdf.parse("2017-06-14");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar1 = Calendar.getInstance();


        while (!date.after(dateEnd)){
            Date date1 = stockMapper.getExCalendar("", MathHelper.utilToSql(date),1);
            if(date1 == null){
                calendar1.setTime(date);
                calendar1.add(Calendar.DATE,1);
                date = calendar1.getTime();
                continue;
            }
            List<String> marketList = stockMapper.getBlockNameList();
            //map对应版块内的最热门的三只股票
            Map<String,List> map = new HashMap<>();
            for(int i = 0; i < marketList.size() ; i++){
                map.put(marketList.get(i),stockMapper.getHottestStock(MathHelper.utilToSql(date1),marketList.get(i)));
            }
            Set<String> set = map.keySet();
            List<Stock> list = null;
            Map<Double,String> mapForList = new TreeMap();
            double ans = 0;
            double i = 0;
            for(String str : set){
                list = map.get(str);
                for(Stock stock : list){
                    ans += stock.getTurnover();
                    i++;
                }
                if(i == 0){
                    ans = 0;
                }else{
                    ans = ans/i;
                }

                mapForList.put(ans,str);
                ans = 0;
                i = 0;
            }


            String line = "";
//                    date.toString() + '\n';
            List<String> ansList = new ArrayList<>();
            Set<Double> set1 = mapForList.keySet();
            int x = set1.size();
            for (Double number:set1){
                if(x<10){
                    ansList.add(mapForList.get(number));
                    line += mapForList.get(number) + "\t";
                }else {
                    x--;
                }
            }
            //第三名，第二名，第一名的顺序
//            line += "\n";
            int m = line.split("\t").length;

            String ninth = "";
            String eitht = "";
            String seventh = "";
            String sixth = "";
            String fifth = "";
            String fourth = "";
            String third = "";
            String second = "";
            String first = "";

            if(m == 9){
                ninth = line.split("\t")[0];
                eitht = line.split("\t")[1];
                seventh = line.split("\t")[2];
                sixth = line.split("\t")[3];
                fifth = line.split("\t")[4];
                fourth = line.split("\t")[5];
                third = line.split("\t")[6];
                second = line.split("\t")[7];
                first = line.split("\t")[8];
            }else {
                ninth = line.split("\t")[0];
                eitht = line.split("\t")[0];
                seventh = line.split("\t")[0];
                sixth = line.split("\t")[0];
                fifth = line.split("\t")[0];
                fourth = line.split("\t")[0];
                third = line.split("\t")[0];
                second = line.split("\t")[0];
                first = line.split("\t")[0];
            }



            createDataMap.addMarketHost(MathHelper.utilToSql(date),first,second,third,fourth,fifth,sixth,seventh,eitht,ninth);


            calendar1.setTime(date);
            calendar1.add(Calendar.DATE,1);
            date = calendar1.getTime();
        }


//        return ansList;

    }

    public void MarketModel(){
        MysqlData mysqlData = new MysqlData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date dateEnd = null;
        Calendar calendar1 = Calendar.getInstance();
        try {
            date = sdf.parse("2017-06-14");
            dateEnd = sdf.parse("2017-06-14");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        stockMapper.getStockId("华远地产");

        while (!date.after(dateEnd)) {
//            Date date1 = stockMapper.getExCalendar("",MathHelper.utilToSql(date),1);
//            if(date1==null){
//                calendar1.setTime(date);
//                calendar1.add(Calendar.DATE,1);
//                date = calendar1.getTime();
//                continue;
//            }
            Market market = mysqlData.getMarketInfo(date,stockMapper);
            if(market == null){
                calendar1.setTime(date);
                calendar1.add(Calendar.DATE,1);
                date = calendar1.getTime();
                continue;
            }
            createDataMap.addMarket(MathHelper.utilToSql(date),market);

            calendar1.setTime(date);
            calendar1.add(Calendar.DATE,1);
            date = calendar1.getTime();
        }
    }


    public Market getMarketInfo(Date date,StockMapper stockMapper){
        java.sql.Date date1 = MathHelper.utilToSql(date);
        List<Stock> stocks = stockMapper.getOneDayStock(date1);
        if(stocks==null)
            return null;                              //没有这一天的股票记录,

        Map<Stock,Stock> exStockMap=new HashMap<>();
        for(int i=0;i<stocks.size();i++){
            Stock exStockPO=null;
            Date exCalendar=stockMapper.getExCalendar(stocks.get(i).getCode(),MathHelper.utilToSql(date),1);
            if(exCalendar!=null) {
                List<Stock> arrayList = stockMapper.getOneStock(stocks.get(i).getCode(),MathHelper.utilToSql(exCalendar),MathHelper.utilToSql(exCalendar));
                if(arrayList.size()!=0){
                    exStockPO=arrayList.get(0);
                }

            }
            exStockMap.put(stocks.get(i),exStockPO);
        }

        return calStockMarket(stocks,exStockMap);
    }

    public Market calStockMarket(List<Stock>stockPOs, Map<Stock,Stock> map){
        long totalVolumn=0;
        int hardenStock=0;
        int dropStock=0;
        int over5Stock=0;
        int down5Stock=0;
        int over5PriceStock=0;
        int down5PriceStock=0;
        int size=stockPOs.size();
        for(int i=0;i<size;i++){
            Stock stockPO=stockPOs.get(i);
            totalVolumn+=stockPO.getVolome();
            if(map.get(stockPO)==null)
                continue;
            double exAdiClose=map.get(stockPO).getAdjClose();
            double range=(stockPO.getAdjClose()-exAdiClose)/exAdiClose;
            if((range>0.1)&&(!stockPO.getCode().startsWith("SZ"))){
                hardenStock++;
                over5Stock++;
                //System.out.println(stockPO.getName());
            }
            else if(range>0.05)
                over5Stock++;
            else if((range<-0.1)&&(!stockPO.getCode().startsWith("SZ"))){
                dropStock++;
                down5Stock++;
            }
            else if(range<-0.05)
                down5Stock++;
            double jiCha=stockPO.getOpen()-stockPO.getClose();     //开盘-收盘价与上一个交易日收盘价的5%作比较
            double compare=map.get(stockPO).getClose()*0.05;
            if(jiCha>compare)
                over5PriceStock++;
            else if(jiCha<-compare)
                down5PriceStock++;
        }
        Market stockMarketVO=new Market(totalVolumn,hardenStock,dropStock,over5Stock,down5Stock,over5PriceStock,down5PriceStock);
        return stockMarketVO;
    }

    @Override
    public void split(){
        List<String> list = createDataMap.getAllID();
        String plate = "";
        for (String str : list){
            if(str.startsWith("60")){
                plate = "sh000001";
            }else if(str.startsWith("000")){
                plate = "sh000300";
            }else if(str.startsWith("002")){
                plate = "sz399005";
            }else if(str.startsWith("300")){
                plate = "sz399006";
            }
            createDataMap.updatePlate(str,plate);
        }
    }

    @Override
    public int pythonTest(){
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("F:\\python workspace\\tuShare\\test.py");
        PyFunction func = (PyFunction)interpreter.get("sum",PyFunction.class);

        int a = 2010, b = 2 ;
        PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println("anwser = " + pyobj.toString());
        return 0;
    }

    public void predictStock(){
        PySystemState sys = Py.getSystemState();
        sys.path.add("C:\\jython2.5.2\\Lib");
        System.out.println(sys.path.toString());

        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("import sys");
        interpreter.exec("print sys.path");
        interpreter.execfile("F:\\python workspace\\tuShare\\skLearn3.py");
        PyFunction func = (PyFunction)interpreter.get("getSklearn",PyFunction.class);
        String start = "2017-01-01";
        String end = "2017-01-01";
        PyObject pyobj = func.__call__();
        System.out.println();
    }

    @Override
    public void weka() {
        //便于测试，用数组保存一些数据，从数据库中取数据是同理的
        //二维数组第一列表示当月的实际数据，第二列是上个月的数据，用于辅助对当月数据的预测的
        //二维数组的数据用于测试集数据，为了展示两种weka载入数据的方法，将训练集数据从arff文件中读取
        double[][] a = {{476005046,306349941},{377331965,476005046}};


        //读入训练集数据
        File inputFile = new File("C:\\Users\\T5-SK\\Desktop\\软工三\\test4.arff");//将路径替换掉
        ArffLoader atf = new ArffLoader();
        try {
            atf.setFile(inputFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Instances instancesTrain = null;
        try {
            instancesTrain = atf.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        instancesTrain.setClassIndex(0);//设置训练数据集的类属性，即对哪个数据列进行预测（属性的下标从0开始）


        //读入测试集数据
        FastVector attrs = new FastVector();
        Attribute ratio = new Attribute("CURtrdsum",1);//创建属性，参数为属性名称和属性号，但属性号并不影响FastVector中属性的顺序
        Attribute preratio = new Attribute("PREtrdsum",2);
        attrs.addElement(ratio);//向FastVector中添加属性，属性在FastVector中的顺序由添加的先后顺序确定。
        attrs.addElement(preratio);
        Instances instancesTest = new Instances("bp",attrs,attrs.size());//创建实例集，即数据集，参数为名称，FastVector类型的属性集，以及属性集的大小（即数据集的列数）
        instancesTest.setClass(ratio);//设置数据集的类属性，即对哪个数据列进行预测
        for(int k=0;k<2;k++){
            Instance ins = new DenseInstance(attrs.size());//创建实例，即一条数据
            ins.setDataset(instancesTest);//设置该条数据对应的数据集，和数据集的属性进行对应
            ins.setValue(ratio, a[k][0]);//设置数据每个属性的值
            ins.setValue(preratio, a[k][1]);
            instancesTest.add(ins);//将该条数据添加到数据集中
        }


        MultilayerPerceptron m_classifier = new MultilayerPerceptron();//创建算法实例，要使用其他的算法，只用把类换做相应的即可
        try {
            m_classifier.buildClassifier(instancesTrain); //进行训练
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0;i<2;i++){//测试分类结果
            //instancesTest.instance(i)获得的是用模型预测的结果值，instancesTest.instance(i).classValue();获得的是测试集类属性的值
            //此处是把预测值和当前值同时输出，进行对比
            try {
                System.out.println(m_classifier.classifyInstance(instancesTest.instance(i))+",,,"+instancesTest.instance(i).classValue());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("bp success!");
    }
}
