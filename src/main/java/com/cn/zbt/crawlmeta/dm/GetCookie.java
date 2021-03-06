package com.cn.zbt.crawlmeta.dm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;
/**
 * 获取新浪微博cookie
 */
public class GetCookie {
	Logger logger = Logger.getLogger(GetCookie.class);
	public String cookie="";
	public static void main(String[] args) throws IOException {
		GetCookie gc = new GetCookie();
		 //  String cook=gc.getMCookies("weibocrash01@163.com","a123456789");
		 // String cook=gc.getMCookies("18792486060","zerd90818");
		Map<String, String> cook=gc.getCookieWx() ;
		System.out.println(cook.toString() );
		
	}
	private static GetCookie instanceSina;  
    private GetCookie(){}  
  
    public static GetCookie getInstanceSina() {  
    if (instanceSina == null) {  
    	instanceSina = new GetCookie();  
    }  
    return instanceSina;  
    }  
    public static void clearInstanceSina() {  
        if (instanceSina != null) {  
        	instanceSina = null;  
        }  
        return ;  
    } 
    private static GetCookie instanceWx;  
    public static GetCookie getInstanceWx() {  
    if (instanceWx == null) {  
    	instanceWx = new GetCookie();  
    }  
    return instanceWx;  
    }  
    public static void clearInstanceWx() {  
        if (instanceWx != null) {  
        	instanceWx = null;  
        }  
        return ;  
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
	        	Thread.sleep(1000);
	        	//new SetProxy().setIp();
	        	cookie=getMCookies_b(username, password);
	        	logger.error("获取新浪微博cookie异常！",e);
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
	        	logger.error("获取新浪微博cookie异常！",e);
		}
		return null;
    }
	/**
	 * 获取搜狗cookie
	 
	 * @return cookie
	 */
public  Map<String, String> getCookieWx() {
	new SetProxy().setIp();
	Random rd =new Random();
    JBrowserDriver driver = new JBrowserDriver(Settings.builder().
      timezone(Timezone.ASIA_SHANGHAI).build());
    driver.get("http://weixin.sogou.com/weixin?type=2&ie=utf8&_sug_=y&_sug_type_=&query="+rd.nextInt(100));
    try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Set<Cookie> cookies = driver.manage().getCookies();  
    Map<String, String> map = new HashMap<String, String>();
    for(Cookie s:cookies){
    	if(s.toString().indexOf("SNUID")!=-1){
    		map.put("SNUID", s.toString().substring(s.toString().indexOf("SNUID=")+6, s.toString().indexOf(";")));
    	}
    	if(s.toString().indexOf("SUID")!=-1){
    		map.put("SUID", s.toString().substring(s.toString().indexOf("SUID=")+5, s.toString().indexOf(";")));
    	}
    }
    driver.quit();  
    if(!map.containsKey("SNUID")||!map.containsKey("SUID")){
    	map=new GetCookie().getCookieWx_b();
    }
    return map;
}

public  Map<String, String> getCookieWx_b() {
	new SetProxy().setIp();
	Random rd =new Random();
    JBrowserDriver driver = new JBrowserDriver(Settings.builder().
      timezone(Timezone.ASIA_SHANGHAI).build());
    driver.get("http://weixin.sogou.com/weixin?type=2&ie=utf8&_sug_=y&_sug_type_=&query="+rd.nextInt(100));
    try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    Set<Cookie> cookies = driver.manage().getCookies();  
    Map<String, String> map = new HashMap<String, String>();
    for(Cookie s:cookies){
    	if(s.toString().indexOf("SNUID")!=-1){
    		map.put("SNUID", s.toString().substring(s.toString().indexOf("SNUID=")+6, s.toString().indexOf(";")));
    	}
    	if(s.toString().indexOf("SUID")!=-1){
    		map.put("SUID", s.toString().substring(s.toString().indexOf("SUID=")+5, s.toString().indexOf(";")));
    	}
    }
    driver.quit();  
    return map;
}
}
