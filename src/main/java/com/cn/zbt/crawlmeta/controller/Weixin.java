package com.cn.zbt.crawlmeta.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;

import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.ReadFile;
import com.cn.zbt.crawlmeta.pojo.Message;
import com.cn.zbt.crawlmeta.service.impl.ResultTabSerImpl;
public class Weixin {
	private static final Logger logger = Logger.getLogger(Weixin.class);
	public String username = Message.username;
	public String password = Message.password;

	public Map<String, HashSet<String>> clvmap = new HashMap<String, HashSet<String>>();

	@SuppressWarnings("deprecation")
	public void getDate(Document doc, String keyword) {

		Date d = new Date();
		Elements elements = doc.getElementsByClass("txt-box");

		for (int p = 0; p < elements.size(); p++) {

			Element element = elements.get(p).getElementsByTag("a").first();

			// 抓取时间 fetchtime
			String sinatime_now = (d.getYear() + 1900) + "-" + "08-25 00:00";// 2012-05-06//
																				// 15:45
			String title = "";
			String content = "";
			String url = "";
			String author = "";
			url = element.attr("href");
			System.out.println("正在处理：" + url);

			Document doc1 = new Weixin().sina(url);
			title = doc1.getElementById("activity-name").text();
			author = doc1.getElementById("post-user").text();
			String ctStr = doc1.getElementById("post-date").text();// 时间块
			Date pubdate = new Date();
			try {
				pubdate = getData(ctStr, sinatime_now, d);
			} catch (Exception e) {
				logger.error("提取新浪微博时间异常");
				System.out.println("提取新浪微博时间异常");
			}

			content = doc1.getElementById("js_content").text();
			/**
			 * 构造微博title,取前40字符
			 */

			// String zfs=doc1.getElementById("sg_readNum3").text();
			// String pls=doc1.getElementById("sg_likeNum3").text();
			String zfs = "0";
			String pls = "0";

			ResultTabSerImpl rs = new ResultTabSerImpl();
			rs.insertRes(new CommonUtils().setMD5(url),title, url, content, Integer.valueOf(pls),
					Integer.valueOf(zfs), new Timestamp(pubdate.getTime()),
					keyword, author, Timestamp.valueOf(sinatime_now),0);
			logger.info("URL:" + url + "\n" + "提取完成。");
			System.out.println("URL:" + url + "\n" + "提取完成。");

		}

	}

	@SuppressWarnings("deprecation")
	public Date getData(String ctStr, String sinatime_now, Date d) {

		// ctStr="02-14 22:25 来自微博 weibo.com";
		String date = "";
		String temp = " " + Message.sysm_from;
		String time = ctStr.trim();
		if (ctStr.contains("来自")) {
			time = ctStr.substring(0, ctStr.indexOf(temp));
		}
		// 当前年份
		if (time.contains(Message.sysm_month)) {
			int year = d.getYear();
			int month = Integer.valueOf(time.substring(0,
					time.indexOf(Message.sysm_month)));
			int day = Integer.valueOf(time.substring(
					time.indexOf(Message.sysm_month) + 1,
					time.indexOf(Message.sysm_day)));
			String de = time.substring(time.indexOf(" "));
			date = (1900 + year) + "-" + month + "-" + day + " " + de + ":00";
		}
		// 时间信息包含今天
		else if (time.contains(Message.sysm_today)) {
			int year = d.getYear();
			int month = d.getMonth();
			int day = d.getDate();
			String de = time.split(" ")[1];
			date = (1900 + year) + "-" + (month + 1) + "-" + day + " " + de
					+ ":00";
		}
		// 或者..分钟前
		else if (time.contains(Message.weibo_fenzhongqian)) {
			String py = time.substring(0,
					time.indexOf(Message.weibo_fenzhongqian));
			date = getPreTime(sinatime_now, "-" + py);
		} else {// 过去年份
			date = time + " 00:00:00";// 2012-06-04 14:31:56
		}

		return string2Date(date);
	}

	public String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	private Date string2Date(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date Dd = null;
		try {
			Dd = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Dd;
	}

	/**
	 * 带cookie爬取新浪微博
	 * 
	 * @param url
	 * @param cookie
	 * @return
	 */
	public Document sina(String url) {
		/*
		 * System.setProperty("http.maxRedirects", "50");
		 * System.getProperties().setProperty("proxySet", "true"); //
		 * 如果不设置，只要代理IP和代理端口正确,此项不设置也可以 String ip = "124.240.187.78";
		 * System.getProperties().setProperty("http.proxyHost", ip);
		 * System.getProperties().setProperty("http.proxyPort", "81");
		 */
		// TODO Auto-generated method stub

		Document doc = null;
		Connection conn = null;
		try {
			conn = Jsoup
					.connect(url)
					.timeout(40000)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
			doc = conn.get();

		} catch (IOException e) {
			// doc = sina_old(url, cookie);
			logger.error("jsoup访问微信异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			if (conn != null) {
			}
		}
		return doc;
	}

	public void getDoc(String keyword) {
		String url = "http://weixin.sogou.com/weixin?type=2&ie=utf8&query="
				+ keyword + "&page=" + 1;
		Document doc = new Weixin().sina(url);
		if (doc.toString().contains("您的访问过于频繁")) {
			String ip = doc.getElementsByClass("ip-time-p").first().text();
			logger.info("访问过于频繁.   " + ip);
			return;
		}
		Element element = doc.getElementById("scd_num");

		int reg = Integer.valueOf(element.text().replace(",", ""));

		if (doc.select("title").text().contains("微博广场")) {
			System.out.println("账号被冻结!请更换账户。");
			return;
		}
		logger.info("----关键词:" + keyword + "共" + (reg / 10 + 1) + "页,共" + reg
				+ "条");
		String url1 = url.substring(0, url.length() - 1);
		for (int i = 1; i < (reg / 10 + 1 > 10 ? 10 : reg / 10 + 1); i++) {
			// for(int i = 1;i<2;i++){
			try {
				url = url1 + i;
				getDate(new Weixin().sina(url), keyword);
				// if(!getDate(doc))
				// {
				// break;
				// };
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.gc();
		}
	}

	public void runInter() {
		List<String> keywords = new ArrayList<String>();
		keywords = new ReadFile().readKeyword();
		for (String keyword : keywords) {
			getDoc(keyword);
			logger.info("----关键词:" + keyword + " 爬取结束----"
					+ new Date(System.currentTimeMillis()));
			System.out.println("----关键词:" + keyword + " 爬取结束----"
					+ new Date(System.currentTimeMillis()));
		}
	}

	public static void main(String[] args) {
		logger.info("----爬取开始----" + new Date(System.currentTimeMillis()));
		Weixin sw = new Weixin();
		sw.runInter();
		logger.info("----全部主页爬取结束----" + new Date(System.currentTimeMillis()));
	}
}
