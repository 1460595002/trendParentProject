package cn.jinronga.trend.job;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.jinronga.trend.pojo.Index;
import cn.jinronga.trend.service.IndexDataService;
import cn.jinronga.trend.service.IndexService;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/1 0001
 * Time: 20:43
 * E-mail:1460595002@qq.com
 * 类说明:同时刷新指数代码与指数数据
 */
public class IndexDataSyncJob extends QuartzJobBean {
	



	@Autowired
	private IndexService indexService;

	@Autowired
	private IndexDataService indexDataService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.println("定时启动：" + DateUtil.now());
		List<Index> indexes = indexService.fresh();
		for (Index index : indexes) {
			 indexDataService.fresh(index.getCode());
		}
		System.out.println("定时结束：" + DateUtil.now());

	}

}
