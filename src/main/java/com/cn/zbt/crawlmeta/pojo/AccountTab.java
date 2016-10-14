package com.cn.zbt.crawlmeta.pojo;

public class AccountTab  {
	int id;//自增ID
	String username;//用户名
	String password;//密码
	int type;//有效性 1有效，0无效
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
