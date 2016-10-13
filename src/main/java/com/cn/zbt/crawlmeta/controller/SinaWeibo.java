package com.cn.zbt.crawlmeta.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.GetCookie;
import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.dm.ReadKeyword;
import com.cn.zbt.crawlmeta.dm.SetProxy;
import com.cn.zbt.crawlmeta.dm.SetUser;
import com.cn.zbt.crawlmeta.pojo.Message;
import com.cn.zbt.crawlmeta.pojo.UserTab;
import com.cn.zbt.crawlmeta.service.ResultTabSer;

/**
 * 连接新浪微博，下载document
 */
public class SinaWeibo {
	private static final Logger logger = Logger.getLogger(SinaWeibo.class);
	 private static ResultTabSer resultTabService = (ResultTabSer) GetService		.getInstance().getService("resultTabService");
	public String username = "";
	public String password = "";

	public SinaWeibo() {
		UserTab user = new SetUser().setUser();
		username = user.getUsername();
		password = user.getPassword();
	}

	/**
	 * 带cookie爬取新浪微博
	 * 
	 * @param url
	 * @param cookie
	 * @return
	 */
	public Document fetch(String url, String cookie, int i, String keyword) {
		String[] cook = cookie.split(";");
		Map<String, String> map = new HashMap<String, String>();
		for (String s : cook) {
			String[] coo = s.split("=");
			String key = coo[0];
			String value = coo[1];
			map.put(key, value);
		}
		Document doc = null;
		Connection loginConn = null;
		String smblog = "搜微博";
		new SetProxy().setIp();
		try {
			loginConn = Jsoup.connect(url).userAgent(Message.user_agent_mobile)
					.method(Method.POST).timeout(4000).ignoreContentType(true);
			loginConn.data("keyword", keyword).data("smblog", smblog)
					.data("page", i + "").cookies(map);
			;
			doc = loginConn.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(" 访问新浪微博异常==========>", e);
			logger.error("keyword为：" + keyword + ".page为：" + i);
			e.printStackTrace();
		}
		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getDoc(String keyword) {
		GetCookie gc = new GetCookie();
		String cookie = gc.getMCookies(username, password);
		String url = "http://weibo.cn/search/";
		Document doc = new SinaWeibo().fetch(url, cookie, 1, keyword);
		String reg = "/(\\d+)页";

		String content = doc.toString();
		if (doc.select("title").text().contains("微博广场")) {
			logger.debug("账号被冻结!请更换账户。");
			UserTab user = new SetUser().setUser();
			username = user.getUsername();
			password = user.getPassword();
			return;
		}
		Pattern p1 = Pattern.compile(reg);
		List matches1 = null;
		Matcher matcher1 = p1.matcher(content.replace("\n", "")
				.replace("\r", "").replace("&nbsp;", " "));
		if (matcher1.find() && matcher1.groupCount() >= 1) {
			matches1 = new ArrayList();
			for (int k = 1; k <= matcher1.groupCount(); k++) {
				String temp1 = matcher1.group(k);
				matches1.add(temp1);
			}
		} else {
			matches1 = Collections.EMPTY_LIST;
		}
		if (!matches1.isEmpty()) {
			String res = (String) matches1.get(0);
			logger.info("关键词" + keyword + "共" + Integer.valueOf(res) + "页");
			for (int i = 1; i < Integer.valueOf(res); i++) {
				// for(int i = 1;i<2;i++){
				try {
					getData(new SinaWeibo().fetch(url, cookie, i, keyword),
							keyword);
					Thread.sleep(10000);
				} catch (Exception e) {
					new SetProxy().setIp();
					logger.error("访问URL异常" + e + keyword + i);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.gc();
			}
		} else {
			try {
				getData(new SinaWeibo().fetch(url, cookie, 1, keyword), keyword);
				Thread.sleep(5000);
			} catch (Exception e) {
				logger.error("访问URL异常" + keyword);
				new SetProxy().setIp();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.gc();
		}

	}

	@SuppressWarnings("deprecation")
	public  void getData(Document doc, String keyword) {

		// 账号被封
		if (doc.select("title").text().contains("微博广场")) {
			logger.debug("账号被冻结!请更换账户。");
			UserTab user = new SetUser().setUser();
			username = user.getUsername();
			password = user.getPassword();
			return;
		}
		Date d = new Date();
		Elements elements = doc.getElementsByClass("c");
		String C_systime = "";
		String systime = "";
		C_systime = doc.getElementsByClass("b").get(0).text();// Sina.cn[09-06
																// 11:53]新浪系统时间
		systime = C_systime.substring(C_systime.indexOf("[") + 1,
				C_systime.indexOf("]"));// 09-06 11:53

		for (int p = 0; p < elements.size(); p++) {
			Element element = elements.get(p);
			if (element.hasAttr("id")) {
				/*
				 * if ((p < elements.size() - 1) && !elements.get(p + 1).text()
				 * .contains("设置")&&! elements.get(p + 1).text()
				 * .contains("彩版")) {
				 */
				if ((p < elements.size() - 1)
						&& !elements.get(p).text().contains("设置")
						&& !elements.get(p).text().contains("彩版")) {
					// 抓取时间fetchtime
					String sinatime_now = (d.getYear() + 1900) + "-" + systime;// 2016-05-06//
																				// 15:45:00
					String title = "";
					String content = "";
					String url = "";
					String author = "";
					String id = element.attr("id").substring(2);
					url = "http://weibo.cn/comment/" + id;
					logger.info("正在处理：" + url);
					String ctStr = element.getElementsByClass("ct").get(0)
							.text().replace("&nbsp;", " ");// 时间块
					Date pubdate = new Date();
					try {
						pubdate = getDate(ctStr, sinatime_now, d);
					} catch (Exception e) {
						logger.error("提取新浪微博时间异常");
					}
					String text1 = element.text();// 存档用
					content = element.select(".ctt").first().text();
					
					// content = text1.substring(text1.indexOf(":") + 1);
					author = element.select("a").first().text();// 作者

					// 抽取微博内容,用于情感倾向断定
					element.select("a").remove();
					element.select("img").remove();
					element.select(".ct").remove();
					// 转发数
					String zfs = text1.substring(
							text1.lastIndexOf(Message.sysm_zhuanfa) + 5,
							text1.lastIndexOf(Message.sysm_pinglun));
					// 评论数
					String pls = text1.substring(
							text1.indexOf(Message.sysm_pinglun) + 5,
							text1.indexOf(Message.sysm_shoucang));
					text1 = text1.substring(0, text1.lastIndexOf("赞["));
					/**
					 * 构造微博title,取前40字符
					 */
					if (content.length() > 40) {
						title = content.trim().substring(0, 39) + "...";
					} else {
						title = content.trim();
					}
					resultTabService.insertRes(new CommonUtils().setMD5(url),title.substring(1), url,
							content.substring(1), Integer.valueOf(pls),
							Integer.valueOf(zfs),
							new Timestamp(pubdate.getTime()), keyword+"_新浪微博", author,
							new Timestamp(string2Date(sinatime_now).getTime()),0);
					logger.info("URL:" + url + " " + "提取完成。");
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public Date getDate(String ctStr, String sinatime_now, Date d) {
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
			date = time;// 2012-06-04 14:31:56
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

	public String getType(String content) {
		return "";
	}

	public void runInter() {
		HashSet<String>  keywords1=new ReadKeyword().getKeyword();
		for (final String keyword:keywords1){
			getDoc(keyword);
			logger.info("----关键词:" + keyword + " 爬取结束----"
					+ new Date(System.currentTimeMillis()));
		}
	}

	public static void main(String[] args) {
		logger.info("----爬取开始----" + new Date(System.currentTimeMillis()));
		SinaWeibo sw = new SinaWeibo();
		 sw.runInter();
		 
		logger.info("----爬取结束----" + new Date(System.currentTimeMillis()));
	}
}
