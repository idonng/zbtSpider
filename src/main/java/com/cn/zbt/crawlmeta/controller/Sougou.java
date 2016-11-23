package com.cn.zbt.crawlmeta.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSession;
import org.jsoup.Connection;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.Ctext;
import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.dm.ReadKeyword;
import com.cn.zbt.crawlmeta.dm.SetProxy;
import com.cn.zbt.crawlmeta.service.ResultTabSer;

public class Sougou {
	private static final Logger logger = Logger.getLogger(Sougou.class);
	private static ResultTabSer resultTabService = (ResultTabSer) GetService
			.getInstance().getService("resultTabService");
	/**
	 * 爬取sougou
	 * 
	 * @param url
	 *            q :关键字  :页码
	 * @return
	 */
	@SuppressWarnings("finally")
	public Document fetch(String url, String q, String p) {
		Document doc = null;
		Connection conn = null;
		try {
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
			conn.data("query", q).data("_asf","news.sogou.com").data("_ast","1479692032")
					 .data("page", p).timeout(5000);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_old(url);
			logger.error("jsoup访问搜狗网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}

	/**
	 * 爬取人民网
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
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
					.timeout(5000);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_old(url);
			logger.error("jsoup访问搜狗网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}

	@SuppressWarnings("finally")
	public Document fetch_old(String url) {
		Document doc = null;
		Connection conn = null;
		new SetProxy().setIp();
		try {
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
					.timeout(5000);
			doc = conn.get();
		} catch (IOException e) {
			logger.error("jsoup访问搜狗网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}

	public void getDoc(String keyword) {
		int i = 1;
		String reg = "";
		do {
			if(i<0){
				return;
			}
			String url = "http://news.sogou.com/news";
			try {
				Document doc = new Sougou().fetch(url, keyword, i++ + "");
				reg = doc.getElementById("pagebar_container").text();
				getData(doc, keyword);
				Thread.sleep(10000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				i--;
				logger.error("解析错误URL:" + url + "。异常详情：" + e);
			}
			System.gc();
		} while (reg.contains("下一页"));
	}

	public void getData(Document doc, String keyword) {
		Date sinatime_now = new Date();
		Elements elements = doc.select(".vrwrap>div>h3");
		System.out.println(elements.size());
		System.out.println(elements.html());
		for (int p = 0; p < elements.size(); p++) {
			Element element = elements.get(p).getElementsByTag("a").first();
			String title = "";
			String content = "";
			String url = "";
			String author = "搜狗";
			url = element.attr("href");
			logger.info("正在处理：" + url);
			if (CommonUtils.checkUrlExist(url)) {
				logger.info("已经处理，跳过URL：" + url);
				continue;
			}
			if (url.contains(".wml") || url.length() == 0) {
				logger.info("跳过不处理wml：" + url);
				continue;
			}
			 try {Document doc1 = new Weixin().fetch(url);
				title = doc1.select("title").first().text().trim();
				String ctStr = "";
				  ctStr = CommonUtils.getRegexTime( 
						Ctext.deleteLabel(doc1.toString())).trim();
				Date pubdate = new Date();
				// 转换各种格式的日期
				pubdate = (CommonUtils.matchDateString(ctStr) == null ? sinatime_now
						:CommonUtils.matchDateString(ctStr));
				pubdate = pubdate.compareTo(sinatime_now) > 0 ? sinatime_now
						: pubdate;
				content = Ctext.deleteLabel(doc1.toString()).trim();
				Map<Integer, String> map = Ctext.splitBlock(content);
				// 数据库字段超长、后续可修改
				if (Ctext.judgeBlocks(map).length() > 9999) {
					content = Ctext.judgeBlocks(map).substring(0, 9999);
				} else {
					content = Ctext.judgeBlocks(map);
				}
				String regex2 = "来源:(.*?)<";
		 
				author =CommonUtils.getRegex(regex2,
						Ctext.deleteLabel(doc1.toString())).trim();
				author = (author.length() == 0 || author == null) ? CommonUtils
						.getHost(url) : author;
				// 转发量，评论量。新闻稿有的不存在，需要后续处理，进行热度排序推送

				String zfs = "0";
				String pls = "0";
				int type = 2;
				if (url.contains("bbs")) {
					type = 3;
				} else if (url.contains("blog")) {
					type = 4;
				} else if (url.contains("weibo")) {
					type = 1;
				}
				resultTabService.insertRes(CommonUtils.setMD5(url),
						title, url, content, CommonUtils.getHost(url), type, keyword,
						Integer.valueOf(pls), Integer.valueOf(zfs), pubdate,
						sinatime_now, author, sinatime_now);
				logger.info("URL:" + url + " 提取完成。");} catch (Exception e) {
				logger.error("解析错误URL:" + url + "。异常详情：" + e);
			} 
		}
	}

	public void runInter() {
		HashSet<String> keywords1 = new ReadKeyword().getKeyword();
		for (final String keyword : keywords1) {
			logger.info("----关键词:" + keyword + " 爬取开始----"
					+ new Date(System.currentTimeMillis()));
			if(keyword!=null&&keyword.trim().length()!=0)
			{
				getDoc(keyword.trim());
			}
			logger.info("----关键词:" + keyword + " 爬取结束----"
					+ new Date(System.currentTimeMillis()));
		}
	}

	public static void main(String[] args) {
		logger.info("----爬取开始----" + new Date(System.currentTimeMillis()));
		Sougou sw = new Sougou();
		 sw.runInter();
		
		logger.info("----全部主页爬取结束----" + new Date(System.currentTimeMillis()));
	}
}
