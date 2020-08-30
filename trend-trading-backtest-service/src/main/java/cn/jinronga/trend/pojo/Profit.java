package cn.jinronga.trend.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/8 0008
 * Time: 22:07
 * E-mail:1460595002@qq.com
 * 类说明:利润类 与IndexDate比较
 */
public class Profit {
    String Date;
    float value;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
