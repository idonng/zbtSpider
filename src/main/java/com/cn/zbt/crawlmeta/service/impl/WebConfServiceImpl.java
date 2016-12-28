package com.cn.zbt.crawlmeta.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cn.zbt.crawlmeta.dao.IWebConfDao;
import com.cn.zbt.crawlmeta.pojo.WebConf;
import com.cn.zbt.crawlmeta.service.IWebConfService;

@Service("webConfService")
public class WebConfServiceImpl implements IWebConfService {
	@Resource
	private IWebConfDao webConfDao;
	
	@Override
	// 根据域名查询网站配置信息

	public  WebConf selectByHostName(String hostName){
		// TODO Auto-generated method stub
		return this.webConfDao.selectByHostName(hostName);
	}

	public  WebConf selectByHostNameLike(String hostName){
		// TODO Auto-generated method stub
		return this.webConfDao.selectByHostNameLike(hostName);
	}

}


