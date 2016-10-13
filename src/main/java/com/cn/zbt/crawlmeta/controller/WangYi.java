package com.cn.zbt.crawlmeta.controller;
 
import java.io.IOException; 
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
import com.cn.zbt.crawlmeta.dm.SetProxy;
import com.cn.zbt.crawlmeta.service.ResultTabSer;
public class WangYi {
	private static final Logger logger = Logger.getLogger(WangYi.class);
	 private static ResultTabSer resultTabService = (ResultTabSer) GetService			.getInstance().getService("resultTabService");
	/**
	 * 爬取网易网
	 * 
	 * @param url
	 * 	q :关键字
	 *	p:页码
	 * @return
	 */
	@SuppressWarnings("finally")
	public Document fetch(String url,String q,String p) {
		Document doc = null;
		Connection conn = null;
		String site="site:163.com";
		try {
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
			conn.data("q", q).data("start", p+"0").data("ue", "utf8") 
			.data("site",site).timeout(5000);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_old(url);
			logger.error("访问网易网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}
	/**
	 * 爬取网易网
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
			logger.error(" 访问网易网异常==========>", e);
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
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
					.timeout(2000);
			doc = conn.get();
		} catch (IOException e) {
			logger.error(" 访问网易网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}

	public void getDoc(String keyword) {
		int i = 0;
		String reg="";
		do {
			String url = "http://www.youdao.com/search";
			try {
				Document doc=new WangYi().fetch(url,keyword,i+++"");
				reg=doc.getElementsByClass("c-pages").text(); 
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
		Elements elements = doc.select("#results>li>div>div>h3");
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
			if(url.contains(".wml")){
				continue;
			}
			try {
				Document doc1 = new WangYi().fetch(url);
				title = doc1.select("title").first().text().trim();
				//发布时间
				String ctStr =  new CommonUtils().getRegex("((\\d{2}|((1|2)\\d{3}))(-|年)\\d{2}(-|月)\\d{2}(日|)( \\d{1,2}:\\d{1,2}(:\\d{1,2}|)|))",
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
			 
				String author = doc1.select("#ne_article_source").text().trim();
				  author = (author.length() == 0 || author == null) ? "网易网"
							: author;
				// 转发量，评论量。新闻稿有的不存在，需要后续处理，进行热度排序推送

				String zfs = "0";
				String pls = "0";

				resultTabService.insertRes(new CommonUtils().setMD5(url),title, url, content,
						Integer.valueOf(pls), Integer.valueOf(zfs),
						new Timestamp(pubdate.getTime()), keyword+"_有道（网易）", author,
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

		WangYi sw = new WangYi();
		sw.runInter();
		//Document doc=sw.fetch_old("http://money.163.com/14/0313/18/9N848J4600253B0H.html");
		//System.out.println(doc.toString());
		logger.info("----全部主页爬取结束----" + new Date(System.currentTimeMillis()));
	}
}
