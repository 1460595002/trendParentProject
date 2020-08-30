package cn.jinronga.trend.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/11 0011
 * Time: 14:51
 * E-mail:1460595002@qq.com
 * 类说明:年收益
 */
public class AnnualProfit {
    //年份
    private int year;
    //指数收益
    private float indexIncome;
    //趋势收益
    private  float trendIncome;


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getIndexIncome() {
        return indexIncome;
    }

    public void setIndexIncome(float indexIncome) {
        this.indexIncome = indexIncome;
    }

    public float getTrendIncome() {
        return trendIncome;
    }

    public void setTrendIncome(float trendIncome) {
        this.trendIncome = trendIncome;
    }

    @Override
    public String toString() {
        return "AnnualProfit{" +
                "year=" + year +
                ", indexIncome=" + indexIncome +
                ", trendIncome=" + trendIncome +
                '}';
    }
}
