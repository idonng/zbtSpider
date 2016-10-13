package com.cn.zbt.crawlmeta.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbt.crawlmeta.dao.UserTabDao;
import com.cn.zbt.crawlmeta.pojo.UserTab;
import com.cn.zbt.crawlmeta.service.UserTabSer;
@Transactional
@Service("userTabService")
public class UserTabSerImpl implements UserTabSer{
	@Resource
	private UserTabDao userTabDao;
	@Override
	 public UserTab findValue() {
		 return  this.userTabDao.findValue();
	    }
	@Override
	 public void insertIp(String username,String password,int type) {
		 UserTab it=new UserTab();
		 it.setUsername(username);
		 it.setPassword(password); 
		 it.setType(type);
		 this.userTabDao.insertUser(it);
	    }
	@Override
	 public void updateIp(int id) {
		UserTab it=new UserTab();
		 it.setId(id); 
		 this.userTabDao.updateUser(it);
	    }
	
}
