package com.cn.zbt.crawlmeta.service;

import com.cn.zbt.crawlmeta.pojo.AccountTab;

public interface AccountTabSer {

	 public AccountTab findValue() ;
	 public void insertIp(String ip,String port,int type) ;
	 public void updateIp(int id);
}
