package com.cn.zbt.crawlmeta.pojo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * 读取配置文件
 *
 */
public class ReadResource {
	public ReadResource(){
		
	}
	
	/**
	 * 获取path对应配置文件信息
	 * @param path
	 * @return
	 */
	public Properties getProperties(String path){
		path = path.replaceAll("%20", " ");
		Properties pro=new Properties();
		
		try {
			InputStream inputStream = new FileInputStream(path);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			pro.load(reader);
			reader.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pro;
	}

	public static void main(String[] str){
		Properties pp=new ReadResource().getProperties("paragram.properties");
		System.out.println(pp.getProperty("historyDir"));
	}
}
