package anyeight.serviceImpl.neuralLearning;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T5-SK on 2017/6/14.
 */
public class BackProce {

    public List<ArrayList<Double>> selectChance(List<ArrayList<Double>>result, double account){
        double accountF = account;
        System.out.println("初始账户为： "+account);
        ArrayList<Double>expect = new ArrayList<Double>();
        ArrayList<Double>target = new ArrayList<Double>();
        for(int i = 0;i<result.size();i++){
            expect.add(result.get(i).get(0));
            target.add(result.get(i).get(1));
        }
        List<ArrayList<Double>>chance = new ArrayList<ArrayList<Double>>();
        for(int i = 1;i<expect.size();i++){
            if(expect.get(i)>target.get(i-1)){
                //买入
                account += account*(target.get(i)-target.get(i-1))/target.get(i-1);
            }
            ArrayList<Double>list = new ArrayList<Double>();
            list.add((account-accountF)/accountF);
            list.add((double) i);
            chance.add(list);
        }
        System.out.println("期末账户为： "+account);
        System.out.println("年化收益率为： "+(account-accountF)/accountF);
        return chance;
    }
}
