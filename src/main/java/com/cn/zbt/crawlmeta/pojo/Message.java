package com.cn.zbt.crawlmeta.pojo;

import java.util.Properties;

/**
 * 配置文件获取值
 */
public class Message{
	
	private Message() {
		// TODO Auto-generated constructor stub
	}
	static ReadProper rp = new ReadProper();
	static Properties proper = rp.Datamining();
	/**手机浏览器标识**/
	public static String user_agent_mobile=proper.getProperty("user_agent_mobile");
	/**PC浏览器标识**/
	public static String user_agent_pc=proper.getProperty("user_agent_pc");
	
	public static String name = proper.getProperty("name");
	public static String pass = proper.getProperty("pass");	
	
	public static String sysm_luntan = proper.getProperty("sysm_luntan"); //论坛标志
	public static String sysm_weibo = proper.getProperty("sysm_weibo"); //论坛标志
	public static String sysm_xinwen = proper.getProperty("sysm_xinwen");//新闻标志

	//新浪微博
	public static String sysm_total1 = proper.getProperty("sysm_total1");	;// =	微博[
	public static String sysm_total2 = proper.getProperty("sysm_total2")	;//	=	] 关注[
	public static String sysm_end	=proper.getProperty("sysm_end")	;// =	设置:皮肤.图片.条数.隐私
	public static String sysm_zhuanfa = proper.getProperty("sysm_zhuanfa")	;//	=	] 转发[
	public static String sysm_pinglun = proper.getProperty("sysm_pinglun")	;//	=	] 评论[
	public static String sysm_shoucang = proper.getProperty("sysm_shoucang")	;// =	] 收藏
	public static String sysm_tuzan = proper.getProperty("sysm_tuzan")	;// = 	原图 赞[
	public static String sysm_zan = proper.getProperty("sysm_zan")	;// = 	赞[
	public static String sysm_day = proper.getProperty("sysm_day");//=日
	public static String sysm_month = proper.getProperty("sysm_month");//=月
	public static String sysm_today = proper.getProperty("sysm_today");//=今天
	public static String weibo_fenzhongqian = proper.getProperty("weibo_fenzhongqian");
	public static String sysm_from = proper.getProperty("sysm_from");//=来自
	public static int page = Integer.valueOf(proper.getProperty("page"));//新浪微博抓取页数
	public static int NEGNUM = Integer.valueOf(proper.getProperty("NEGNUM"));//****svm保存词个数****

	//Mop
	public static String mop_huifu = proper.getProperty("mop_huifu"); //= 回复数
	public static int mop_page = Integer.valueOf(proper.getProperty("mop_page"));//抓取页数

	//标记位
	public static int xinwen = Integer.valueOf(proper.getProperty("xinwen"));
	public static int luntan = Integer.valueOf(proper.getProperty("luntan"));
	public static int weibo = Integer.valueOf(proper.getProperty("weibo"));
	public static int wenben = Integer.valueOf(proper.getProperty("wenben"));
	
	
	
	//zhued
	public static String username = proper.getProperty("username");//=用户名
	public static String password = proper.getProperty("password");//=密码

	public static String ip = proper.getProperty("ip");//=代理IP
	public static String port = proper.getProperty("port");//=代理IP端口
}
