package cn.jinronga.trend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cn.jinronga.trend.pojo.IndexData;
import cn.jinronga.trend.util.SpringContextUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/1 0001
 * Time: 19:07
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Service //服务类
@CacheConfig(cacheNames="index_datas")//缓存名称index_datas，在redis中表示index_datas
public class IndexDataService {
	private Map<String, List<IndexData>> indexDatas=new HashMap<>();
	@Autowired RestTemplate restTemplate;


	//实现短路器功能
	@HystrixCommand(fallbackMethod = "third_part_not_connected")
	public List<IndexData> fresh(String code) {
		List<IndexData> indexeDatas =fetch_indexes_from_third_part(code);
		
		indexDatas.put(code, indexeDatas);
		
//		System.out.println("code:"+code);
//		System.out.println("indexeDatas:"+indexDatas.get(code).size());

		//使用SpringContextUtil.getBean才能出发缓存的操作
		IndexDataService indexDataService = SpringContextUtil.getBean(IndexDataService.class);
		//清空redis的数据
		indexDataService.remove(code);
		//保存
		return indexDataService.store(code);
	}


	//从redis中清空数据
	@CacheEvict(key="'indexData-code-'+ #p0")
	public void remove(String code){
    	
    }

	//保存数据到redis
	@CachePut(key="'indexData-code-'+ #p0")
	public List<IndexData> store(String code){
    	return indexDatas.get(code);
    }

	//从redis中获取数据
    @Cacheable(key="'indexData-code-'+ #p0")
    public List<IndexData> get(String code){
    	return CollUtil.toList();
    }


    //获取数据
	public List<IndexData> fetch_indexes_from_third_part(String code){

		/**
		 * IndexService 服务类，使用工具类RestTemplate 来获取如下地址：
		 http://127.0.0.1:8090/indexes/
		 */
		List<Map> temp= restTemplate.getForObject("http://127.0.0.1:8090/indexes/"+code+".json",List.class);
    	return map2IndexData(temp);
	}


	//获取出来的内容是Map类型，所以需要个 map2Index把 Map 转换为 IndexData。
	private List<IndexData> map2IndexData(List<Map> temp) {
		List<IndexData> indexDatas = new ArrayList<>();
		for (Map map : temp) {
			String date = map.get("date").toString();
			float closePoint = Convert.toFloat(map.get("closePoint"));
			IndexData indexData = new IndexData();
			
			indexData.setDate(date);
			indexData.setClosePoint(closePoint);
			indexDatas.add(indexData);
		}
		
		return indexDatas;
	}
	// 当采集指数数据失败了不抛出异常调用该的方法
    public List<IndexData> third_part_not_connected(String code){
    	System.out.println("third_part_not_connected()");
    	IndexData index= new IndexData();
    	index.setClosePoint(0);
    	index.setDate("n/a");
    	return CollectionUtil.toList(index);
    }
		
	
}
