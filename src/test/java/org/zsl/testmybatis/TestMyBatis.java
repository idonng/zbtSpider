package org.zsl.testmybatis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.zbt.crawlmeta.dm.SetUser;
import com.cn.zbt.crawlmeta.pojo.IpTab;
import com.cn.zbt.crawlmeta.pojo.UserTab;
import com.cn.zbt.crawlmeta.service.IpTabSer;
import com.cn.zbt.crawlmeta.service.UserTabSer;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestMyBatis {
	//private static Logger logger = Logger.getLogger(TestMyBatis.class);
//	private ApplicationContext ac = null;
	@Resource  
	  private   UserTabSer userTabService;
//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}

	@Test
	public void test1() {
		UserTab it=userTabService.findValue();
		System.out.println("____");
		System.out.println(it.getUsername());
		
		// logger.info("值："+user.getUserName());
		//logger.info(JSON.toJSONString(user));
	}
}
