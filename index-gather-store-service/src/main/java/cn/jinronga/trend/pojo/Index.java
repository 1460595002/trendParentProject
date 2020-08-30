package cn.jinronga.trend.pojo;

import java.io.Serializable;
/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/1/31 0031
 * Time: 19:55
 * E-mail:1460595002@qq.com
 * 类说明：收集代码
 */
public class Index implements Serializable{
	String code;
	String name;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
