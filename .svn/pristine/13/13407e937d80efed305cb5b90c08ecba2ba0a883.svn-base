package com.cn.zbt.crawlmeta.crawl;

import java.util.Date;

import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.dm.SetProxy;
import com.cn.zbt.crawlmeta.pojo.IpTab;
import com.cn.zbt.crawlmeta.service.IpTabSer;

public class GetIp {
	private static IpTabSer ipTabService = (IpTabSer) GetService.getInstance()
			.getService("ipTabService");

	public static void main(String[] args) throws InterruptedException {

		SetProxy gi = new SetProxy();
		// 从代理网站获取代理

		// gi.crawl();
		// System.out.println("抓取完成"+ new Date(System.currentTimeMillis()));
		// 从数据库刷新代理
		for (int i = 0; i < 1800; i++) {
			IpTab it = ipTabService.findValue();
			gi.warh(it);
			System.out.println("正在处理第"+i+"个：" +it.getIp()  );
		}
		System.out.println("冲洗完成" + new Date(System.currentTimeMillis()));
	}

}
