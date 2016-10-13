package com.cn.zbt.crawlmeta.dm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.cn.zbt.crawlmeta.pojo.KeywordTab;
import com.cn.zbt.crawlmeta.service.KeywordTabSer;

public class ReadKeyword {
	private KeywordTabSer keywordTabService = (KeywordTabSer) GetService.getInstance()
			.getService("keywordTabService");
	public List<String> getKeyword() {
		HashSet<String> list=new HashSet<String>();
		//List<String> list=new ArrayList<String>();
		List<KeywordTab> kts = keywordTabService.findAllKeyword();
		for(KeywordTab kt:kts){
			String[] ss=kt.getKeyword().split(" ");
			for(String s:ss){
				list.add(s);
			}			
		}
		return list;
	}

	public static void main(String[] args) {
		List<String>  list=new ReadKeyword().getKeyword();
		for (String li:list){
			System.out.println(li);
		}
	}
}