package com.cn.zbt.crawlmeta.dm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 获取新浪微博cookie
 */
public class GetCookie {
	Logger log = Logger.getLogger(GetCookie.class);
	public String cookie="";
	public static void main(String[] args) throws IOException {
		GetCookie gc = new GetCookie();
		String cook=gc.getMCookies("weibocrash02@163.com","a123456789");
		
		System.out.println(cook);
		
	}
	/**
	 * 获取cookie
	 * @param username 新浪微博用户名
	 * @param password 密码
	 * @return cookie
	 */
	@SuppressWarnings("finally")
	public String getMCookies(String username,String password){
		try{
			Document doc = Jsoup.connect("http://login.weibo.cn/login/?ns=1&revalid=2&backURL=http%3A%2F%2Fweibo.cn%2F&backTitle=%CE%A2%B2%A9&vt=").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36").get();
			Elements inputs = doc.select("form").select("input");
			String formURL= "http://login.weibo.cn/login/"+doc.select("form").attr("action");
			HashMap<String, String> map = new HashMap<String, String>();
			String useragent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36";
			for(Element str:inputs){
				if(str.attr("type").equals("password")){
					map.put(str.attr("name"),password);
					continue;
				}
				if (str.hasAttr("value")) {
					map.put(str.attr("name"), str.attr("value"));
				}else{
					map.put(str.attr("name"),"");
				}
			}
			map.put("mobile", username);
			Map<String, String> cookieMap = Jsoup.connect(formURL).userAgent(useragent).timeout(4000).data(map).execute().cookies();
			String cookies = "";
			for(String key:cookieMap.keySet()){
				cookies+=key+"="+cookieMap.get(key)+";";
			}
			cookie=cookies.equals("")?null:cookies;
			//return cookies.equals("")?null:cookies.substring(cookies.indexOf("SUB"), cookies.length());
			return cookie;
	        }catch (Exception e) {
	        	Thread.sleep(10000);
	        	new SetProxy().setIp();
	        	cookie=getMCookies_b(username, password);
			log.error("获取新浪微博cookie异常！",e);
		}
		finally{
			return cookie;
		}
    }
	/**
	 * 获取cookie
	 * @param username 新浪微博用户名
	 * @param password 密码
	 * @return cookie
	 */
	public String getMCookies_b(String username,String password){
		try{
			Document doc = Jsoup.connect("http://login.weibo.cn/login/?ns=1&revalid=2&backURL=http%3A%2F%2Fweibo.cn%2F&backTitle=%CE%A2%B2%A9&vt=").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36").get();
			Elements inputs = doc.select("form").select("input");
			String formURL= "http://login.weibo.cn/login/"+doc.select("form").attr("action");
			HashMap<String, String> map = new HashMap<String, String>();
			String useragent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36";
			for(Element str:inputs){
				if(str.attr("type").equals("password")){
					map.put(str.attr("name"),password);
					continue;
				}
				if (str.hasAttr("value")) {
					map.put(str.attr("name"), str.attr("value"));
				}else{
					map.put(str.attr("name"),"");
				}
			}
			map.put("mobile", username);
			Map<String, String> cookieMap = Jsoup.connect(formURL).userAgent(useragent).timeout(4000).data(map).execute().cookies();
			String cookies = "";
			for(String key:cookieMap.keySet()){
				cookies+=key+"="+cookieMap.get(key)+";";
			}
			//return cookies.equals("")?null:cookies.substring(cookies.indexOf("SUB"), cookies.length());
			return cookies.equals("")?null:cookies;
	        }catch (Exception e) {
			log.error("获取新浪微博cookie异常！",e);
		}
		return null;
    }
}
