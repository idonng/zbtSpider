package com.cn.zbt.crawlmeta.dao;


import org.springframework.stereotype.Repository;

import com.cn.zbt.crawlmeta.pojo.UserTab;

@Repository
public interface UserTabDao {
	 public UserTab findValue() ;
	 public void insertUser(UserTab it) ;
	 public void updateUser(UserTab it);
}
