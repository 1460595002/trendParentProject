package cn.jinronga.trend.client;

import cn.jinronga.trend.pojo.IndexData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/4 0004
 * Time: 20:40
 * E-mail:1460595002@qq.com
 * 类说明:
 */
//使用feign模式从 INDEX-DATA-SERVICE 微服务获取数据。 如果访问不了的时候，就去找 IndexDataClientFeignHystrix 要数据了。
@FeignClient(value = "INDEX-DATA-SERVICE",fallback = IndexDataClientFeignHystrix.class)
public interface IndexDataClient {
    @GetMapping("/data/{code}")
    public List<IndexData>getIndexData(@PathVariable("code") String code);



}
