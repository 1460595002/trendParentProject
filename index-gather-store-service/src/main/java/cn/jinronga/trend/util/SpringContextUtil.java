package cn.jinronga.trend.util;
 
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/1 0001
 * Time: 15:10
 * E-mail:1460595002@qq.com
 * 类说明:工具类，用于获取 IndexService
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
     
    private SpringContextUtil() {
         System.out.println("SpringContextUtil()");
    }
     
    private static ApplicationContext applicationContext;
     
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
    	System.out.println("applicationContext:"+applicationContext);
        SpringContextUtil.applicationContext = applicationContext;
    }
     
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
 
}