package com.cn.zbt.crawlmeta.controller;
 
import java.io.IOException; 
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.ConfWeb;
import com.cn.zbt.crawlmeta.dm.Ctext;
import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.dm.ReadKeyword;
import com.cn.zbt.crawlmeta.service.ResultTabSer;
public class YouDao {
	private static final Logger logger = Logger.getLogger(YouDao.class);
	 private static ResultTabSer resultTabService = (ResultTabSer) GetService			.getInstance().getService("resultTabService");
	/**
	 * 爬取有道网
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
		try {
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
			conn.data("q", q).data("start", p+"0").data("ue", "utf8") 
			 .timeout(5000);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_old(url);
			logger.error("访问有道网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}
	/**
	 * 爬取有道网
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
					.timeout(2000);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_old(url);
			logger.error(" 访问有道网异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}

	@SuppressWarnings("finally")
	public Document fetch_old(String url) {
		Document doc = null;
		Connection conn = null;
		//new SetProxy().setIp();
		try {
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
					.timeout(2000);
			doc = conn.get();
		} catch (IOException e) {
			logger.error(" 访问有道网异常==========>", e);
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
				Document doc=new YouDao().fetch(url,keyword,i+++"");
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

	public void getData(Document doc, String keyword) {
		Date sinatime_now = new Date();
		Elements elements = doc.select("#results>li>div");
		for (int p = 0; p < elements.size(); p++) {
			Elements elementsurls = elements.get(p).getElementsByTag("a");
			for (Element elementsurl : elementsurls) {
				
			 
				String title = "";
				String content = "";
				String url = "";
				url = elementsurl.attr("href");
				 logger.info("正在处理：" + url);
				Long cue=CommonUtils.checkUrlExist(url);
				if (cue!=0) {
					logger.info("已经处理，跳过URL：" + url);
					continue;
				}
				if(url.contains(".wml")||url.contains("cache")){
					logger.info("跳过URL：" + url);
					continue;
				}
				 try {
					Document doc1 = new YouDao().fetch(url);
					title = doc1.select("title").first().text().trim();
					//发布时间
					String ctStr =  CommonUtils.getRegexTime( 
							 doc1.toString().replace("\n", "")
								.replace("\r", "").replace("&nbsp;", " ")).trim();
					Date pubdate = new Date();
					// 转换各种格式的日期
					pubdate = (CommonUtils.matchDateString(ctStr) == null ? sinatime_now
							: CommonUtils.matchDateString(ctStr));
					pubdate = pubdate.compareTo(sinatime_now) > 0 ? sinatime_now : pubdate;
					content = Ctext.deleteLabel(doc1.toString()).trim();
					Map<Integer, String> map = Ctext.splitBlock(content);
					// 数据库字段超长、后续可修改
					if (Ctext.judgeBlocks(map).length() > 9999) {
						content = Ctext.judgeBlocks(map).substring(0, 9999);
					} else {
						content = Ctext.judgeBlocks(map);
					}
					content = (content.length()==0) ? title : content;
					if (!CommonUtils.checkContent(content.replaceAll("\n", "").replaceAll("\r", ""))
							&& !CommonUtils.checkContent(title.replaceAll("\n", "").replaceAll("\r", ""))) {
						logger.info("不包含关键字，跳过URL：" + url);
						continue;
					}
					String author = doc1.select("#ne_article_source").text().trim();
					  author = (author.length() == 0 || author == null) ? "有道网"
								: author;
					// 转发量，评论量。新闻稿有的不存在，需要后续处理，进行热度排序推送

					String zfs = "0";
					String pls = "0";
					

					//配置网站来源、等级
					Map<String, String> webMap = ConfWeb.getWebConf(CommonUtils
							.getHost(url));
					String resultSource = webMap.get("chsName");
					int webConfKey = Integer.valueOf(webMap.get("webConfKey"));
					
					
					int  type=2;
					if(url.contains("weibo")){
						type=1;
					}
					else if(url.contains("blog")){
						type=4;
					}
					else if(url.contains("bbs")){
						type=3;
					}
					resultTabService.insertRes(CommonUtils.setMD5(url),
							title, url, content,resultSource, type, keyword,
							Integer.valueOf(pls), Integer.valueOf(zfs), pubdate,
							sinatime_now, author, sinatime_now,webConfKey);
					logger.info("URL:" + url  + "提取完成。");
				} catch (Exception e) {
					logger.error("解析错误URL:" +url+"。异常详情："+ e);
				} 
			 }
		}
	}

	public void runInter() {
		HashSet<String>  keywords1=new ReadKeyword().getKeyword();
		for (final String keyword:keywords1){
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

		YouDao sw = new YouDao();
		sw.runInter();
		//Document doc=sw.fetch_old("http://money.163.com/14/0313/18/9N848J4600253B0H.html");
		//System.out.println(doc.toString());
		logger.info("----全部主页爬取结束----" + new Date(System.currentTimeMillis()));
	}
}
