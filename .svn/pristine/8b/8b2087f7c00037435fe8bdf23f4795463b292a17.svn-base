package com.cn.zbt.crawlmeta.pojo;

import java.util.Properties;

/**
 * 读取每种类型的配置文件
 */
public class ReadProper {
	private static Properties newspro;
	private static Properties bbspro;
	private static Properties weibpro;
	private static Properties sysbpro;
	private static Properties netpush;
	public static Properties systemConfig;
	private String root =  ReadProper.class.getResource("/").getPath();
	
	/**读取各个配置文件属性,常驻内存**/
	public Properties news() {
		if(newspro==null){
			newspro= new ReadResource().getProperties(root+"news.properties");
		}
		return newspro;
	}
	public Properties weibo() {
		if(weibpro==null){
			weibpro= new ReadResource().getProperties(root+"weibo.properties");
		}
		return weibpro;
	}
	public Properties bbs() {
		if(bbspro==null){
			bbspro= new ReadResource().getProperties(root+"bbs.properties");
		}
		return bbspro;
	}
	public Properties Datamining() {
		if(sysbpro==null){
			sysbpro= new ReadResource().getProperties(root+"symbleCrawler.properties");
		}
		return sysbpro;
	}
	public Properties netpush(){
		if(netpush==null){
			String path =  ReadProper.class.getResource("/").getPath();
			netpush=new ReadResource().getProperties(path+"netpush.properties");
		}
		return netpush;
	}
	public Properties systemConfig(){
		if(systemConfig==null){
			String path =  ReadProper.class.getResource("/").getPath();
			systemConfig=new ReadResource().getProperties(path+"systemConfig.properties");
		}
		return systemConfig;
	}
}
