package com.cn.zbt.crawlmeta.crawl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cn.zbt.crawlmeta.dm.SetProxy;

public class WebScore {
	private static final Logger logger = Logger.getLogger(WebScore.class);
	public static void main(String[] args) throws IOException, InterruptedException {
		for(int i=242;i<1784;i++){
			try{
				new SetProxy().setIp();
				String url = "http://search.top.chinaz.com/top.aspx?t=all&p="+i;
				Document doc = Jsoup.connect(url).get();
				String host_name = "";
				String chs_name = "";
				String web_score = "";
				Elements els = doc.select(".ContTit").select(".ulli")
						.select(".clearfix");
				for (Element els1 : els) {
					Element el1 = els1.select(".w320").select(".PCop").select("a")
							.first();
					chs_name = el1.text();
				 
					host_name = el1.attr("href");
					int start1 = host_name.indexOf("site_") + 5;
					int end1=0;
					if( host_name.indexOf(".com.")!=-1){
					  end1 = host_name.indexOf(".com.");}
					else if( host_name.indexOf(".cn.")!=-1){
						end1 = host_name.indexOf(".cn.");
						}
					else if( host_name.indexOf(".html")!=-1){
						end1 = host_name.indexOf(".html");
						}
					else{
						end1=host_name.length();
					}
					host_name = host_name.substring(start1, end1);
					 
					Element el2 = els1.select(".w90>div").select("img").first();
					web_score = el2.attr("src");
					int start2 = web_score.indexOf("baidu/") + 6;
					int end2 = web_score.indexOf(".gif");
					web_score = web_score.substring(start2, end2);
					logger.info(host_name+";"+chs_name+";"+web_score); 
				}
				Thread.sleep(5000);
			}
			catch(Exception e){
				new SetProxy().setIp();
				String url = "http://search.top.chinaz.com/top.aspx?t=all&p="+i;
				Document doc = Jsoup.connect(url).get();
				String host_name = "";
				String chs_name = "";
				String web_score = "";
				Elements els = doc.select(".ContTit").select(".ulli")
						.select(".clearfix");
				for (Element els1 : els) {
					Element el1 = els1.select(".w320").select(".PCop").select("a")
							.first();
					chs_name = el1.text();
				 
					host_name = el1.attr("href");
					int start1 = host_name.indexOf("site_") + 5;
					int end1=0;
					if( host_name.indexOf(".com.")!=-1){
					  end1 = host_name.indexOf(".com.");}
					else if( host_name.indexOf(".cn.")!=-1){
						end1 = host_name.indexOf(".cn.");
						}
					else if( host_name.indexOf(".html")!=-1){
						end1 = host_name.indexOf(".html");
						}
					else{
						end1=host_name.length();
					}
					host_name = host_name.substring(start1, end1);
					 
					Element el2 = els1.select(".w90>div").select("img").first();
					web_score = el2.attr("src");
					int start2 = web_score.indexOf("baidu/") + 6;
					int end2 = web_score.indexOf(".gif");
					web_score = web_score.substring(start2, end2);
					logger.info(host_name+";"+chs_name+";"+web_score); 
				}
				Thread.sleep(5000);
				
			}
			
			
			
		
	}
}}
