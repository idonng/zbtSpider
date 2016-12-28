package org.zsl.testmybatis;


import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.zbt.crawlmeta.pojo.ResultTab;
import com.cn.zbt.crawlmeta.pojo.WebConf;
import com.cn.zbt.crawlmeta.service.IWebConfService;
import com.cn.zbt.crawlmeta.service.ResultTabSer;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class RefreshData {
	@Resource  
	  private   IWebConfService  webConfService;
	@Resource  
	  private   ResultTabSer  resultTabService;
	 
 
	@Test
	public void test1() { 
		List<ResultTab> rts= resultTabService.findAll();
		for(ResultTab rt :rts)
		{
			System.out.println(rt.getResultKy());
			String hostName=rt.getResultSource();
			String chsName=hostName;
			int webKey= 1; 
			WebConf wc=webConfService.selectByHostName(hostName);
			if(wc!=null){
			  chsName=wc.getChsName();
			  webKey= wc.getWebKey();
			 }
			else{
				WebConf wclike=webConfService.selectByHostNameLike(hostName);
				if(wclike !=null){
					 chsName=wclike.getChsName();
					 webKey= wclike.getWebKey();
				}
			}
			resultTabService.updateRefeshData(rt.getResultKy(), chsName, webKey);
		}
	}
}
