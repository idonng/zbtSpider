package com.cn.zbt.crawlmeta.pojo;

import java.sql.Timestamp;
import java.util.Date;

public class ResultTab {
	public int id;
	public String urlmd5;
	public String title;
	public String url;
	public String content;
	public int pls;
	public int zfs;
	public Date pubdate;
	public Date crawldate;
	public String keyword;
	public String author;
	public int emoflag;

	public String getUrlmd5() {
		return urlmd5;
	}

	public void setUrlmd5(String urlmd5) {
		this.urlmd5 = urlmd5;
	}

	public Date getCrawldate() {
		return crawldate;
	}

	public void setCrawldate(Date crawldate) {
		this.crawldate = crawldate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getEmoflag() {
		return emoflag;
	}

	public void setEmoflag(int emoflag) {
		this.emoflag = emoflag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPls() {
		return pls;
	}

	public void setPls(int pls) {
		this.pls = pls;
	}

	public int getZfs() {
		return zfs;
	}

	public void setZfs(int zfs) {
		this.zfs = zfs;
	}

	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	 
}
