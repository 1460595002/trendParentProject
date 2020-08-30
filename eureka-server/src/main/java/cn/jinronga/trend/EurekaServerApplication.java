package cn.jinronga.trend;
  
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
  
import cn.hutool.core.util.NetUtil;
/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/1/30 0030
 * Time: 19:33
 * E-mail:1460595002@qq.com
 * 类说明:服务注册中心
 */
  
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
      
    public static void main(String[] args) {
        //8761 这个端口是默认的，就不要修改了，后面的子项目，都会访问这个端口。
        int port = 8761;
        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(EurekaServerApplication.class).properties("server.port=" + port).run(args);
    }
}