package anyeight.util;

import java.sql.Date;
import java.util.ArrayList;


/**
 * Created by 啊 on 2017/5/14.
 */
public class MathHelper {
    public static double getVariance(ArrayList<Double> list){
        int size=list.size();
        double variance=0.0;
        double average=getAverage(list,size);
        for (int i=0;i<size;i++){
            System.out.println(i+"   "+list.get(i));
            variance+=Math.pow(list.get(i)-average,2);
        }
        return variance;
    }
    public static double getAverage(ArrayList<Double>list,int size){
        double average=0;
        for(int i=0;i<size;i++){
            average+=list.get(i);
        }
        average=average/size;
        return average;
    }
    public static double getCovariance(ArrayList<Double>list1,ArrayList<Double>list2){
        double result=0;
        int size=list1.size();
        double average1=getAverage(list1,size);
        double average2=getAverage(list2,size);
        for(int i=0;i<size;i++){
            result+=(list1.get(i)-average1)*(list2.get(i)-average2);
        }
        result=result/size;
        return result;
    }

    public static Date utilToSql(java.util.Date date){
        java.sql.Date sqlDate=new java.sql.Date(date.getTime());
       return sqlDate;
    }
    public static java.util.Date sqlToUtil(Date date){
        java.util.Date d=new java.util.Date (date.getTime());
        return d;
    }

}
