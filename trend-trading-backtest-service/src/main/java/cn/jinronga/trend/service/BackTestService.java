package cn.jinronga.trend.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.jinronga.trend.client.IndexDataClient;
import cn.jinronga.trend.pojo.AnnualProfit;
import cn.jinronga.trend.pojo.IndexData;
import cn.jinronga.trend.pojo.Profit;
import cn.jinronga.trend.pojo.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/4 0004
 * Time: 21:24
 * E-mail:1460595002@qq.com
 * 类说明:用于提供所有模拟回测数据服务
 */
@Service
public class BackTestService {
    @Autowired
    IndexDataClient  indexDataClient;


  //获取回测数据
    public List<IndexData>listIndexData(String code){

        List<IndexData> result=indexDataClient.getIndexData(code);
        Collections.reverse(result); //表示让老的数据在前面新的数据在后面

        for (IndexData indexData:result){
            System.out.println(indexData.getDate());
        }

        return result;
    }

    /**
     *
     * @param ma 移动均线：比如一个点的二十的均线今天往前数二十天，不包括今天，把这二十天收盘点加起来然后除以二
     * @param sellRate 购买阈值
     * @param buyRate 出售阈值
     * @param serviceCharge 手续费
     * @param indexDatas 通过日期帅选过后的模拟回测
     * @return
     */
    //收益对比
    public Map<String,Object> simulate(int ma, float sellRate, float buyRate, float serviceCharge, List<IndexData> indexDatas){

        List<Profit>profits=new ArrayList<>();
        List<Trade>trades=new ArrayList<>();
        float initCash=1000; //假设买一千块钱
        float cash=initCash;
        float share =0; //每份多少钱
        float value=0; //价值

        int winCount=0;//交易总数
        float totalWinRate=0;//总赢率
        float  avgWinRate=0; //平均胜出
        float totalLossRate=0; //亏损率
        int lossCount=0; //损失数
        float avgLossRate=0; //平均盈亏率







        float init=0; //趋势投资与收盘点开始比较对齐

        if(!indexDatas.isEmpty()){
            init=indexDatas.get(0).getClosePoint();
        }

        for (int i=0;i<indexDatas.size();i++){
          IndexData indexData =indexDatas.get(i);
          float closePoint=indexData.getClosePoint();
            float avg = getMA(i,ma,indexDatas);
            float max = getMax(i,ma,indexDatas);

            float increase_rate = closePoint/avg;
            float decrease_rate = closePoint/max;
            if(avg!=0) {
                //buy 超过了均线
                if(increase_rate>buyRate  ) {
                    //如果没买
                    if(0 == share) {
                        share = cash / closePoint;
                        cash = 0;
                        //购买的时候交易对象
                        Trade trade=new Trade();
                        trade.setByDate(indexData.getDate());
                        trade.setBuyClosePoint(indexData.getClosePoint());
                        trade.setSellDate("n/a"); //因为交易的时间不清楚所以为空
                        trade.setSellClosePoint(0); //购买的指数
                        trades.add(trade); //添加到List集合




                    }
                }
                //sell 低于了卖点
                else if(decrease_rate<sellRate ) {
                    //如果没卖
                    if(0!= share){
                        cash = closePoint * share * (1-serviceCharge);
                        share = 0;


                        Trade trade=trades.get(trades.size()-1);
                        trade.setSellDate(indexData.getDate());
                        trade.setSellClosePoint(indexData.getClosePoint());

                        float rate=cash/initCash;
                        trade.setRate(rate);


                        if(trade.getSellClosePoint()-trade.getBuyClosePoint()>0) {
                            totalWinRate +=(trade.getSellClosePoint()-trade.getBuyClosePoint())/trade.getBuyClosePoint();
                            winCount++;
                        }
                        else {
                            totalLossRate +=(trade.getSellClosePoint()-trade.getBuyClosePoint())/trade.getBuyClosePoint();
                            lossCount ++;
                        }

                    }
                }
                //do nothing
                else{

                }
            }

            if(share!=0) {
                value = closePoint * share;
            }
            else {
                value = cash;
            }
            float rate = value/initCash;

            Profit profit = new Profit();
            profit.setDate(indexData.getDate());
            profit.setValue(rate*init);

        System.out.println("profit.value:" + profit.getValue());
            profits.add(profit);


        }
        //平均胜率
        avgWinRate = totalWinRate / winCount;
        //平均输率
        avgLossRate = totalLossRate / lossCount;
        //年收益
       List<AnnualProfit> annualProfits=caculateAnnualProfits(indexDatas,profits);

        Map<String,Object> map = new HashMap<>();
        map.put("profits", profits); //趋势投资
        map.put("trades",trades); //交易集合

        //交易统计
        map.put("winCount", winCount);
        map.put("lossCount", lossCount);
        map.put("avgWinRate", avgWinRate); //平均赢率
        map.put("avgLossRate", avgLossRate); //平均输率
        map.put("annualProfits", annualProfits);

        return map;
    }

    private static float getMax(int i, int day, List<IndexData> list) {
        int start = i-1-day;
        if(start<0)
            start = 0;
        int now = i-1;

        if(start<0)
            return 0;

        float max = 0;
        for (int j = start; j < now; j++) {
            IndexData bean =list.get(j);
            if(bean.getClosePoint()>max) {
                max = bean.getClosePoint();
            }
        }
        return max;
    }

    private static float getMA(int i, int ma, List<IndexData> list) {
        int start = i-1-ma;
        int now = i-1;

        if(start<0)
            return 0;

        float sum = 0;
        float avg = 0;
        for (int j = start; j < now; j++) {
            IndexData bean =list.get(j);
            sum += bean.getClosePoint();
        }
        avg = sum / (now - start);
        return avg;
    }
    //用于计算当前时间范围多少年
    public float getYear(List<IndexData> allIndexDatas) {
        float years;
        String sDateStart = allIndexDatas.get(0).getDate();
        String sDateEnd = allIndexDatas.get(allIndexDatas.size()-1).getDate();

        Date dateStart = DateUtil.parse(sDateStart);
        Date dateEnd = DateUtil.parse(sDateEnd);

        long days = DateUtil.between(dateStart, dateEnd, DateUnit.DAY);
        years = days/365f;
        return years;
    }


//    算完整时间范围内，每一年的指数投资收益和趋势投资收益
    private List<AnnualProfit> caculateAnnualProfits(List<IndexData> indexDatas, List<Profit> profits) {
        List<AnnualProfit> result = new ArrayList<>();
        String strStartDate = indexDatas.get(0).getDate();
        String strEndDate = indexDatas.get(indexDatas.size()-1).getDate();
        Date startDate = DateUtil.parse(strStartDate);
        Date endDate = DateUtil.parse(strEndDate);
        int startYear = DateUtil.year(startDate);
        int endYear = DateUtil.year(endDate);
        for (int year =startYear; year <= endYear; year++) {
            AnnualProfit annualProfit = new AnnualProfit();
            annualProfit.setYear(year);
            float indexIncome = getIndexIncome(year,indexDatas);
            float trendIncome = getTrendIncome(year,profits);
            annualProfit.setIndexIncome(indexIncome);
            annualProfit.setTrendIncome(trendIncome);
            result.add(annualProfit);
        }
        return result;
    }

    //计算某年的指数收益
    private float getIndexIncome(int year,List<IndexData>indexDatas){
        IndexData first=null;
        IndexData last=null;


        for (IndexData indexData:indexDatas){
            String strDate=indexData.getDate();
//            Date date=DateUtil.parse(strDate);
            int currentYear=getYear(strDate);
            if(currentYear==year){
                if(null==first){
                    first=indexData;
                }
                    last=indexData;

            }

        }
        return (last.getClosePoint() - first.getClosePoint()) / first.getClosePoint();
    }
    //计算某年的趋势投资收益
    private float getTrendIncome(int year, List<Profit> profits) {
        Profit first=null;
        Profit last=null;
        for (Profit profit : profits) {
            String strDate = profit.getDate();
            int currentYear = getYear(strDate);
            if(currentYear == year) {
                if(null==first)
                    first = profit;
                last = profit;
            }
            if(currentYear > year)
                break;
        }
        return (last.getValue() - first.getValue()) / first.getValue();
    }
       //获取某个日期
    private  int getYear(String date){
        String strYear= StrUtil.subBefore(date,"-",false);
        return Convert.toInt(strYear);

    }

}