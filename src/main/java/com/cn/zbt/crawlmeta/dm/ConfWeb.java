package com.cn.zbt.crawlmeta.dm;

import java.util.HashMap;
import java.util.Map;
import com.cn.zbt.crawlmeta.pojo.WebConf;
import com.cn.zbt.crawlmeta.service.IWebConfService;

public class ConfWeb {
	private static IWebConfService webConfService = (IWebConfService) GetService
			.getInstance().getService("webConfService");
	public static Map<String, String> getWebConf(String hostName){
		Map<String, String> map =new HashMap<String, String>();
			String chsName=hostName;
			int webKey= 1; 
			WebConf wc=webConfService.selectByHostName(hostName);
			if(wc!=null){
			  chsName=wc.getChsName();
			  webKey= wc.getWebKey();
			 }
			else{
				WebConf wclike=webConfService.selectByHostNameLike(hostName);
				if(wclike !=null){
					 chsName=wclike.getChsName();
					 webKey= wclike.getWebKey();
				}
			}
		map.put("chsName", chsName);
		map.put("webConfKey", webKey+"");
		return map;
		
	}
	
	
	public static void main(String[] args) {
		 String hostName="tianya";
		 Map<String, String> map =new HashMap<String, String>();
		 map=ConfWeb.getWebConf(hostName);
		 System.out.println(map.get("chsName"));
		 System.out.println(map.get("webConfKey"));
	}
}
