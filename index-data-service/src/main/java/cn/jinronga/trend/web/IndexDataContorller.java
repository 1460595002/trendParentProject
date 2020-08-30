package cn.jinronga.trend.web;

import cn.jinronga.trend.config.IpConfiguration;
import cn.jinronga.trend.pojo.IndexData;
import cn.jinronga.trend.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/2 0002
 * Time: 18:15
 * E-mail:1460595002@qq.com
 * 类说明:控制器调用indexDataService的get方法
 */
@RestController
public class IndexDataContorller {

    @Autowired
    IndexDataService indexDataService;
    @Autowired
    IpConfiguration ipConfiguration;

//  http://127.0.0.1:8021/data/000300  获取指数数据


    //访问data/{code}就调用get方法(获取redis的指数数据)并打印使用的端口号
    @GetMapping("/data/{code}")
    public List<IndexData>get(@PathVariable("code")String code)throws  Exception{

        System.out.println("当前实例是 :" + ipConfiguration.getPort());
        return indexDataService.get(code);
    }


}
