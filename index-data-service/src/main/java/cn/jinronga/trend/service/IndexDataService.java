package cn.jinronga.trend.service;

import java.util.List;

import cn.jinronga.trend.pojo.IndexData;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/2 0002
 * Time: 17:12
 * E-mail:1460595002@qq.com
 * 类说明：服务类从redis中获取数据
 */
@Service
@CacheConfig(cacheNames="index_datas")//对应的redis缓存是index_datas
public class IndexDataService {

    @Cacheable(key="'indexData-code-'+ #p0")//redis数据中的key
    public List<IndexData> get(String code){
    	return CollUtil.toList();
    }
}
