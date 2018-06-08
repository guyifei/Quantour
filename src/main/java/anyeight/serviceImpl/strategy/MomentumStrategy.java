package anyeight.serviceImpl.strategy;

import anyeight.dao.StockMapper;
import anyeight.model.BuyAndSellPrice;
import anyeight.util.MathHelper;
import anyeight.util.StrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 啊 on 2017/5/14.
 */
@Service("momentumStrategy")
public class MomentumStrategy extends QuantitativeServiceImpl{
    @Autowired
    Strategy strategy;
    @Autowired
    StockMapper stockMapper;

    @Override
    protected StrategyModel getStrategyModel(Date before, int formative, List<String> blocks, StrategyType strategyType) {
        Date formativeStart = null;
        Date formativeEnd = null;
        Map<String, Double> canBuyList = new HashMap<>();
        Map<String, Double> yield = new HashMap<>(blocks.size());
        formativeStart = stockMapper.getExCalendar("", MathHelper.utilToSql(before), formative);
        if (formativeStart == null)
            return null;                                                             //在该持有期找不到数据
        formativeEnd = stockMapper.getExCalendar("", MathHelper.utilToSql(before), 1);
        List<BuyAndSellPrice> buyAndSellPrices = stockMapper.getAvailableClose(MathHelper.utilToSql(formativeStart), MathHelper.utilToSql(formativeEnd), blocks);
        Map<String,Double>openPrice=strategy.getOpenPrice(before);
        for (int i = 0; i < buyAndSellPrices.size(); i++) {
            BuyAndSellPrice buyAndSellPrice = buyAndSellPrices.get(i);
            if (openPrice.containsKey(buyAndSellPrice.getId())) {
                yield.put(buyAndSellPrice.getId(), (buyAndSellPrice.getEndAdjClose() - buyAndSellPrice.getStartAdjClose()) / buyAndSellPrice.getStartAdjClose());
                canBuyList.put(buyAndSellPrice.getId(), openPrice.get(buyAndSellPrice.getId()));
            }
        }
        StrategyModel strategyModel = new StrategyModel(canBuyList, yield);
        return strategyModel;
    }
}
