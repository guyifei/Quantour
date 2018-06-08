package anyeight.util.TimerTask;

import anyeight.dao.StockMapper;
import anyeight.dao.UserMapper;
import anyeight.model.Stock;
import anyeight.model.User_Collect;
import anyeight.serviceImpl.SendEmail;
import anyeight.util.MathHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 啊 on 2017/6/13.
 */
public class NFDFlightDataTimerTask extends TimerTask {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StockMapper stockMapper;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void run() {
        try {
            //在这里写你要执行的内容
            Date date=new Date();
            Date exDate=stockMapper.getExCalendar("", MathHelper.utilToSql(date),1);
            Map<String,String> emailMap=userMapper.getCustomerEmail();
            Iterator<Map.Entry<String,String>> iterator1 = emailMap.entrySet().iterator();
            Map.Entry<String,String> tail1 = null;
            while (iterator1.hasNext()) {
                User_Collect user_collect=userMapper.getUserCollectVO(tail1.getKey());
                List<String> collectStockId=user_collect.getCollectStockId();
                List<String>collectStockName=user_collect.getCollectStockName();
                List<String>sendStock=new ArrayList<>();
                for(int i=0;i<collectStockId.size();i++){
                    List<Stock>stacks=stockMapper.getOneStock(collectStockId.get(i),MathHelper.utilToSql(exDate),MathHelper.utilToSql(date));
                    if(stacks!=null){
                        if(stacks.size()>=2){
                            double zhangFu=stacks.get(0).getAdjClose()-stacks.get(1).getAdjClose();
                            if(zhangFu>=0.05)
                                sendStock.add(collectStockId.get(i));
                        }
                    }
                }
                if(sendStock.size()!=0){
                    SendEmail sendEmail=new SendEmail(tail1.getValue(),"您有股票涨幅超过百分之5，请至AnyEight股票系统中查看");
                    Thread thread = new Thread(sendEmail);
                    thread.start();
                }
            }
        System.out.println("当前时间"+formatter.format(Calendar.getInstance().getTime()));
        } catch(Exception e) {
            System.out.println("-------------解析信息发生异常--------------");
        }
    }
}
