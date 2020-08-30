package cn.jinronga.trend.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jinronga.trend.pojo.Index;
import cn.jinronga.trend.service.IndexService;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/1/31 0031
 * Time: 20:58
 * E-mail:1460595002@qq.com
 * 类说明:控制器分别调用IndexService的方法
 */
@RestController
public class IndexController {
	@Autowired IndexService indexService;


	//  http://127.0.0.1:8001/freshCodes  刷新
	//  http://127.0.0.1:8001/getCodes    获取
	//  http://127.0.0.1:8001/removeCodes  清空数据

	//访问freshCodes的时候，调用IndexService的fresh方法(刷新数据)
	@GetMapping("/freshCodes")
	public String fresh() throws Exception {
		indexService.fresh();
		return "刷新数据成功";
	}
	//访问 getCodes的时候， 调用 IndexService的get() 方法(获取redis中的数据)
	@GetMapping("/getCodes")
	public List<Index> get() throws Exception {
		return indexService.get();
	}
	//访问 removeCodes的时候调用IndexService的remove方法(清空数据)并return提示信息:清空数据成功!
	@GetMapping("/removeCodes")
	public String remove() throws Exception {
		indexService.remove();
		return "清空数据成功!";
	}
}

