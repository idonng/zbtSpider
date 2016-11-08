package com.cn.zbt.crawlmeta.crawl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cn.zbt.crawlmeta.controller.TianYa;
import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.Ctext;

public class ParserJob {
	public static void main(String[] args) throws IOException {
		String url = "http://3g.163.com/news/15/0506/06/AOTOHFQA00014AEE_2.html";

		Document doc = Jsoup.connect(url).get();

		String content = doc.toString();
		String ctStr =CommonUtils.getRegex("((\\d{2}|((1|2)\\d{3}))(-|年)\\d{2}(-|月)\\d{2}(日|)( \\d{1,2}:\\d{1,2}(:\\d{1,2}|)|))",
				content.replace("\n", "")
					.replace("\r", "").replace("&nbsp;", " ")).trim();
		
		
		System.out.println(ctStr);
		String result = "";
		Pattern p = Pattern.compile("回复：(.*?)(<|[\u4e00-\u9fa5])");
		List matches = null;
		Matcher matcher = p.matcher(content.replace("\n", "").replace("\r", "")
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

		System.out.println(result);

	}
}
