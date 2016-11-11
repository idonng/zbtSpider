package com.cn.zbt.crawlmeta.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

	public static void trustEveryone() {
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new HostnameVerifier() {
						public boolean verify(String hostname,
								SSLSession session) {
							return true;
						}
					});

			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context
					.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		trustEveryone();
		try {
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
			conn.data("query", q).data("ie", "utf8").data("p","76330100")
					 .data("page", p).timeout(5000);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_old(url);
			logger.error("jsoup访问人民网异常==========>", e);
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
			logger.error("jsoup访问人民网异常==========>", e);
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
			logger.error("jsoup访问人民网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}

	public void getDoc(String keyword) {
		int i = 0;
		String reg = "";
		do {
			if(i<0){
				return;
			}
			String url = "https://www.sogou.com/web";
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

	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public void getData(Document doc, String keyword) {
		Date sinatime_now = new Date();
		Elements elements = doc.select(".rb>h3");
		System.out.println(elements.size());
		for (int p = 0; p < elements.size(); p++) {
			Element element = elements.get(p).getElementsByTag("a").first();
			String title = "";
			String content = "";
			String url = "";
			String author = "搜狗";
			url = element.attr("href");
			url = CommonUtils.getRegex("url=(.*?)&q", url);
			try {
				url = URLDecoder.decode(url, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info("正在处理：" + url);
			if (CommonUtils.checkUrlExist(url)) {
				logger.info("已经处理，跳过URL：" + url);
				continue;
			}
			if (url.contains(".wml") || url.length() == 0) {
				logger.info("跳过不处理wml：" + url);
				continue;
			}
			try {
				Document doc1 = new Sougou().fetch(url);
				title = doc1.select("title").first().text().trim();
				String ctStr = "";
				String regex1 = "((\\d{2}|((1|2)\\d{3}))(-|年|\\.|/)\\d{1,2}(-|月|\\.|/)\\d{1,2}(日|)(( |)\\d{1,2}:\\d{1,2}(:\\d{1,2}|)|))";
				Pattern p1 = Pattern.compile(regex1);
				List matches1 = null;
				Matcher matcher1 = p1.matcher(doc1.toString().replace("\n", "")
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
					ctStr = (String) matches1.get(0); // 时间块
				}

				Date pubdate = new Date();
				// 转换各种格式的日期
				pubdate = (CommonUtils.matchDateString(ctStr) == null ? sinatime_now
						:CommonUtils.matchDateString(ctStr));
				pubdate = pubdate.compareTo(sinatime_now) > 0 ? sinatime_now
						: pubdate;
				Ctext ctx = new Ctext();
				content = ctx.deleteLabel(doc1.toString()).trim();
				Map<Integer, String> map = ctx.splitBlock(content);
				// 数据库字段超长、后续可修改
				if (ctx.judgeBlocks(map).length() > 9999) {
					content = ctx.judgeBlocks(map).substring(0, 9999);
				} else {
					content = ctx.judgeBlocks(map);
				}
				String regex2 = "来源:(.*?)<";
				Pattern p2 = Pattern.compile(regex2);
				List matches2 = null;
				Matcher matcher2 = p2.matcher(doc1.toString().replace("\n", "")
						.replace("\r", "").replace("&nbsp;", " "));
				if (matcher2.find() && matcher2.groupCount() >= 1) {
					matches2 = new ArrayList();
					for (int k = 1; k <= matcher2.groupCount(); k++) {
						String temp2 = matcher2.group(k);
						matches2.add(temp2);
					}
				} else {
					matches2 = Collections.EMPTY_LIST;
				}
				if (!matches2.isEmpty()) {
					author = (String) matches2.get(0);
				}
				author =CommonUtils.getRegex("来源:(.*?)<",
						ctx.deleteLabel(doc1.toString())).trim();
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
				logger.info("URL:" + url + " 提取完成。");
			} catch (Exception e) {
				logger.error("解析错误URL:" + url + "。异常详情：" + e);
			}
		}
	}

	public void runInter() {
		HashSet<String> keywords1 = new ReadKeyword().getKeyword();
		for (final String keyword : keywords1) {
			logger.info("----关键词:" + keyword + " 爬取开始----"
					+ new Date(System.currentTimeMillis()));
			getDoc(keyword.trim());
			logger.info("----关键词:" + keyword + " 爬取结束----"
					+ new Date(System.currentTimeMillis()));
		}
	}

	public static void main(String[] args) {
		logger.info("----爬取开始----" + new Date(System.currentTimeMillis()));

		Sougou sw = new Sougou();
		sw.runInter();
		 Document
		 doc=sw.fetch_old("http://money.163.com/14/0313/18/9N848J4600253B0H.html");
		 System.out.println(doc.toString());
		logger.info("----全部主页爬取结束----" + new Date(System.currentTimeMillis()));
	}
}
