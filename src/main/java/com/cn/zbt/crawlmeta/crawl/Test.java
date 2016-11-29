package com.cn.zbt.crawlmeta.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.cn.zbt.crawlmeta.controller.Crawl360;
import com.cn.zbt.crawlmeta.controller.Weixin;
import com.cn.zbt.crawlmeta.controller.YouDao;
import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.Ctext;
import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.service.ResultTabSer;

public class Test {
	private static ResultTabSer resultTabService = (ResultTabSer) GetService
			.getInstance().getService("resultTabService");

	public static void main(String[] args) throws IOException {
			   String 		 url="http://weixin.sogou.com/weixin";	 
			   URL urlEX = new URL(url);
		        URLConnection rulConnection = urlEX.openConnection(); 
		        HttpURLConnection httpUrlConnection = (HttpURLConnection)rulConnection;
		    
		        httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		         List<String> cookieList = httpUrlConnection.getHeaderFields().get("Set-Cookie");
		        for(int i=0;i<cookieList.size();i++) {
		            System.out.println("cookie:"+cookieList.get(i));
		        } 
	}
 /*String	url="http://weixin.sogou.com/weixin";
		Document doc = null;
		Connection conn = null;
		Date date=new Date();
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			conn.data("query", "%e5%b1%b1%e4%b8%9c%e6%ad%a5%e9%95%bf").data("type", "2").data("ie", "utf8").data("_sug_","n").data("_sug_type_","1").data("sst0",date.getTime()+"")
			.data("page", "1").data("sut","0").data("lkt","0,0,0").data("w","")
			.timeout(5000);
			doc = conn.get();
			System.out.println(doc.toString());
		}  */
	
	
		        //一下请求参数只供参考，网站不同需要的参数也不同
		        //获取SUID
		        //String SUID = cookieList.get(1).substring(cookieList.get(1).indexOf("=")+1, cookieList.get(1).indexOf(";"));
		        //System.out.println("SUID:"+SUID);
		        //获取SNUID
		        //String SNUID = cookieList.get(3).substring(cookieList.get(3).indexOf("=")+1, cookieList.get(3).indexOf(";"));
		/*Map<String, String> map = null;
		Response res=null;
		try {
			res = Jsoup
					.connect("http://pb.sogou.com/pv.gif").data("Referer","http://weixin.sogou.com")
					.data("Host","weixin.sogou.com").data("X-Requested-With","XMLHttpRequest")
					.method(Method.HEAD).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.timeout(30000).execute();
			map = res.cookies();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		/*Map<String, String> map = null;
		Response res=null;
		try {
			res = Jsoup.connect("http://weixin.sogou.com/")
					.data("Referer","http://weixin.sogou.com")
					.data("Host","weixin.sogou.com").data("X-Requested-With","XMLHttpRequest")
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
					.method(Method.GET).timeout(30000).execute();
			map = res.cookies();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println(map.toString());*/
	 
		//System.out.println(map);
		//手 
		//String url = "http://mp.weixin.qq.com/s?__biz=MjM5NDU3NDk1MQ==&mid=2650441862&idx=5&sn=0c80c191e008b10911a28e4d43ffadbd&scene=0#wechat_redirect";
		//地球 
		//String url = "http://mp.weixin.qq.com/s?__biz=MzA4MDk5NjcyMg==&mid=2652770816&idx=1&sn=61d9d4a2df03d241ab33dd16bb97190b&scene=0#wechat_redirect";
		//五星 [B@6dc6dc2b
		//String url = "http://mp.weixin.qq.com/s?src=3&timestamp=1479972763&ver=1&signature=wgWivHRVVu4CF9bubIodXnvedNqOfK197hmQgLWfGkn8yat3DVT*jvJwBOLue*a0pFUoQo9A8JpsvBFmXMxyvWJZlnSd1Kns4miipRxoVolDe1qa4Ud1oa-K2wIha2qK1n*9aMy*l1OQlSiobjWuPA==&devicetype=Windows-QQBrowser&version=61030004&pass_ticket=qMx7ntinAtmqhVn+C23mCuwc9ZRyUp20kIusGgbFLi0=&uin=MTc1MDA1NjU1&ascene=1";
		
		/*
		 * if(url.equals("")){ System.out.println(1); } url =
		 * CommonUtils.getRegex("url=(.*?)&q", url); url =
		 * URLDecoder.decode(url, "utf-8"); System.out.println(url); Document
		 * doc = Jsoup.connect(url).get();
		 */
		//  test(url) ;
		/*
		 * HashSet<String> set = new HashSet<String>(); String kt="步长+人大代表";
		 * if(kt.contains("+")){ String s=kt.replace("+", ".*"); set.add(s);
		 * String s1=kt.split("\\+")[1].trim()+".*"+kt.split("\\+")[0] ;
		 * set.add(s1); } else{ set.add(".*"+kt+".*"); } for(String s:set){
		 * System.out.println(s); }
		 */
		/*String s = "爱上大声地步长as da das人大代表";
		String reg = ".*步长.*人大代表.*";
		System.out.println(reg);
		if (s.matches(reg) || s.matches(reg)) {
			System.out.println("111");
		}

		System.out.println("222");*/

	/*
	 * public static String getHost(String url){
	 * if(url==null||url.trim().equals("")){ return ""; } String host = "";
	 * //Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+"); String regex=
	 * "((\\w)+)+\\.(com|cn|net|org|biz|edu|gov|mil|cc)(\\.(com|cn|net|org|biz|edu|gov|mil|cc)|)/"
	 * ; host=CommonUtils.getRegex(regex, url); return host; }
	 */
	public static void test(String url) {
		Document doc1 = new Weixin().fetch(url);
		System.out.println(doc1.select("#activity-name").html());
		String title = doc1.select("#activity-name").html().replaceAll("&nbsp;", " ").trim();
		System.out.println(title);
		if(doc1.select("#activity-name").html().equals(title)){
			System.out.println("000");
		}
		String author = doc1.select("#post-user").text().trim();
		author = (author.length() == 0 || author == null) ? "微信" : author;
		String ctStr = CommonUtils.getRegexTime(
				Ctext.deleteLabel(doc1.toString())).trim();
		Date pubdate = new Date();
		// 转换各种格式的日期

		// 转发量，评论量。新闻稿有的不存在，需要后续处理，进行热度排序推送
		// 后续JS解析，可以提取此两个字段
		// String zfs = doc1.select("#sg_readNum3").text().trim();
		// String pls = doc1.select("#sg_likeNum3").text().trim();

		String zfs = "0";
		String pls = "0";
		int type = 5;
		System.out.println(CommonUtils.setMD5(title+author));//36EDCCD0DC1FFF2D23FD1A32E9CA000F
		Long cte = CommonUtils.checkUrlExist(title+author);
		// 防止链接失效
		url = url
				+ "&devicetype=Windows-QQBrowser&version=61030004&pass_ticket=qMx7ntinAtmqhVn+C23mCuwc9ZRyUp20kIusGgbFLi0=&uin=MTc1MDA1NjU1&ascene=1";
		if (cte != 0) {
			System.out.println("1111");
		} else {
			System.out.println("2222");
		}
	}
}
