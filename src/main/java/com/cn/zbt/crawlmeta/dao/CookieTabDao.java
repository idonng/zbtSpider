package com.cn.zbt.crawlmeta.dao;

import java.util.List;

import com.cn.zbt.crawlmeta.pojo.CookieTab;


public interface CookieTabDao {
	 
    public List<CookieTab> findAllCookie();
    
    public List<CookieTab> findUser();
    
    public void updateCookie(CookieTab ct);
}
