package com.cn.zbt.crawlmeta.dm;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cn.zbt.crawlmeta.pojo.ResultTab;
import com.cn.zbt.crawlmeta.service.ResultTabSer;

/**
 * @author zed
 * 
 */
public class CommonUtils {

	public Date matchDateString(String dateStr) {
		try {
			List<String> matches = new ArrayList<String>();
			Pattern p = Pattern
					.compile(
							"(\\d{1,4})[-|\\|/|年|\\.](\\d{1,2})[-|\\|/|月|\\.](\\d{1,2})[\\|/|日|号]?[\\s]*(\\d{0,2})[点|时|:|：]?(\\d{0,2})[分]?[:|：]?(\\d{0,2})[秒]?[\\s]*[PM|AM]?",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			Matcher matcher = p.matcher(dateStr);
			if (matcher.find() && matcher.groupCount() >= 1) {
				for (int i = 1; i <= matcher.groupCount(); i++) {
					// System.out.println("matcher.group(i)"+matcher.group(i));
					if (i == 1 && matcher.group(i).length() == 2) {
						matches.add("20" + matcher.group(i));
						continue;
					}
					if (matcher.group(i) == null || matcher.group(i).equals("")) {
						matches.add("00");
					} else if (matcher.group(i).length() == 1) {
						matches.add("0" + matcher.group(i));
					} else {
						matches.add(matcher.group(i));
					}
				}
			}
			if (matches.size() > 0) {
				String bef = "";
				String after = "";
				for (int i = 0; i < matches.size(); i++) {
					if (i < 3) {
						bef = bef + matches.get(i) + "-";
					} else {
						after = after + matches.get(i) + ":";
					}
				}
				bef = bef.substring(0, bef.length() - 1);
				if (!after.isEmpty()) {
					after = after.substring(0, after.length() - 1);
				}
				String result = bef + " " + after;
				SimpleDateFormat ssa = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = ssa.parse(result.trim());
				return date;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getRegex(String regex, String doc) {
		String result = "";
		Pattern p = Pattern.compile(regex);
		List matches = null;
		Matcher matcher = p.matcher(doc.replace("\n", "").replace("\r", "")
				.replace("&nbsp;", " "));
		if (matcher.find() && matcher.groupCount() >= 1) {
			matches = new ArrayList();
			for (int k = 1; k <= matcher.groupCount(); k++) {
				String temp = matcher.group(k);
				matches.add(temp);
			}
		} else {
			matches = Collections.EMPTY_LIST;
		}
		if (!matches.isEmpty()) {
			result = (String) matches.get(0);
		}

		return result;
	}

	public String setMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 5 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static ResultTabSer resultTabService = (ResultTabSer) GetService
			.getInstance().getService("resultTabService");

	public boolean checkUrlExist(String url) {
		String urlmd5 = new CommonUtils().setMD5(url);
		List<ResultTab> list = new ArrayList<ResultTab>();
		list = resultTabService.findAllResult(urlmd5);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// SaveMainbody s=new SaveMainbody();
		/*String[] ss = { "2010/3/26/11:45", "2015年3月4日 12:46",
				"2014年08月27日08：33", "14年10月10日", "2015-11-2912:01",
				"2015-1-1 1:11:11", "2015-1-1 1:11", "2015/01/01 11:11:11",
				"2015/01/01 01:11:11", "2015/1/1 1:11:11", " 2015/1/1 1:11",
				"2015年01月01日 11:11:11", "2015年01月01日 01:11:11",
				"2015年1月1日 1:11:11", " 2015年1月1日 1:11", " 2015年1月1日",
				"16/11/11 11:11" };
		// String[] ss={"2010/3/26/11:45"};
		for (String str : ss) {
			System.out.println("*************");
			System.out.println("当前时间为：" + str);
			CommonUtils cu = new CommonUtils();
			System.out.println("处理后的时间为：" + cu.matchDateString(str));

		}*/
		CommonUtils cu = new CommonUtils();
		String url="http://weibo.cn/comment/CezcY67Tq";
		Boolean bl=cu.checkUrlExist(url);
		System.out.println(bl);

		
	}
}