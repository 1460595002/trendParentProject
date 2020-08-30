package cn.jinronga.trend;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/1/31 0031
 * Time: 19:52
 * E-mail:1460595002@qq.com
 * 类说明:采集存储微服务
 */
@SpringBootApplication  //springboot应用
@EnableEurekaClient //注册Eureka服务
@EnableHystrix  //启动断路器
@EnableCaching //表示启动缓存
public class IndexGatherStoreApplication {
    public static void main(String[] args) {


        int defaultPort = 8001;
        int port=defaultPort;
        int redisPort=6379;
        int eurekaServerPort = 8761;
        port = defaultPort ;

        //判断eureka是否启动
        if(NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("检查到端口%d 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出%n", eurekaServerPort );
            System.exit(1);
        }

        //判断redis客户端是否启动
        if(NetUtil.isUsableLocalPort(redisPort)){
            System.err.printf("检查到端口%d 未启用，判断 redis 服务器没有启动，本服务无法使用，故退出%n", redisPort );
            System.exit(1);
        }

        if(null!=args && 0!=args.length) {
            for (String arg : args) {
                if(arg.startsWith("port=")) {
                    String strPort= StrUtil.subAfter(arg, "port=", true);
                    if(NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(IndexGatherStoreApplication.class).properties("server.port=" + port).run(args);

    }
    //这样才能使用RestTemplate
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}