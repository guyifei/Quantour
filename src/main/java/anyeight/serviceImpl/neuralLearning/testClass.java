package anyeight.serviceImpl.neuralLearning;

import anyeight.dao.StockMapper;
import anyeight.model.Stock;
import anyeight.service.NeuralLearn;
import anyeight.service.StockService;
import anyeight.util.MathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class testClass implements NeuralLearn{
    @Autowired
    StockMapper stockMapper;

    public List<ArrayList<Double>> NeuralLearn(String code,Date date1, Date date2, Date date3){
        char first=code.toCharArray()[0];
        if(!(first-'0'>=0&&'9'-first>=0))
            code=stockMapper.getStockId(code);
        return help(code,date1,date2,date3);
    }

    @Override
    public List<ArrayList<Double>> MoneyGet(List<ArrayList<Double>> resultList) {
        BackProce b = new BackProce();
        double account = 10000;
        List<ArrayList<Double>>chance = b.selectChance(resultList,account);

//        new Graph("1",chance);
        return chance;
    }

    public List<ArrayList<Double>> help(String code, Date date1, Date date2, Date date3){
        List listWrong = new ArrayList();
        try{

//            ResultSet rs = stmt.executeQuery(sql2);
//            Map<String,String> map = stockService.getIDList();
//            Set<String> id = map.keySet();
//            List<String> idList = new ArrayList<>();
//            for(String line:id){
//                idList.add(map.get(line));
//            }
            List<Stock> rs = stockMapper.getOneStock(code, MathHelper.utilToSql(date1),MathHelper.utilToSql(date2));

            if(rs == null){
                return listWrong;
            }

            //创建序列
            List<Double> openPrice = new ArrayList<Double>();
            List<Double> highPrice = new ArrayList<Double>();
            List<Double> overPrice = new ArrayList<Double>();
            List<Double> lowPrice = new ArrayList<Double>();
            List<Double> vol = new ArrayList<Double>();

            for(Stock stock : rs){
                openPrice.add(stock.getOpen());
                highPrice.add(stock.getHigh());
                overPrice.add(stock.getClose());
                lowPrice.add(stock.getLow());
                vol.add((double) stock.getVolome());
            }

            Methods m = new Methods();
            double [][]dataset = m.bpTrain(overPrice, highPrice, lowPrice, openPrice, vol);
            if(dataset == null){
                return listWrong;
            }
            double [][]target = new double[dataset.length][];
            for(int i = 0;i<dataset.length;i++){
                target[i] = new double[1];
                target[i][0] = overPrice.get(overPrice.size()-dataset.length+i);
            }



//            String sql3="select * from Data2015to2016";
//            ResultSet rs2 = stmt.executeQuery(sql3);
//            int i = rs.size();
//            Map<String,String> map = stockService.getIDList();
//            Set<String> id = map.keySet();
//            List<String> idList = new ArrayList<>();
//            for(String line:id){
//                idList.add(map.get(line));
//            }
            List<Stock> rs2 = stockMapper.getOneStock(code,MathHelper.utilToSql(date2),MathHelper.utilToSql(date3));

            if(rs2 == null){
                return listWrong;
            }

            //创建序列
            List<Double> openPrice2 = new ArrayList<Double>();
            List<Double> highPrice2 = new ArrayList<Double>();
            List<Double> overPrice2 = new ArrayList<Double>();
            List<Double> lowPrice2 = new ArrayList<Double>();
            List<Double> vol2 = new ArrayList<Double>();

            for(Stock stock : rs2){
                openPrice2.add(stock.getOpen());
                highPrice2.add(stock.getHigh());
                overPrice2.add(stock.getClose());
                lowPrice2.add(stock.getLow());
                vol2.add((double) stock.getVolome());
            }

            Methods m2 = new Methods();
            double [][]dataset2 = m2.bpTrain(overPrice2, highPrice2, lowPrice2, openPrice2, vol2);
            if(dataset2 == null){
                return listWrong;
            }
            double [][]target2 = new double[dataset2.length][];
            for(int i = 0;i<dataset2.length;i++){
                target2[i] = new double[1];
                target2[i][0] = overPrice2.get(overPrice2.size()-dataset2.length+i);
            }



            BPnet bp = new BPnet(new int[]{6,13,13,1}, 0.15, 0.8);
            //迭代训练50000次
            for(int n=0;n<50000;n++)
                for(int i=0;i<dataset.length;i++)
                    bp.train(dataset[i], target[i]);


            //测试数据集
            double []result = new double[dataset2.length];
            List<ArrayList<Double>>resultList = new ArrayList<ArrayList<Double>>();
            for(int j=0;j<dataset2.length;j++){
                double []a = bp.computeOut(dataset2[j]);
                ArrayList<Double>list = new ArrayList<Double>();
                result[j] = 100*(-Math.log(1/a[0]-1));
                list.add(result[j]);
                list.add(target2[j][0]);
                resultList.add(list);
//                System.out.println(Arrays.toString(dataset2[j])+":"+result[j]+" real:"+target2[j][0]);
            }


//            new Graph("1",resultList);

//            System.out.println("End");
            return resultList;


        }catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

            return listWrong;
        }
    }


}
