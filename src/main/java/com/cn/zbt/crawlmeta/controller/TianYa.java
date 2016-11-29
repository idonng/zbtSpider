package com.cn.zbt.crawlmeta.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.cn.zbt.crawlmeta.dm.CommonUtils;
import com.cn.zbt.crawlmeta.dm.Ctext;
import com.cn.zbt.crawlmeta.dm.GetService;
import com.cn.zbt.crawlmeta.dm.ReadKeyword;
import com.cn.zbt.crawlmeta.dm.SetProxy;
import com.cn.zbt.crawlmeta.service.ResultTabSer;

public class TianYa {
	private static final Logger logger = Logger.getLogger(TianYa.class);
	private static ResultTabSer resultTabService = (ResultTabSer) GetService
			.getInstance().getService("resultTabService");

	/**
	 * 爬取天涯网
	 * 
	 * @param url
	 *            q :关键字 p:页码
	 * @return
	 */
	@SuppressWarnings("finally")
	public Document fetch(String url, String q, String pn) {
		Document doc = null;
		Connection conn = null;
		try {
			// new SetIp().setIp();
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
					.timeout(4000);
			conn.data("q", q).data("pn", pn);
			doc = conn.get();
		} catch (IOException e) {
			logger.error(" 访问天涯网异常==========>", e);
		} finally {
			return doc;
		}
	}

	/**
	 * 爬取天涯网
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("finally")
	public Document fetch(String url) {
		Document doc = null;
		Connection conn = null;
		try {
			// new SetIp().setIp();
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
					.timeout(2000);
			doc = conn.get();
		} catch (IOException e) {
			doc = fetch_b(url);
			logger.error(" 访问天涯网异常==========>", e);
		} finally {
			return doc;
		}
	}

	@SuppressWarnings("finally")
	public Document fetch_b(String url) {
		Document doc = null;
		Connection conn = null;
		try {
			new SetProxy().setIp();
			conn = Jsoup
					.connect(url)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
					.timeout(2000);
			doc = conn.get();
		} catch (IOException e) {
			logger.error(" 访问天涯网异常==========>", e);
		} finally {
			return doc;
		}
	}

	public void getDoc(String keyword) {
		int i = 0;
		String reg = "";
		do {
			reg = "";
			String url = "http://search.tianya.cn/bbs";
			i = i + 1;
			if (i > 75 || i < 0) {
				logger.info("页数异常" + url);
				return;
			}
			try {
				logger.info("开始解析关键字为:“" + keyword + "” 页数为：" + i);
				Document doc = new TianYa().fetch(url, keyword, i + "");
				if (doc.toString().contains("没有找到含有")) {
					logger.info("页数异常" + url);
					return;
				}
				reg = doc.getElementsByClass("long-pages").last().toString();
				getData(doc, keyword);
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				i--;
				logger.error("解析错误，关键字为:" + keyword + "页数为：" + i + "。异常详情：" + e);
			}
			System.gc();
		} while (!reg.contains("<span>下一页</span>"));

	}

	public void getData(Document doc, String keyword) {
		Date sinatime_now = new Date();
		Elements elements = doc.select(".searchListOne>ul>li");
		for (int p = 0; p < elements.size() - 1; p++) {
			Element element = elements.get(p).select("div>h3>a").first();
			String title = "";
			String content = "";
			String url = "";
			url = element.attr("href");
			logger.info("正在处理：" + url);
			Long cue=CommonUtils.checkUrlExist(url);
			if (cue!=0) {
				logger.info("已经处理，跳过URL：" + url);
				continue;
			}
			try {
				Document doc1 = new TianYa().fetch(url);
				title = doc1.select("title").first().text().trim();
				String ctStr = CommonUtils.getRegexTime( 
						Ctext.deleteLabel(doc1.toString())).trim();
				Date pubdate = new Date();
				// 转换各种格式的日期
				pubdate = (CommonUtils.matchDateString(ctStr) == null ? sinatime_now
						: CommonUtils.matchDateString(ctStr));
				pubdate = pubdate.compareTo(sinatime_now) > 0 ? sinatime_now
						: pubdate;
				content = Ctext.deleteLabel(doc1.toString()).trim();
				Map<Integer, String> map = Ctext.splitBlock(content);
				// 数据库字段超长、后续可修改
				if (Ctext.judgeBlocks(map).length() > 9999) {
					content = Ctext.judgeBlocks(map).substring(0, 9999);
				} else {
					content = Ctext.judgeBlocks(map);
				}
				content = (content.length()==0) ? title : content;
				if (!CommonUtils.checkContent(content)
						&& !CommonUtils.checkContent(title)) {
					continue;
				}
				// 作者
				String author = CommonUtils.getRegex(
						"楼主：<a href[^>]*?>(.*?)</a>", doc1.toString()).trim();
				author = (author.length() == 0 || author == null) ? "天涯网"
						: author;
				// 转发量，评论量。新闻稿有的不存在，需要后续处理，进行热度排序推送
				String zfs = CommonUtils.getRegex("点击：(.*?)回复",
						Ctext.deleteLabel(doc1.toString())).trim();
				zfs = (zfs.length() == 0 || zfs == null) ? "0" : zfs;

				String pls = CommonUtils.getRegex(
						"回复：(.*?)(<|[\u4e00-\u9fa5])",
						doc1.toString().replace("\n", "").replace("\r", "")
								.replace("&nbsp;", " ")).trim();
				pls = (pls.length() == 0 || pls == null) ? "0" : pls;
				int type = 3;
				if (url.contains("weibo")) {
					type = 1;
				} else if (url.contains("blog")) {
					type = 4;
				} else if (url.contains("news")) {
					type = 2;
				}
				resultTabService.insertRes(CommonUtils.setMD5(url), title, url,
						content, CommonUtils.getHost(url), type, keyword,
						Integer.valueOf(pls), Integer.valueOf(zfs), pubdate,
						sinatime_now, author, sinatime_now);
				logger.info("URL:" + url + "     " + "提取完成。");
			} catch (Exception e) {
				logger.error("解析错误:" + url + "错误详情： " + e);
			}

		}
	}

	public void runInter() {
		HashSet<String> keywords1 = new ReadKeyword().getKeyword();
		for (final String keyword : keywords1) {
			logger.info("----关键词:" + keyword + " 爬取开始----"
					+ new Date(System.currentTimeMillis()));
			if (keyword != null && keyword.trim().length() != 0) {
				getDoc(keyword.trim());
			}
			logger.info("----关键词:" + keyword + " 爬取结束----"
					+ new Date(System.currentTimeMillis()));
		}
	}

	public static void main(String[] args) throws IOException {
		logger.info("----爬取开始----" + new Date(System.currentTimeMillis()));

		TianYa sw = new TianYa();
		// sw.runInter();
		sw.getDoc("赵超+步长");
		logger.info("----全部主页爬取结束----" + new Date(System.currentTimeMillis()));
	}
}
