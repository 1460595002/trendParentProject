package cn.jinronga.trend.config;

import cn.jinronga.trend.job.IndexDataSyncJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/1 0001
 * Time: 20:49
 * E-mail:1460595002@qq.com
 * 类说明:定时器配置
 */
@Configuration//配置类
public class QuartzConfiguration {
	//定时器配置，每隔一分钟执行一次。 其实每隔一分钟实在太紧密了，
	// 4个小时一次就很不错了。 这里用一分钟，是为了便于重复执行的观察效果。
	private static final int interval = 1;

	@Bean
	public JobDetail weatherDataSyncJobDetail() {
		return JobBuilder.newJob(IndexDataSyncJob.class).withIdentity("indexDataSyncJob")
		.storeDurably().build();
	}
	
	@Bean
	public Trigger weatherDataSyncTrigger() {
		SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInMinutes(interval).repeatForever();
		
		return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
				.withIdentity("indexDataSyncTrigger").withSchedule(schedBuilder).build();
	}
}
