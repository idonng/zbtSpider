package com.cn.zbt.crawlmeta.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cn.zbt.crawlmeta.dm.CommonUtils;

public class Test {
	public static void main(String[] args) throws IOException {
		
		 Document doc1=Jsoup.connect("http://3g.163.com/news/15/0506/06/AOTOHFQA00014AEE_2.html").get();
			String cont= doc1.toString().replace("\n", "")
					.replace("\r", "").replace("&nbsp;", " ")	;
			System.out.println(cont);
			

			Pattern p = Pattern.compile("来源:(.*?)</a>");
			Matcher matcher = p.matcher(cont);
			if (matcher.find() && matcher.groupCount() >= 1) {
					String temp = matcher.group();
					 System.out.println(temp);
			 
			}
			
			
		String author = new CommonUtils().getRegex("来源:(.*?)<",cont
				).trim();
		System.out.println(author);
		 author = new CommonUtils().getRegex("([\u4e00-\u9fa5]*)",
		 		author).trim();
		 System.out.println(author);
		  author = (author.length() == 0 || author == null) ? "人民网"
					: author;
		System.out.println(MessageFormat.format("作者为：{0}" , author ));
		String url="http://www.so.com/link?url=http%3A%2F%2Fnews.163.com%2F16%2F0903%2F22%2FC02QSTBL00014SEH.html&q=site%3A163.com+%E6%AD%A5%E9%95%BF&ts=1474617376&t=920704698115daafe731938bf0130af&src=haosou";
		url= new CommonUtils().getRegex("url=(.*?)&q",url);
		  String key="";
		try {
			key = URLDecoder.decode(url, "utf-8");
			  System.out.println(key);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		/*int i=1;
		do{
			System.out.println("1`11");
			 i=0;
			}while(i==0);*/
		 

			
	

	}

}
