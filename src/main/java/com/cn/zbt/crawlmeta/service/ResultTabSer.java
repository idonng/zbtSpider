package com.cn.zbt.crawlmeta.service;

import java.util.Date;
import java.util.List;

import com.cn.zbt.crawlmeta.pojo.ResultTab;

public interface ResultTabSer {

	public List<ResultTab> findAllResult(String resultUrlmd5);

	public void insertResult(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate);

	public void updateResult(String resultUrlmd5, String resultTitle,
			  String resultBody, int resultComments,
			int resultForwarded, String keyword, String resultAuthor,
			Date rcrawlDate );

	public void insertRes(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate);

}
