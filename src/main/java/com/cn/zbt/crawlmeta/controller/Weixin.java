package com.cn.zbt.crawlmeta.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.Ctext;
import com.cn.zbt.crawlmeta.dm.GetCookie;
import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.dm.ReadKeyword;
import com.cn.zbt.crawlmeta.dm.SetProxy;
import com.cn.zbt.crawlmeta.service.ResultTabSer;

public class Weixin {
	private static final Logger logger = Logger.getLogger(Weixin.class);
	private static ResultTabSer resultTabService = (ResultTabSer) GetService
			.getInstance().getService("resultTabService");
 /**
  * 爬取Weixin
  * @param url
  * @param q 关键词
  * @param p 页码
  * @return
  */
	@SuppressWarnings("finally")
	public Document fetch(String url, String q, String p) {
		 //出现验证码 http://www.sogou.com/antispider/
		/*GetCookie gc = new GetCookie();
		String cookie=gc.getMCookiesWx();
		String[] cook = cookie.split(";");
		Map<String, String> map = new HashMap<String, String>();
		for (String s : cook) {
			String[] coo = s.split("=");
			String key = coo[0];
			String value = coo[1];
			map.put(key, value);
		}*/
		/*String cookie="SNUID=DABC21285451114A90AE89F95455DC43;SUID=8EE8727C2A0B940A000000005837D241;SUV=1480053313685968;";
		String[] cook = cookie.split(";");
		Map<String, String> map = new HashMap<String, String>();
		for (String s : cook) {
			String[] coo = s.split("=");
			String key = coo[0];
			String value = coo[1];
			map.put(key, value);
		} */
		GetCookie gc = new GetCookie();
		Map<String, String> map = new HashMap<String, String>();
		map=gc.getMCookiesWx();
		Date date=new Date();
		if(map==null||map.get("SNUID").length()==0||map.get("SNUID")==null||map.get("SUID").length()==0||map.get("SUID")==null){
			map=gc.getMCookiesWx_b();
		}
		if(map==null||map.get("SNUID").length()==0||map.get("SNUID")==null||map.get("SUID").length()==0||map.get("SUID")==null){
			return null;
		}
		map.put("SUV", date.getTime()+"");
		System.out.println(map.toString());
		Document doc = null;
		try {
			Connection	conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36") ;
			conn.data("query", q).data("type", "2").data("ie", "utf8") 
					.data("page", p).cookies(map)
					.timeout(5000);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_old(url,q,p);
		} finally {
			return doc;
		}
	}
	@SuppressWarnings("finally")
	public Document fetch_old(String url, String q, String p) {
		GetCookie gc = new GetCookie();
		Map<String, String> map = new HashMap<String, String>();
		map=gc.getMCookiesWx();
		Date date=new Date();
		if(map==null||map.get("SNUID").length()==0||map.get("SNUID")==null||map.get("SUID").length()==0||map.get("SUID")==null){
			map=gc.getMCookiesWx_b();
		}
		if(map==null||map.get("SNUID").length()==0||map.get("SNUID")==null||map.get("SUID").length()==0||map.get("SUID")==null){
			return null;
		}
		map.put("SUV", date.getTime()+"");
		System.out.println(map.toString());
		Document doc = null;
		try {
			Connection	conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36") ;
			conn.data("query", q).data("type", "2").data("ie", "utf8") 
					.data("page", p).cookies(map)
					.timeout(5000);
			doc = conn.get();
		} catch (IOException e) {
			logger.error("jsoup访问搜狗网微信异常==========>", e);
			logger.error("URL为：" + url+"  关键字："+q+" 页码："+p);
		} finally {
			return doc;
		}
	}
	 /**
	  * 爬取Weixin
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
			logger.error("jsoup访问搜狗网微信异常==========>", e);
			logger.error("URL为：" + url);
		} finally {
			return doc;
		}
	}

	public void getDoc(String keyword) {
		int i = 0;
		String reg = "";
		do {
			if (i < 0||i>10) {
				return;
			}
			String url = "http://weixin.sogou.com/weixin";
			try {
				Document doc = new Weixin().fetch(url, keyword, i++ + "");
				if(doc==null||!doc.select("title").text().contains(keyword)){
					i=(--i<0)?0:i;
					logger.error("搜索关键词错误URL:" + url +" 关键字："+keyword +" 页数：" +i++ );
					continue;
				}
				//System.out.println(doc.toString());
				reg = doc.getElementById("pagebar_container").text();
				getData(doc, keyword);
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				i--;
				logger.error("解析错误URL:" + url +" 关键字："+keyword +" 页数：" +i++ + "。\n异常详情：" + e);
			}
			System.gc();
		} while (reg.contains("下一页"));
	}

	public void getData(Document doc, String keyword) {
		Date sinatime_now = new Date();
		Elements elements = doc.select(".news-list>li");
		for (int p = 0; p < elements.size(); p++) {
			Element element = elements.get(p).getElementsByTag("a").first();
			String title = "";
			String content = "";
			String url = "";
			String author = "微信";
			url = element.attr("href");
			logger.info("正在处理：" + url);
			 try {
				Document doc1 = new Weixin().fetch(url);
				title = doc1.select("#activity-name").html().replaceAll("&nbsp;", " ").trim();
				if(title.length()==0||title==null){
					logger.info("标题为空，不作处理 ,URL：" + url);
					continue;
				}
				author = doc1.select("#post-user").text().trim();
				author = (author.length() == 0 || author == null) ? "微信公众平台" : author;
				String ctStr = CommonUtils.getRegexTime(
						Ctext.deleteLabel(doc1.toString())).trim();
				Date pubdate = new Date();
				// 转换各种格式的日期
				pubdate = (CommonUtils.matchDateString(ctStr) == null ? sinatime_now
						: CommonUtils.matchDateString(ctStr));
				pubdate = pubdate.compareTo(sinatime_now) > 0 ? sinatime_now
						: pubdate;
				content=doc1.select("#page-content").text().trim();
				content = (content.length()==0) ? title : content;
				Long cue=CommonUtils.checkUrlExist(title+author);
				if (cue!=0) {
					logger.info("已经处理，跳过URL：" + url);
					continue;
				}
				if (!CommonUtils.checkContent(content)
						&& !CommonUtils.checkContent(title)) {
					continue;
				}
				// 转发量，评论量。新闻稿有的不存在，需要后续处理，进行热度排序推送
				//	后续JS解析，可以提取此两个字段
				// String zfs = doc1.select("#sg_readNum3").text().trim();
				// String pls = doc1.select("#sg_likeNum3").text().trim();
			
				String zfs = "0";
				String pls = "0";
				int type = 5;
				
				//防止链接失效
				url=url+"&devicetype=Windows-QQBrowser&version=61030004&pass_ticket=qMx7ntinAtmqhVn+C23mCuwc9ZRyUp20kIusGgbFLi0=&uin=MTc1MDA1NjU1&ascene=1";
				
				resultTabService.insertRes(CommonUtils.setMD5(title+author),
						title, url, content, "weixin", type, keyword,
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
			if (keyword != null && keyword.trim().length() != 0) {
				getDoc(keyword.trim());
			}
			logger.info("----关键词:" + keyword + " 爬取结束----"
					+ new Date(System.currentTimeMillis()));
		}
	}

	public static void main(String[] args) {
	
		Weixin sw = new Weixin();
		sw.getDoc("山东步长");
	/*	do{
			logger.info("----全部主页爬取开始----" + new Date(System.currentTimeMillis()));
			 sw.runInter();
			 logger.info("----全部主页爬取结束----" + new Date(System.currentTimeMillis()));
		}while(true);*/
	
		 
	}
}
