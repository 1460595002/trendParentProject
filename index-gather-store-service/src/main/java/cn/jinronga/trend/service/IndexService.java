package cn.jinronga.trend.service;

import cn.jinronga.trend.pojo.Index;
import cn.jinronga.trend.util.SpringContextUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/1/31 0031
 * Time: 20:10
 * E-mail:1460595002@qq.com
 * 类说明:IndexService 服务类
 *
 * ，使用工具类RestTemplate 来获取如下地址：http://127.0.0.1:8090/indexes/codes.json
 */
@Service
@CacheConfig(cacheNames="indexes")//表示缓存名称是indexs 保存到redis以indexs命名
public class IndexService {
	private List<Index> indexes;
	@Autowired RestTemplate restTemplate;

	//实现断路器功能
	@HystrixCommand(fallbackMethod = "third_part_not_connected")
	public List<Index> fresh() {
		/**
		 * 这里用了SpringContextUtil.Bean去重新获取IndexService为什么不直接在 fresh 方法里调用 remove, store 方法，
		 * 而要重新获取一次呢？ 这个是因为当前 springboot 的机制大概有这么个 bug吧.
		 从已经存在的方法里调用 redis 相关方法，并不能触发redis 相关操作，所以只好用这种方式重新获取一次了。
		 所以通过以下方式就能进行保存了
		 */

		indexes =fetch_indexes_from_third_part();//不管redis有没有数据都会从第三方中获取数据
		//使用SpringContextUtil.getBean才能出发缓存的操作
		IndexService indexService = SpringContextUtil.getBean(IndexService.class);
		//获取数据之后就把数据给删除
		indexService.remove();
		//删除之后就进行保存
		return indexService.store();
	}
	//从redis中清空数据    这个注解@CacheEvict(allEntries = true)就把数据清空了
	@CacheEvict(allEntries=true)
	public void remove(){

	}
	//保存数据到redis
	@Cacheable(key="'all_codes'")
	public List<Index> store(){
		return indexes;//indexes是一个成员变量
	}
	//专门从redis中获取数据
	@Cacheable(key="'all_codes'")
	public List<Index> get(){
		return CollUtil.toList();
	}

	/* //如果fetch_indexes_from_third_part数据获取失败了，就会抛出异常，那么就会被@HystrixCommand捕获就会调用third_part_not_connected
     @HystrixCommand(fallbackMethod = "third_part_not_connected")
     //在redis中保存到indexs的key会是all_codes
     @Cacheable(key="'all_codes'")*/
	public List<Index> fetch_indexes_from_third_part(){
		/**
		 * IndexService 服务类，使用工具类RestTemplate 来获取如下地址：
		 http://127.0.0.1:8090/indexes/codes.json
		 */

		List<Map> temp= restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json",List.class);
		return map2Index(temp);
	}
	//获取出来的内容是Map类型，所以需要个 map2Index把 Map 转换为 Index。
	private List<Index> map2Index(List<Map> temp) {
		List<Index> indexes = new ArrayList<>();
		for (Map map : temp) {
			String code = map.get("code").toString();
			String name = map.get("name").toString();
			Index index= new Index();
			index.setCode(code);
			index.setName(name);
			indexes.add(index);
		}

		return indexes;
	}
	// 当采集数据失败了不抛出异常调用该的方法
	public List<Index> third_part_not_connected(){
		System.out.println("third_part_not_connected()");
		Index index= new Index();
		index.setCode("000000");
		index.setName("无效指数代码");
		return CollectionUtil.toList(index);
	}

}
