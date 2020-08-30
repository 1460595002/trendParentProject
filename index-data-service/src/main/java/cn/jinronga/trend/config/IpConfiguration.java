package cn.jinronga.trend.config;
 
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/2 0002
 * Time: 16:50
 * E-mail:1460595002@qq.com
 * 类说明:用于获取不同的端口号，因为要做成集群，不同的服务拥有不同的端口号
 */
@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {
 
    private int serverPort;
 
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }
 
    public int getPort() {
        return this.serverPort;
    }
}