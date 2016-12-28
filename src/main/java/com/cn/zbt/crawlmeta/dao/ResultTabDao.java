package com.cn.zbt.crawlmeta.dao;

import java.util.List;
import com.cn.zbt.crawlmeta.pojo.ResultTab;


public interface ResultTabDao {
	//根据urlMD5查询 
	public List<ResultTab> findAllResult(ResultTab rt);
	 //插入数据库
	    public void insertResult(ResultTab rt);
	    //更新数据库
	    public void updateResult(ResultTab rt);
	    
	    //更新数据库微信
	    public void updateResultWx(ResultTab rt);
	    //二次更新数据库微信
	    public void updateResult1(ResultTab rt);
	    
	    
	    //刷新历史数据
	    public void updateRefeshData(ResultTab rt);
	    public List<ResultTab> findAll();

}
