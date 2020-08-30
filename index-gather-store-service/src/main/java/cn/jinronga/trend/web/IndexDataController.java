package cn.jinronga.trend.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cn.jinronga.trend.pojo.IndexData;
import cn.jinronga.trend.service.IndexDataService;
/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/1 0001
 * Time: 19:57
 * E-mail:1460595002@qq.com
 * 类说明:控制器分别调用IndexDataService方法
 */
@RestController
public class IndexDataController {
	@Autowired
	IndexDataService indexDataService;
//  http://127.0.0.1:8001/freshIndexData/000300
//  http://127.0.0.1:8001/getIndexData/000300
//  http://127.0.0.1:8001/removeIndexData/000300


	//访问freshIndexData/{code}的时候调用indexDataService的fresh方法（刷新数据）并return刷新数据成功！
	@GetMapping("/freshIndexData/{code}")
	public String fresh(@PathVariable("code")String code)throws  Exception{
		indexDataService.fresh(code);
		return "刷新数据成功！";

	}
	//访问getIndexData/{code}的时候调用indexDataservice的get方法（从redis中获取数据）
	@GetMapping("/getIndexData/{code}")
	public List<IndexData>get(@PathVariable("code")String code)throws Exception{
		return indexDataService.get(code);
	}

	//访问removeIndexData/{code}的时候调用indexDataService的remove方法（清空数据）并return清空数据成功！
	@GetMapping("/removeIndexData/{code}")
	public String remove(@PathVariable("code") String code)throws Exception{
		indexDataService.remove(code);
		return "清空数据成功！";
	}


}
