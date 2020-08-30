package cn.jinronga.trend.client;

import cn.hutool.core.collection.CollectionUtil;
import cn.jinronga.trend.pojo.IndexData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/4 0004
 * Time: 21:17
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Component
public class IndexDataClientFeignHystrix implements IndexDataClient{
    //当IndexDataClient获取不了数据，就会调用该方法
    @Override
    public List<IndexData> getIndexData(String code) {
        IndexData indexData = new IndexData();
        indexData.setClosePoint(0);
        indexData.setDate("0000-00-00");
        return CollectionUtil.toList(indexData);
    }

}
