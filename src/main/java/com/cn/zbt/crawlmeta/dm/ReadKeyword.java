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

	public static void main(String[] args) {
		HashSet<String> list = new ReadKeyword().getKeyword();
		for (String li : list) {
			System.out.println(li);
		}
	}
}
