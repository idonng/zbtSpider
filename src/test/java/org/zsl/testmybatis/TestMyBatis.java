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
import com.cn.zbt.crawlmeta.service.ResultTabSer;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestMyBatis {
	//private static Logger logger = Logger.getLogger(TestMyBatis.class);
//	private ApplicationContext ac = null;
	@Resource  
	  private   ResultTabSer resultTabService;
//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}

	@Test
	public void test1() {
		String url="http://finance.eastmoney.com/news/1353,20161106681105890.html";
		Date sinatime_now = new Date();
		String title = "";
		String content = "";
		String author = "";
		Document doc1 = new YouDao().fetch(url);
		  title = doc1.select("title").first().text().trim();
		String ctStr = "";
		String regex1 = "((\\d{2}|((1|2)\\d{3}))(-|年|/)\\d{1,2}(-|月|/)\\d{1,2}(日|)(( |)\\d{1,2}:\\d{1,2}(:\\d{1,2}|)|))";
		ctStr = CommonUtils.getRegex(regex1,
				Ctext.deleteLabel(doc1.toString())).trim();
		System.out.println(ctStr);
		 // 时间块
		Date pubdate = new Date();
		// 转换各种格式的日期
		pubdate = ( CommonUtils.matchDateString(ctStr) == null ? sinatime_now
				: CommonUtils.matchDateString(ctStr));
		pubdate = pubdate.compareTo(sinatime_now) > 0 ? sinatime_now
				: pubdate;
		content = Ctext.deleteLabel(doc1.toString()).trim();
		Map<Integer, String> map = Ctext.splitBlock(content);
		// 数据库字段超长、后续可修改
		if (Ctext.judgeBlocks(map).length() > 9999) {
			content = Ctext.judgeBlocks(map).substring(0, 9999);
		} else {
			content = Ctext.judgeBlocks(map);
		}
		String regex2 = "作者：(.*?)<";
		author = CommonUtils.getRegex(regex2,
				 doc1.toString().replace("\n", "").replace("\r", "")
				.replace("&nbsp;", " ")).trim();
		System.out.println(author);
		// 转发量，评论量。新闻稿有的不存在，需要后续处理，进行热度排序推送
		  author = (author.length() == 0 || author == null) ? "东方财富网"
					: author;
		String zfs = "0";
		String pls = "0";
		int type = 2; //news
		if (url.contains("bbs")) {
			type = 3;
		} else if (url.contains("blog")) {
			type = 4;
		} else if (url.contains("weibo")) {
			type = 1;
		}
		resultTabService.insertRes1(CommonUtils.setMD5(url),
				title, url, content, CommonUtils.getHost(url), type, "",
				Integer.valueOf(pls), Integer.valueOf(zfs), pubdate,
				sinatime_now, author, sinatime_now);
		 
	}
}
