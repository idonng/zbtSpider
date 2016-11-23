package org.zsl.testmybatis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.zbt.crawlmeta.controller.YouDao;
import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.Ctext;
import com.cn.zbt.crawlmeta.pojo.AccountTab;
import com.cn.zbt.crawlmeta.pojo.KeywordTab;
import com.cn.zbt.crawlmeta.service.KeywordTabSer;
import com.cn.zbt.crawlmeta.service.ResultTabSer;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class CopyOfTestMyBatis {
	//private static Logger logger = Logger.getLogger(TestMyBatis.class);
//	private ApplicationContext ac = null;
	@Resource  
	  private   KeywordTabSer  keywordTabService;
//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}

	@Test
	public void test1() { 
		
	List<KeywordTab> lks=keywordTabService.findAllKeyword();
	for(KeywordTab kt:lks){
		System.out.println(kt.getKeyword_name());
	}
	}
}
