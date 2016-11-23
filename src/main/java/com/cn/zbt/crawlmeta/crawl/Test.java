package com.cn.zbt.crawlmeta.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cn.zbt.crawlmeta.controller.Crawl360;
import com.cn.zbt.crawlmeta.controller.YouDao;
import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.Ctext;
import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.service.ResultTabSer;

public class Test {
	private static ResultTabSer resultTabService = (ResultTabSer) GetService
			.getInstance().getService("resultTabService");
	public static void main(String[] args) throws IOException {
		/*String url="http://bbs1.people.com.cn/post/129/1/2/159564390.html";
		 if(url.equals("")){
			 System.out.println(1);
		 }
		url = CommonUtils.getRegex("url=(.*?)&q", url);
			url = URLDecoder.decode(url, "utf-8");
		System.out.println(url);
		Document doc = Jsoup.connect(url).get();
 		test(url) ;*/
		int i=5;
		i=(--i<0)?0:i;
		System.out.println(i);
		 
		 
	}
	/*public static String getHost(String url){
        if(url==null||url.trim().equals("")){
            return "";
        }
        String host = "";
        //Pattern p =  Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
       String	regex="((\\w)+)+\\.(com|cn|net|org|biz|edu|gov|mil|cc)(\\.(com|cn|net|org|biz|edu|gov|mil|cc)|)/";
        host=CommonUtils.getRegex(regex, url);
        return host;
    }*/
	public static void test(String url){
		Date sinatime_now = new Date();
		String title = "";
		String content = "";
		String author = "人民网";
		Document doc1 = new YouDao().fetch(url);
		  title = doc1.select("title").first().text().trim();
		String ctStr = "";
		String regex1 = "((\\d{2}|((1|2)\\d{3}))(-|年|/)\\d{1,2}(-|月|/)\\d{1,2}(日|)(( |)\\d{1,2}:\\d{1,2}(:\\d{1,2}|)|))";
		ctStr = CommonUtils.getRegex(regex1,
				Ctext.deleteLabel(doc1.toString())).trim();
		 // 时间块
System.out.println(ctStr);
		Date pubdate = new Date();
		// 转换各种格式的日期
		pubdate = ( CommonUtils.matchDateString(ctStr) == null ? sinatime_now
				: CommonUtils.matchDateString(ctStr));
		pubdate = pubdate.compareTo(sinatime_now) > 0 ? sinatime_now
				: pubdate;
		System.out.println(pubdate);
		content = Ctext.deleteLabel(doc1.toString()).trim();
		Map<Integer, String> map = Ctext.splitBlock(content);
		// 数据库字段超长、后续可修改
		if (Ctext.judgeBlocks(map).length() > 9999) {
			content = Ctext.judgeBlocks(map).substring(0, 9999);
		} else {
			content = Ctext.judgeBlocks(map);
		}
		String regex2 = "楼主：<a href[^>]*?>(.*?)</a>";
		Pattern p2 = Pattern.compile(regex2);
		List matches2 = null;
		Matcher matcher2 = p2.matcher(doc1.toString().trim());
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
		author = CommonUtils.getRegex("来源:(.*?)<",
				Ctext.deleteLabel(doc1.toString())).trim();
		// 转发量，评论量。新闻稿有的不存在，需要后续处理，进行热度排序推送
		  author = (author.length() == 0 || author == null) ? "360"
					: author;
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
				title, url, content, CommonUtils.getHost(url), type, "",
				Integer.valueOf(pls), Integer.valueOf(zfs), pubdate,
				sinatime_now, author, sinatime_now);
	}
}
