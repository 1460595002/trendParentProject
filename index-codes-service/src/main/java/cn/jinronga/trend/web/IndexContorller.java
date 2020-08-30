package cn.jinronga.trend.web;

import cn.jinronga.trend.config.IpConfiguration;
import cn.jinronga.trend.pojo.Index;
import cn.jinronga.trend.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/2 0002
 * Time: 15:06
 * E-mail:1460595002@qq.com
 * 类说明:返回代码集合，，并通过 IpConfiguration 获取当前接口并打印。
 */
@RestController
public class IndexContorller {
    @Autowired
    IndexService indexService;

    @Autowired
    IpConfiguration ipConfiguration;

//  http://127.0.0.1:8011/codes 获取指数数据

    @GetMapping("/codes")//访问codes调用indexSercice的get方法(获取指数代码)
    @CrossOrigin //允许跨域，因为后续的回测视图是另一个端口的
    public List<Index>codes()throws  Exception{

        System.out.println("当前实例的端口是："+ ipConfiguration.getPort());//打印端口号
        return indexService.get();
    }

}
