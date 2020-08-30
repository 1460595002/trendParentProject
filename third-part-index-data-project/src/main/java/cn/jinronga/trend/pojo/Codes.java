package cn.jinronga.trend.pojo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/8/30 0030
 * Time: 17:07
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class Codes implements Serializable {
    private Integer id;
    private String name;

    private String  code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
