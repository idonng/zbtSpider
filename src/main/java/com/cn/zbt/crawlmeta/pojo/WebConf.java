package com.cn.zbt.crawlmeta.pojo;

import java.util.Date;

public class WebConf {
	
	//网站自增ID
    private Integer webKey;
    
    //网站英文域名
    private String hostName;

    //网站中文名称
    private String chsName;

    //网站预警重要等级
    private Integer webLevel;

    //操作人ID
    private Long userId;

    //最后更新时间
    private Date lastupdateTime;

    public Integer getWebKey() {
        return webKey;
    }

    public void setWebKey(Integer webKey) {
        this.webKey = webKey;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName == null ? null : hostName.trim();
    }

    public String getChsName() {
        return chsName;
    }

    public void setChsName(String chsName) {
        this.chsName = chsName == null ? null : chsName.trim();
    }

    public Integer getWebLevel() {
        return webLevel;
    }

    public void setWebLevel(Integer webLevel) {
        this.webLevel = webLevel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(Date lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
    }
}