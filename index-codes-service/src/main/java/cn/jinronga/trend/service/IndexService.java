package cn.jinronga.trend.service;

import java.util.List;

import cn.jinronga.trend.pojo.Index;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;

/**
 *获取指数代码
 *
 */

@Service
@CacheConfig(cacheNames="indexes")//在redis中的名称是indexes
public class IndexService {
	private List<Index> indexes;

    @Cacheable(key="'all_codes'")  //在Redis中key是all_codes
    public List<Index> get(){ //获取指数代码，没有数据就设置如下
    	Index index = new Index();
    	index.setName("无效指数代码");
    	index.setCode("000000");
    	return CollUtil.toList(index);
    }
}
