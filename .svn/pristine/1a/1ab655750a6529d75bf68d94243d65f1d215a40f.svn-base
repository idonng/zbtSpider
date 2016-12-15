package com.cn.zbt.crawlmeta.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cn.zbt.crawlmeta.dao.ResultTabDao;
import com.cn.zbt.crawlmeta.pojo.ResultTab;
import com.cn.zbt.crawlmeta.service.ResultTabSer;

@Transactional
@Service("resultTabService")
public class ResultTabSerImpl implements ResultTabSer {
	@Resource
	private ResultTabDao resultTabDao;

	@Override
	public List<ResultTab> findAllResult(ResultTab rt) {
		// TODO Auto-generated method stub
		List<ResultTab> list = new ArrayList<ResultTab>();
		list = this.resultTabDao.findAllResult(rt);
		return list;
	}

	@Override
	public synchronized void insertResult(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate) {
		// TODO Auto-generated method stub
		ResultTab rt = new ResultTab();
		rt.setResultUrlmd5(resultUrlmd5);
		rt.setResultTitle(resultTitle);
		rt.setResultUrl(resultUrl);
		rt.setResultBody(resultBody);
		rt.setResultSource(resultSource);
		rt.setResultType(resultType);
		rt.setKeyword(keyword);
		rt.setResultAuthor(resultAuthor);
		rt.setResultForwarded(resultForwarded);
		rt.setResultComments(resultComments);
		rt.setPublishedDate(publishedDate);
		rt.setFcrawlDate(fcrawlDate);
		rt.setRcrawlDate(rcrawlDate);
		this.resultTabDao.insertResult(rt);
	}

	@Override
	public void updateResult(String resultUrlmd5, String resultTitle,
			  String resultBody, int resultComments,
			int resultForwarded, String keyword, String resultAuthor,
			Date rcrawlDate ) {
		// TODO Auto-generated method stub
		ResultTab rt = new ResultTab();
		rt.setResultUrlmd5(resultUrlmd5);
		rt.setResultTitle(resultTitle);
		rt.setResultBody(resultBody);
		rt.setKeyword(keyword);
		rt.setResultAuthor(resultAuthor);
		rt.setResultForwarded(resultForwarded);
		rt.setResultComments(resultComments);
		rt.setRcrawlDate(rcrawlDate);
		this.resultTabDao.updateResult(rt);
	}
	/*public synchronized void updateResultWx(Long resultKy ,String resultUrlmd5,  
			 String resultUrl,Date rcrawlDate ){
		// TODO Auto-generated method stub
		ResultTab rt = new ResultTab();
		rt.setResultKy(resultKy);
		rt.setResultUrlmd5(resultUrlmd5);
		rt.setResultUrl(resultUrl);
		rt.setRcrawlDate(rcrawlDate);
		this.resultTabDao.updateResultWx(rt);
	}*/
	@Override
	public synchronized void insertRes(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate) {

		List<ResultTab> list = new ArrayList<ResultTab>();
		int n = 0;
		ResultTab rt = new ResultTab();
		rt.setResultUrlmd5(resultUrlmd5);
		list = this.resultTabDao.findAllResult(rt);
		//for (ResultTab rt1 : list) {
		//	if (resultUrlmd5.equals(rt1.getResultUrlmd5())) {
		if(list.size()>0){
				n = 1;
			}
		if (n == 0) {
			insertResult(resultUrlmd5, resultTitle, resultUrl, resultBody,
					resultSource, resultType, keyword, resultComments,
					resultForwarded, publishedDate, fcrawlDate, resultAuthor,
					rcrawlDate);
		} else {
			updateResult(resultUrlmd5, resultTitle,  resultBody,
					resultComments, resultForwarded, keyword, resultAuthor,
					rcrawlDate );
		}
	}
	@Override
	public void updateResult1(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate) {
		// TODO Auto-generated method stub
		ResultTab rt = new ResultTab();
		rt.setResultUrlmd5(resultUrlmd5);
		rt.setResultTitle(resultTitle);
		rt.setResultUrl(resultUrl);
		rt.setResultBody(resultBody);
		rt.setResultSource(resultSource);
		rt.setResultType(resultType);
		rt.setKeyword(keyword);
		rt.setResultAuthor(resultAuthor);
		rt.setResultForwarded(resultForwarded);
		rt.setResultComments(resultComments);
		rt.setPublishedDate(publishedDate);
		rt.setFcrawlDate(fcrawlDate);
		rt.setRcrawlDate(rcrawlDate);
		this.resultTabDao.updateResult1(rt);
	}
	@Override
	public synchronized void insertRes1(String resultUrlmd5, String resultTitle,
			String resultUrl, String resultBody, String resultSource,
			int resultType, String keyword, int resultComments,
			int resultForwarded, Date publishedDate, Date fcrawlDate,
			String resultAuthor, Date rcrawlDate) {

		List<ResultTab> list = new ArrayList<ResultTab>();
		int n = 0;
		ResultTab rt = new ResultTab();
		rt.setResultUrlmd5(resultUrlmd5);
		list = this.resultTabDao.findAllResult(rt);
		//for (ResultTab rt1 : list) {
		//	if (resultUrlmd5.equals(rt1.getResultUrlmd5())) {
		if(list.size()>0){
				n = 1;
			}
		if (n == 0) {
			insertResult(resultUrlmd5, resultTitle, resultUrl, resultBody,
					resultSource, resultType, keyword, resultComments,
					resultForwarded, publishedDate, fcrawlDate, resultAuthor,
					rcrawlDate);
		} else {
			updateResult1(resultUrlmd5, resultTitle, resultUrl, resultBody,
					resultSource, resultType, keyword, resultComments,
					resultForwarded, publishedDate, fcrawlDate, resultAuthor,
					rcrawlDate);
		}
	}
}
