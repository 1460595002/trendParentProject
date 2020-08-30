package cn.jinronga.trend.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.jinronga.trend.pojo.AnnualProfit;
import cn.jinronga.trend.pojo.IndexData;
import cn.jinronga.trend.pojo.Profit;
import cn.jinronga.trend.pojo.Trade;
import cn.jinronga.trend.service.BackTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/4 0004
 * Time: 22:35
 * E-mail:1460595002@qq.com
 * 类说明:控制器，返回的数据是放在一个 Map 里的，而目前的key是 indexDatas。
 */
@RestController
public class BackTestController {
    @Autowired
    BackTestService backTestService;

    @GetMapping("/simulate/{code}/{ma}/{buyThreshold}/{sellTreshold}/{serviceCharge}/{startDate}/{endDate}")
    //访问simulate/{code就执行backTestService方法返回list集合，返回的数据是放在一个 Map 里的
    //因为将来会返回各种各样的数据，通过这种方式才好区分不同的数据。
    @CrossOrigin//允许跨域
    public Map<String,Object> backTest(@PathVariable("code") String code,
                                       @PathVariable("ma") int ma,
                                       @PathVariable("buyThreshold") float buyThreshold,
                                       @PathVariable("sellTreshold") float sellTreshold,
                                       @PathVariable("serviceCharge")float serviceCharge,
                                       @PathVariable("startDate")String strStartDate,
                                       @PathVariable("endDate") String strEndDate) throws Exception {
     //获取所有的数据
      List<IndexData> allIndexDatas=backTestService.listIndexData(code);
      //计算出开始日去和结束日期
        String indexStartDate=allIndexDatas.get(0).getDate();
        String indexEndDate = allIndexDatas.get(allIndexDatas.size()-1).getDate();
       //过滤
        allIndexDatas=filterByDateRange(allIndexDatas,strStartDate,strEndDate);

            float sellRate=sellTreshold;//出售阈值
            float buyRate=buyThreshold;//购买阈值

//            float sellRate=0.95f;
//            float buyRate=1.05f;
            float serviveChare=0f;
            Map<String,?>simulateResult=backTestService.simulate(ma,buyRate,buyRate,serviveChare,allIndexDatas);
            //利润
            List<Profit> profits=(List<Profit>) simulateResult.get("profits");
            //交易记录
            List<Trade>trades=(List<Trade>)simulateResult.get("trades");




       //获取年份
        float years = backTestService.getYear(allIndexDatas);
        //计算出指数的收益和趋势投资的收益
        //总收入
        float indexIncomeTotal = (allIndexDatas.get(allIndexDatas.size()-1).getClosePoint() - allIndexDatas.get(0).getClosePoint()) /
                allIndexDatas.get(0).getClosePoint();
        //年收入
        float indexIncomeAnnual = (float) Math.pow(1+indexIncomeTotal, 1/years) - 1;
        float trendIncomeTotal = (profits.get(profits.size()-1).getValue() - profits.get(0).getValue()) / profits.get(0).getValue();
        float trendIncomeAnnual = (float) Math.pow(1+trendIncomeTotal, 1/years) - 1;

        int winCount=(Integer)simulateResult.get("winCount");//平均胜次数
        int lossCount=(Integer)simulateResult.get("lossCount");//平均次数
        float avgWinRate = (Float) simulateResult.get("avgWinRate");//平均胜率
        float avgLossRate = (Float) simulateResult.get("avgLossRate");//平均输率
          //年收益分布对比
        List<AnnualProfit> annualProfits=(List<AnnualProfit>)simulateResult.get("annualProfits");





        Map<String,Object> result = new HashMap<>();
        //将返回的allIndexDatas数据放进Map中
        result.put("indexDatas", allIndexDatas);
        result.put("indexStartDate", indexStartDate);
        result.put("indexEndDate", indexEndDate);
        result.put("profits", profits);
        result.put("trades",trades);
        result.put("years", years);
        result.put("indexIncomeTotal", indexIncomeTotal);
        result.put("indexIncomeAnnual", indexIncomeAnnual);
        result.put("trendIncomeTotal", trendIncomeTotal);
        result.put("trendIncomeAnnual", trendIncomeAnnual);


        result.put("winCount", winCount);
        result.put("lossCount", lossCount);
        result.put("avgWinRate", avgWinRate);
        result.put("avgLossRate", avgLossRate);

       result.put("annualProfits",annualProfits);



        return  result;
    }
    //日期需求

    /**
     *
     * @param allIndexDatas //开始日期结束日期之间的数据
     * @param strstartDate 开始日期
     * @param strEndDate 结束日期
     * @return：对于服务端没有时间数据就返回所有，否则就返回日期范围的
     *
     */
    private List<IndexData>filterByDateRange(List<IndexData>allIndexDatas,String strstartDate,String strEndDate){
       //如果开始日期或结束日期为空就不过滤了，直接返回所有数据
        if(StrUtil.isBlankOrUndefined(strstartDate)||StrUtil.isBlankOrUndefined(strEndDate)){
             return  allIndexDatas;
         }

         List<IndexData>result=new ArrayList<>();

         Date startDate=DateUtil.parseDate(strstartDate);
         Date endDate=DateUtil.parse(strEndDate);
         //遍历所有数据
         for(IndexData indexData :allIndexDatas){
             //把每个数据的日期拿出来
             Date date =DateUtil.parse(indexData.getDate());
             //如果日期处于开始日期与结束日期之间就满足条件添加result里面去
            if(
                    date.getTime()>=startDate.getTime() &&
                            date.getTime()<=endDate.getTime()
            ){
                result.add(indexData);
            }

         }
         return result;
    }

}
