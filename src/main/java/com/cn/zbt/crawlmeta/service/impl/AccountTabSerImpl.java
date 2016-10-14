package com.cn.zbt.crawlmeta.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbt.crawlmeta.dao.AccountTabDao;
import com.cn.zbt.crawlmeta.pojo.AccountTab;
import com.cn.zbt.crawlmeta.service.AccountTabSer;
@Transactional
@Service("accountTabService")
public class AccountTabSerImpl implements AccountTabSer{
	@Resource
	private AccountTabDao accountTabDao;
	@Override
	 public AccountTab findValue() {
		 return  this.accountTabDao.findValue();
	    }
	@Override
	 public void insertIp(String username,String password,int type) {
		 AccountTab it=new AccountTab();
		 it.setUsername(username);
		 it.setPassword(password); 
		 it.setType(type);
		 this.accountTabDao.insertUser(it);
	    }
	@Override
	 public void updateIp(int id) {
		AccountTab it=new AccountTab();
		 it.setId(id); 
		 this.accountTabDao.updateUser(it);
	    }
	
}
