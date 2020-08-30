package cn.jinronga.trend.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/10 0010
 * Time: 11:03
 * E-mail:1460595002@qq.com
 * 类说明:记录交易记录购买的日期，出售的日期，购买盘点，出售盘点，收益。
 */
public class Trade {

    private String byDate; //购买日期
    private String sellDate; //出售日期
    private float buyClosePoint; //收盘点
    private float sellClosePoint; //购买的指数
    private float rate; //收益

    public String getByDate() {
        return byDate;
    }

    public void setByDate(String byDate) {
        this.byDate = byDate;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public float getBuyClosePoint() {
        return buyClosePoint;
    }

    public void setBuyClosePoint(float buyClosePoint) {
        this.buyClosePoint = buyClosePoint;
    }

    public float getSellClosePoint() {
        return sellClosePoint;
    }

    public void setSellClosePoint(float sellClosePoint) {
        this.sellClosePoint = sellClosePoint;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
