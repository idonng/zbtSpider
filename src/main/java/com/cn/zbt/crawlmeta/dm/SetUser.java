package com.cn.zbt.crawlmeta.dm;

import com.cn.zbt.crawlmeta.pojo.AccountTab;
import com.cn.zbt.crawlmeta.service.AccountTabSer;

public class SetUser {
	private AccountTabSer accountTabService = (AccountTabSer) GetService.getInstance()
			.getService("accountTabService");

	public AccountTab setUser() {
		AccountTab it = accountTabService.findValue();
		return it;
	}

}
