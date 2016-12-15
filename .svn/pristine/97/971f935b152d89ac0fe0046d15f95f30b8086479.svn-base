package com.cn.zbt.crawlmeta.dm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetService {
	private static GetService instance;  
    private GetService(){}  
  
    public static GetService getInstance() {  
    if (instance == null) {  
        instance = new GetService();  
    }  
    return instance;  
    }  
    public static void clearInstance() {  
        if (instance != null) {  
            instance = null;  
        }  
        return ;  
    }  
	private static ApplicationContext ac = null;
	public  Object getService(String serstr){
		if(ac==null){
		ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");}
		Object bf=ac.getBean(serstr);
		return bf;
	}
}
