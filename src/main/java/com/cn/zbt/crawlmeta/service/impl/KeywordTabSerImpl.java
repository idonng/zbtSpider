package com.cn.zbt.crawlmeta.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cn.zbt.crawlmeta.dao.KeywordTabDao;
import com.cn.zbt.crawlmeta.pojo.KeywordTab;
import com.cn.zbt.crawlmeta.service.KeywordTabSer;
@Transactional
@Service("keywordTabService")
public class KeywordTabSerImpl implements KeywordTabSer {
	@Resource
	private KeywordTabDao keywordTabDao;

	@Override
	public List<KeywordTab> findAllKeyword() {
		// TODO Auto-generated method stub
		return this.keywordTabDao.findAllKeyword( );
	}
	
	 
}
