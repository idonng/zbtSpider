package com.cn.zbt.crawlmeta.service;

import java.sql.Timestamp;
import java.util.List;

import com.cn.zbt.crawlmeta.pojo.ResultTab;



public interface ResultTabSer {
    
    public List<ResultTab> findAllResult(String urlmd5);
 
    public void insertResult(String urlmd5,String title,String url,String content,int pls,int zfs,Timestamp pubdate,String keyword,String author,Timestamp crawldate,int emoflag);
    
    public void updateResult(String urlmd5,String title,String url,String content,int pls,int zfs,String keyword,String author,Timestamp crawldate);
    public void insertRes(String urlmd5,String title, String url,String content, int pls, int zfs, Timestamp pubdate,String keyword,String author,Timestamp crawldate,int emoflag);
    	   
}
