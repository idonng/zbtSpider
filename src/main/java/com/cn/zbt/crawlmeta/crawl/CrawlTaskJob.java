/*package com.cn.zbt.crawlmeta.crawl;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.cn.zbt.crawlmeta.controller.Weixin;
import com.cn.zbt.crawlmeta.dm.ReadKeyword;
 
@Component
public class CrawlTaskJob  {
	private static final Logger logger = Logger.getLogger(CrawlTaskJob.class);
	   @Scheduled(cron = "0 0 5,15 * * ?")
	  
	    public void runCrawl(){
				logger.info("----爬取微信公众平台任务全部开始----" + new Date(System.currentTimeMillis()));
				 
				ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
				HashSet<String>  keywords=new ReadKeyword().getKeyword();
				for (final String keyword:keywords){
					fixedThreadPool.submit(new Runnable() {
						@Override
						public void run() {
							new Weixin().getDoc(keyword);
							}
					});
				}
				if(!fixedThreadPool.isShutdown()){
					fixedThreadPool.shutdown();}
				while (!fixedThreadPool.isTerminated()) {
				}  
				logger.info("----爬取微信公众平台任务全部结束----" + new Date(System.currentTimeMillis()));
			 }
}
*/