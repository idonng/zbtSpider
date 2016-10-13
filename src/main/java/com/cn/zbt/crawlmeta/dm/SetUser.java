package com.cn.zbt.crawlmeta.dm;

import com.cn.zbt.crawlmeta.pojo.UserTab;
import com.cn.zbt.crawlmeta.service.UserTabSer;

public class SetUser {
	private UserTabSer userTabService = (UserTabSer) GetService.getInstance()
			.getService("userTabService");

	public UserTab setUser() {
		UserTab it = userTabService.findValue();
		return it;
	}

}
