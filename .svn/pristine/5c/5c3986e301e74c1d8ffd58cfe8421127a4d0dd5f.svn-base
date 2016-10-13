package com.cn.zbt.crawlmeta.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbt.crawlmeta.dao.IpTabDao;
import com.cn.zbt.crawlmeta.pojo.IpTab;
import com.cn.zbt.crawlmeta.service.IpTabSer;
@Transactional
@Service("ipTabService")
public class IpTabSerImpl implements IpTabSer{
	@Resource
	private IpTabDao ipTabDao;
	@Override
	 public IpTab findValue() {
		 return  this.ipTabDao.findValue();
	    }
	@Override
	 public void insertIp(String ip,String port,int type) {
		 IpTab it=new IpTab();
		 it.setIp(ip);
		 it.setPort(port);
		 it.setType(type);
		 this.ipTabDao.insertIp(it);
	    }
	@Override
	 public void updateIp(int id) {
		 IpTab it=new IpTab();
		 it.setId(id); 
		 this.ipTabDao.updateIp(it);
	    }
	
}
