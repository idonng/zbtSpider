package com.cn.zbt.crawlmeta.crawl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

import com.cn.zbt.crawlmeta.controller.RenMin;
import com.cn.zbt.crawlmeta.controller.SinaWeibo;
import com.cn.zbt.crawlmeta.controller.HuaShang;
import com.cn.zbt.crawlmeta.controller.TianYa;
import com.cn.zbt.crawlmeta.controller.WangYi;
import com.cn.zbt.crawlmeta.dm.ReadFile;
public class CrawlJob implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(Crawl.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// do all the tasks that you need to perform just after the server
		// starts
		// Notification that the web application initialization process is
		// starting

		/*
		 * do{ runCrawl(); }while(true);
		 */
		   runCrawl();
		 
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Notification that the servlet context is about to be shut down.
		logger.info("----爬取任务被强制结束----" + new Date(System.currentTimeMillis()));
	}

	/**
	 * 1：新浪微博 2：华商网 3：天涯论坛 4：有道搜索（网易） 5：有道搜索（人民网）
	 */
	public void runCrawl() {
		 
		logger.info("----爬取任务全部开始----" + new Date(System.currentTimeMillis()));
		ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(5);
		List<String> keywords1 = new ArrayList<String>();
		keywords1 = new ReadFile().readKeyword();
		for (int i = 0; i < keywords1.size(); i++) {
			final String keyword = keywords1.get(i).trim();
			fixedThreadPool1.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					crawlJob(keyword, 1);
					System.gc();
				}
			});
		}
		ExecutorService fixedThreadPool2 = Executors.newFixedThreadPool(10);
		List<String> keywords2 = new ArrayList<String>();
		keywords2 = new ReadFile().readKeyword();
		for (int i = 0; i < keywords2.size(); i++) {
			final String keyword = keywords2.get(i).trim();
			fixedThreadPool2.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int j = 2; j <= 5; j++) {
						  crawlJob(keyword, j);
						System.gc();
					}
				}
			});
		}
		fixedThreadPool1.shutdown();
		fixedThreadPool2.shutdown();
		while (!fixedThreadPool1.isTerminated()
				|| !fixedThreadPool2.isTerminated()) {
		} 
	 
		logger.info("----爬取任务全部结束----" + new Date(System.currentTimeMillis()));
	 }
	public void crawlJob(String keyword, int m) {
		try {
			switch (m) {
			case 1:
				logger.info("----爬取新浪微博关键词:" + keyword + " 爬取开始----"
						+ new Date(System.currentTimeMillis()));
				new SinaWeibo().getDoc(keyword);
				logger.info("----爬取新浪微博关键词:" + keyword + " 爬取结束----"
						+ new Date(System.currentTimeMillis()));
				break;
			case 2:
				logger.info("----爬取华商网关键词:" + keyword + " 爬取开始----"
						+ new Date(System.currentTimeMillis()));
				new HuaShang().getDoc(keyword);
				logger.info("----爬取华商网关键词:" + keyword + " 爬取结束----"
						+ new Date(System.currentTimeMillis()));
				break;
			case 3:
				logger.info("----爬取天涯论坛关键词:" + keyword + " 爬取开始----"
						+ new Date(System.currentTimeMillis()));
				new TianYa().getDoc(keyword);
				logger.info("----爬取天涯论坛关键词:" + keyword + " 爬取结束----"
						+ new Date(System.currentTimeMillis()));
				break;
			case 4:
				logger.info("----爬取网易关键词:" + keyword + " 爬取开始----"
						+ new Date(System.currentTimeMillis()));
				new WangYi().getDoc(keyword);
				logger.info("----爬取网易关键词:" + keyword + " 爬取结束----"
						+ new Date(System.currentTimeMillis()));
				break;
			case 5:
				logger.info("----爬取人民网关键词:" + keyword + " 爬取开始----"
						+ new Date(System.currentTimeMillis()));
				new RenMin().getDoc(keyword);
				logger.info("----爬取人民网关键词:" + keyword + " 爬取结束----"
						+ new Date(System.currentTimeMillis()));
				break;
			default:
				break;
			}
			System.gc();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
