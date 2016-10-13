package com.cn.zbt.crawlmeta.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbt.crawlmeta.dao.CookieTabDao;
import com.cn.zbt.crawlmeta.pojo.CookieTab;
import com.cn.zbt.crawlmeta.service.CookieTabSer;
@Transactional
@Service("cookieTabService")
public class CookieTabSerImpl implements CookieTabSer {
	@Resource
	private CookieTabDao cookieTabDao;
	
	@Override
    public List<CookieTab> findAllCookie() {
        // TODO Auto-generated method stub
        List<CookieTab> list=new ArrayList<CookieTab>();
        list=this.cookieTabDao.findAllCookie(); 
        return list;
    }
	@Override
    public void updateCookie(String cookie, Boolean available, String username) {
        // TODO Auto-generated method stub
        CookieTab ct=new CookieTab();
        ct.setUsername(username);
        ct.setCookie(cookie);
        ct.setAvailable(available);
        this.cookieTabDao.updateCookie(ct);
    }
   
	@Override
    public List<CookieTab> findUser() {
        // TODO Auto-generated method stub
        List<CookieTab> list=new ArrayList<CookieTab>();
        list=this.cookieTabDao.findUser();
        return list;
    }
 

}
