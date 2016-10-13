package com.cn.zbt.crawlmeta.pojo;

public class IpTab  {
	int id;//自增ID
	String ip;//代理IP
	String port;//端口
	int type;//有效性 1有效，0无效
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
