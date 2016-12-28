 package com.cn.zbt.crawlmeta.crawl;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.cn.zbt.crawlmeta.controller.Crawl360;
import com.cn.zbt.crawlmeta.controller.Crawl360news;
import com.cn.zbt.crawlmeta.controller.RenMin;
import com.cn.zbt.crawlmeta.controller.SinaWeibo;
import com.cn.zbt.crawlmeta.controller.HuaShang;
import com.cn.zbt.crawlmeta.controller.TianYa;
import com.cn.zbt.crawlmeta.controller.WangYi;
import com.cn.zbt.crawlmeta.controller.Weixin;
import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.dm.ReadKeyword;
public class CrawlJob implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(CrawlJob.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// do all the tasks that you need to perform just after the server
		// starts
		// Notification that the web application initialization process is
		// starting
 
		do {
			runCrawl();
			try {
				logger.info("停止等待2分钟");
				Thread.sleep(120000);
				logger.info("结束等待2分钟");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GetService.clearInstance();
		} while (true);
		 
		 //  runCrawl();
		 
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Notification that the servlet context is about to be shut down.
		logger.info("----爬取任务被强制结束----" + new Date(System.currentTimeMillis()));
	}

	 /* 
	 * 1：360  2：华商网 3：天涯论坛 4：有道搜索（网易） 5：有道搜索（人民网）6：新浪微博  7：微信
	 */ 
	public void runCrawl() {
		 
		logger.info("----爬取任务全部开始----" + new Date(System.currentTimeMillis()));
		ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(10);
		HashSet<String>  keywords1=new ReadKeyword().getKeyword();
		for (final String keyword:keywords1){
			fixedThreadPool1.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					crawlJob(keyword, 1);
					//System.out.println(keyword);
					System.gc();
				}
			});
		} 
		ExecutorService fixedThreadPool2 = Executors.newFixedThreadPool(10);
		HashSet<String>  keywords2=new ReadKeyword().getKeyword();
		for (final String keyword:keywords2){
			fixedThreadPool2.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int j = 2; j <= 5; j++) {
						  crawlJob(keyword, j);
						//System.out.println(keyword);
						System.gc();
					}
				}
			});
		}
		ExecutorService fixedThreadPool3 = Executors.newFixedThreadPool(1);
		HashSet<String>  keywords3=new ReadKeyword().getKeyword();
		for (final String keyword:keywords3){
			fixedThreadPool3.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int j = 6; j <=6; j++) {
						crawlJob(keyword, j);
						//System.out.println(keyword);
						System.gc();
					}
				}
			});
		}
		if(!fixedThreadPool1.isShutdown()){
			fixedThreadPool1.shutdown();}
		if(!fixedThreadPool2.isShutdown()){
			fixedThreadPool2.shutdown();}
		if(!fixedThreadPool3.isShutdown()){
			fixedThreadPool3.shutdown();}
		while (!fixedThreadPool1.isTerminated()
				|| !fixedThreadPool2.isTerminated()||!fixedThreadPool3.isTerminated()) {
		}  
		logger.info("----爬取任务全部结束----" + new Date(System.currentTimeMillis()));
		
	 }
	public void crawlJob(String keyword, int m) {
		try {
			switch (m) {
			case 1:
				
				new Crawl360().getDoc(keyword);
				
				break;
			case 2:
				
				new HuaShang().getDoc(keyword);
				
				break;
			case 3:
				
				new TianYa().getDoc(keyword);
				
				break;
			case 4:
				
				new WangYi().getDoc(keyword);
				
				break;
			case 5:
				logger.info("----爬取人民网关键词:" + keyword + " 爬取开始----"
						+ new Date(System.currentTimeMillis()));
				new RenMin().getDoc(keyword);
				logger.info("----爬取人民网关键词:" + keyword + " 爬取结束----"
						+ new Date(System.currentTimeMillis()));
				break;
			case 6:
				logger.info("----爬取新浪微博关键词:" + keyword + " 爬取开始----"
						+ new Date(System.currentTimeMillis()));
				new SinaWeibo().getDoc(keyword);
				logger.info("----爬取新浪微博关键词:" + keyword + " 爬取结束----"
						+ new Date(System.currentTimeMillis()));
				break;
				
			case 7:
				logger.info("----爬取微信关键词:" + keyword + " 爬取开始----"
						+ new Date(System.currentTimeMillis()));
				new Weixin().getDoc(keyword);
				logger.info("----爬取微信关键词:" + keyword + " 爬取结束----"
						+ new Date(System.currentTimeMillis()));
				break;
			default:
				break;
			}
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
 