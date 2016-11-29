package com.cn.zbt.crawlmeta.dm;

import java.util.HashSet;
import java.util.List;
import com.cn.zbt.crawlmeta.pojo.KeywordTab;
import com.cn.zbt.crawlmeta.service.KeywordTabSer;

public class ReadKeyword {
	private KeywordTabSer keywordTabService = (KeywordTabSer) GetService
			.getInstance().getService("keywordTabService");

	public HashSet<String> getKeyword() {
		HashSet<String> set = new HashSet<String>();
		List<KeywordTab> kts = keywordTabService.findAllKeyword();
		for (KeywordTab kt : kts) {
			set.add(kt.getKeyword_name());
		}
		return set;
	}
	public HashSet<String> getKeyworda() {
		HashSet<String> set = new HashSet<String>();
		List<KeywordTab> kts = keywordTabService.findAllKeyword();
		for (KeywordTab kt : kts) {
			if(kt.getKeyword_name().contains("+")){
				String s=kt.getKeyword_name().replace("+", ".*");
				set.add(".*"+s+".*");
				String s1=kt.getKeyword_name().split("\\+")[1].trim()+".*"+kt.getKeyword_name().split("\\+")[0].trim() ;
				set.add(".*"+s1+".*");
			}
			else{
				set.add(".*"+kt.getKeyword_name()+".*");
			}
 	}
		return set;
	}
	public static void main(String[] args) {
		HashSet<String> list = new ReadKeyword().getKeyworda();
		for (String li : list) {
			System.out.println(li);
		}
	}
}
