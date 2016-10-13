package com.cn.zbt.crawlmeta.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbt.crawlmeta.dao.LasttimeTabDao;
import com.cn.zbt.crawlmeta.pojo.LasttimeTab;
import com.cn.zbt.crawlmeta.service.LasttimeTabSer;
@Transactional
@Service("lasttimeTabService")
public class LasttimeTabSerImpl implements LasttimeTabSer {
	@Resource
	private LasttimeTabDao lasttimeTabDao;
	
	@Override
    public String findValue(String key) {
      String value=(String) this.lasttimeTabDao.findValue(key);
      return value;
    }

    public void insertLasttimeTab(String key, String value) {
        LasttimeTab lt=new LasttimeTab();
        lt.setKey(key);
        lt.setValue(value);
        this.lasttimeTabDao.insertLasttimeTab(lt);       
    }

    public void updateLasttimeTab(String key, String value) {
        LasttimeTab lt=new LasttimeTab();
        lt.setKey(key);
        lt.setValue(value);
        this.lasttimeTabDao.updateLasttimeTab(lt);         
    }

}
