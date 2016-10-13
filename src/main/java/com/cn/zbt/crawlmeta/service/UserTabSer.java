package com.cn.zbt.crawlmeta.service;

import com.cn.zbt.crawlmeta.pojo.UserTab;

public interface UserTabSer {

	 public UserTab findValue() ;
	 public void insertIp(String ip,String port,int type) ;
	 public void updateIp(int id);
}
