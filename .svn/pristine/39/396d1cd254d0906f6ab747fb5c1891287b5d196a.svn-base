package com.cn.zbt.crawlmeta.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.Ctext;
import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.dm.ReadFile;
import com.cn.zbt.crawlmeta.service.ResultTabSer;
public class HuaShang {
	private static final Logger logger = Logger.getLogger(HuaShang.class);
	 private static ResultTabSer resultTabService = (ResultTabSer) GetService		.getInstance().getService("resultTabService");
	/**
	 * 爬取华商网
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("finally")
	public Document fetch(String url,String q,String p) {
		Document doc = null;
		Connection conn = null;
		String s ="10178614232472326433";
		String entry="1";
		String nsid="1";
		try {
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
					.timeout(5000);
			conn.data("q", q).data("p", p).data("s", s).data("entry", entry).data("nsid", nsid);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_old(url);
			logger.error("访问华商网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}
	/**
	 * 爬取华商网
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("finally")
	public Document fetch(String url) {
		Document doc = null;
		Connection conn = null;
		try {
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
					.timeout(2000);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_old(url);
			logger.error("访问华商网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}

	public Document fetch_old(String Url) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(Url);
			InputStream in = null;
			connection = (HttpURLConnection) url.openConnection();
			// 模拟成ie
			connection
					.setRequestProperty(
							"user-agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Accept-Charset", "utf-8");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			connection
					.setRequestProperty("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.setReadTimeout(2000);
			connection.connect();
			in = connection.getInputStream();
			BufferedReader breader = new BufferedReader(new InputStreamReader(
					in, "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = breader.readLine()) != null) {
				sb.append(line);
			}
			Document doc = Jsoup.parse(sb.toString());
			return doc;
		} catch (IOException e) {
			logger.error("访问异常==========>", e);
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public void getDoc(String keyword) {
		int i = 0;
		String reg="";
		do {
			String url = "http://so.hsw.cn/cse/search";
			try {
				Document doc=new HuaShang().fetch(url,keyword,i+++"");
				//未搜索到结果
				if(doc.toString().contains("建议您尝试变换检索词")){
					return;
				}
				reg=doc.getElementById("pageFooter").text();;
				getData(doc, keyword);
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("解析错误URL:" +url+"。异常详情："+ e);
			}
			System.gc();
		} while (reg.contains("下一页"));
	}

	@SuppressWarnings({ "static-access"  })
	public void getData(Document doc, String keyword) {
		Date sinatime_now = new Date();
		Elements elements = doc.select(".result").select(".f").select(".s0");
		for (int p = 0; p < elements.size(); p++) {
			Element element = elements.get(p).getElementsByTag("a").first();
			String title = "";
			String content = "";
			String url = "";
			url = element.attr("href");
			logger.info("正在处理：" + url);
			if(new CommonUtils().checkUrlExist(url)){
				logger.info("已经处理，跳过URL：" + url);
				continue;
			}
			try {
				Document doc1 = new HuaShang().fetch(url);
				title = doc1.select("title").first().text().trim();
				//发布时间
				String ctStr=new CommonUtils().getRegex("((\\d{2}|((1|2)\\d{3}))(-|年)\\d{2}(-|月)\\d{2}(日|)( \\d{1,2}:\\d{1,2}(:\\d{1,2}|)|))",
						 doc1.toString().replace("\n", "")
							.replace("\r", "").replace("&nbsp;", " ")).trim();
				Date pubdate = new Date();
				// 转换各种格式的日期
				pubdate = (new CommonUtils().matchDateString(ctStr) == null ? sinatime_now
						: new CommonUtils().matchDateString(ctStr));
				Date inittime = new Date();
				inittime = new CommonUtils()
						.matchDateString("1970-01-01 08:00:01");
				pubdate = pubdate.compareTo(sinatime_now) > 0 ? sinatime_now : pubdate;
				pubdate = pubdate.compareTo(inittime) < 0 ? inittime : pubdate;
				Ctext ctx = new Ctext();
				content = ctx.deleteLabel(doc1.toString()).trim();
				Map<Integer, String> map = ctx.splitBlock(content);
				// 数据库字段超长、后续可修改
				if (ctx.judgeBlocks(map).length() > 9999) {
					content = ctx.judgeBlocks(map).substring(0, 9999);
				} else {
					content = ctx.judgeBlocks(map);
				}
				 String author = new CommonUtils().getRegex("来源:(.*?)<",
						 doc1.toString().replace("\n", "")
							.replace("\r", "").replace("&nbsp;", " ")).trim();
				  author = (author.length() == 0 || author == null) ? "华商网"
							: author;
				// 转发量，评论量。新闻稿有的不存在，需要后续处理，进行热度排序推送

				String zfs = "0";
				String pls = "0";

				resultTabService.insertRes(new CommonUtils().setMD5(url), title, url, content,
						Integer.valueOf(pls), Integer.valueOf(zfs),
						new Timestamp(pubdate.getTime()), keyword+"_华商网", author,
						new Timestamp(sinatime_now.getTime()),0);
				logger.info("URL:" + url  + "提取完成。");
			} catch (Exception e) {
				logger.error("解析错误URL:" +url+"。异常详情："+ e);
			}
		}
	}

	public void runInter() {
		List<String> keywords = new ArrayList<String>();
		keywords = new ReadFile().readKeyword();
		for (String keyword : keywords) {
			logger.info("----关键词:" + keyword + " 爬取开始----"
					+ new Date(System.currentTimeMillis()));
			getDoc(keyword.trim());
			logger.info("----关键词:" + keyword + " 爬取结束----"
					+ new Date(System.currentTimeMillis()));
		}
	}

	public static void main(String[] args) {
		logger.info("----爬取开始----" + new Date(System.currentTimeMillis()));

		HuaShang sw = new HuaShang();
		sw.runInter();
		logger.info("----全部主页爬取结束----" + new Date(System.currentTimeMillis()));
	}
}
