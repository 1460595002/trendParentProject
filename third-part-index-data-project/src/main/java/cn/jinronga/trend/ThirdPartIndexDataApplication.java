package cn.jinronga.trend;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/1/30 0030
 * Time: 22:16
 * E-mail:1460595002@qq.com
 * 类说明:第三方数据服务
 */
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //@EnableEurekaClient 表示注册为微服务
public class ThirdPartIndexDataApplication {
    public static void main(String[] args) {
        int port = 8090;
        int eurekaServerPort = 8761;

        if(NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("检查到端口%d 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出%n", eurekaServerPort );
            System.exit(1);
        }
        /**
         * 这一段就表示启动的时候如果带了参数，如： port=8099, 那么程序就会使用 8099 作为端口号了。
         * 这样做的好处是，什么代码都不用改，只需要在启动的时候带不同的参数，就可以启动不同的端口了。
         */
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
        new SpringApplicationBuilder(ThirdPartIndexDataApplication.class).properties("server.port=" + port).run(args);

    }

}