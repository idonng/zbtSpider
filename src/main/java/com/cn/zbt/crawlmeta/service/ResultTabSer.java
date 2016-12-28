package com.cn.zbt.crawlmeta.service;

import java.util.Date;
import java.util.List;

import com.cn.zbt.crawlmeta.pojo.ResultTab;

public interface ResultTabSer {

	public List<ResultTab> findAllResult(ResultTab rt);

	/**
	 *  正常操作插入数据。更新数据insertResult、updateResult
	 *  insertRes集合了以上两个
	 */
	public void insertResult(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate,int webConfKey);
	public void updateResult(String resultUrlmd5, String resultTitle,
			  String resultBody, int resultComments,
			int resultForwarded, String keyword, String resultAuthor,
			Date rcrawlDate );
	public void insertRes(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate,int webConfKey);
	
	/**
	 *  人工操作补数据插入数据。更新数据insertResult、updateResult1
	 *  insertRes1集合了以上两个
	 */
	public void updateResult1(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate);
	public void insertRes1(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate,int webConfKey);
	/**
	 *    刷新历史数据、网站配置
	 */
	
	public void updateRefeshData(Long resultKy,String resultSource,int webConfKey);
	 public List<ResultTab> findAll();

}
