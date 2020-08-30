package cn.jinronga.trend.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/4 0004
 * Time: 20:27
 * E-mail:1460595002@qq.com
 * 类说明:回测服务
 */
public class IndexData {

    String date;
    float closePoint;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getClosePoint() {
        return closePoint;
    }

    public void setClosePoint(float closePoint) {
        this.closePoint = closePoint;
    }
}
