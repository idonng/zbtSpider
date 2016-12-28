package com.cn.zbt.crawlmeta.service;

import com.cn.zbt.crawlmeta.pojo.WebConf;


public interface IWebConfService {
	
	// 根据域名查询网站配置信息

	public  WebConf  selectByHostName(String hostName);
	
	public  WebConf  selectByHostNameLike(String hostName);
}
