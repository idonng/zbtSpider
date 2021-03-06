package com.cn.zbt.crawlmeta.dm;
import java.io.BufferedReader;  
import java.io.ByteArrayInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  
import java.util.Set;  

import org.jsoup.Jsoup;
  /**
   * 基于行块间文字密度提取正文
   * @author Administrator
   *
   */
  
public class Ctext {  
  
    /**  
     * 行分块的大小(块大小=BLOCKS+1)  
     */  
    private static int BLOCKS = 0;  
    /**  
     * 判断为正文的文字骤变率  
     */  
    private static float CHANGE_RATE = 0.3f;  
    /**  
     * 每行最小长度  
     */  
    private static int MIN_LENGTH = 6;  
      
    public static void main(String[] args) {  
  
    	String url="http://news.hsw.cn/s/2014/1028/170645.shtml";
        String html;
		try {
			html = Jsoup.connect(url).get().toString();
			 html = deleteLabel(html);  
		     Map<Integer, String> map = splitBlock(html);  
		     System.out.println(judgeBlocks(map));  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
       
    }  
      
    /**  
     * 去除html标签  
     * @param html 请求获得的html文本  
     * @return 纯文本  
     */  
    public static String deleteLabel(String html){  
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式    
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式    
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式    
        //String regEx_href ="<a\\s+[^>]*?>[\\s\\S]*?<\\/a>";// 定义href标签的正则表达式    
        String regEx_anno = "<!--[\\s\\S]*?-->"; //html注释  
        //html = html.replaceAll(regEx_href, "");  
        html=html.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&nbsp;", "");
        html = html.replaceAll(regEx_script, "");  
        html = html.replaceAll(regEx_style, "");  
        html = html.replaceAll(regEx_anno, "");
        html = html.replaceAll(regEx_html, "");
        html = html.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");//去除空白行  
        html = html.replaceAll("    +| +|　+|&nbsp;", ""); //去除空白  
        return html.trim();  
    }  
  
    /**  
     * 将纯文本按BLOCKS分块  
     * @param text 纯文本  
     * @return 分块后的map集合,键即为块号,值为块内容  
     */  
    @SuppressWarnings("unused")
	public static Map<Integer, String> splitBlock(String text){  
        Map<Integer, String> groupMap = new HashMap<Integer, String>();  
        ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes());  
        BufferedReader br = new BufferedReader(new InputStreamReader(bais));  
        String line = null,blocksLine = "";  
        int theCount = 0,groupCount = 0,count=0;//1.记录每次添加的行数；2.记录块号；3.记录总行数  
        try {  
            while((line=br.readLine())!=null){  
                if (line.length()>MIN_LENGTH) {  
                    //System.out.println(line);  
                    if (theCount<=BLOCKS) {  
                        blocksLine +=line.trim();   
                        theCount++;  
                    }  
                    else {  
                        groupMap.put(groupCount, blocksLine);  
                        groupCount++;  
                        blocksLine = line.trim();  
                        theCount = 1;  
                    }  
                    count++;  
                }  
            }  
            if (theCount!=0) {//加上没凑齐的给给定块数的  
                groupMap.put(groupCount+1, blocksLine);  
            }  
          //  System.out.println("一共"+groupMap.size()+"个行块，数据行数一共有"+count);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return groupMap;  
    }  
      
    /**  
     * 分析每块之间变化的情况  
     * @param map 块集合  
     * @return 正文  
     */  
    public static String judgeBlocks(Map<Integer, String> map){  
        Set<Entry<Integer, String>> sets = map.entrySet();  
        List<Integer> contentBlock = new ArrayList<Integer>();  
        int currentBlock = map.get(0).length(); //当前行的长度  
        int lastBlock = 0; //上一行的长度  
          
        for(Entry<Integer, String> set:sets){  
            lastBlock = currentBlock;  
            currentBlock = set.getValue().length();  
            float between = (float)Math.abs(currentBlock - lastBlock)/Math.max(currentBlock, lastBlock);  
            if (between>=CHANGE_RATE) {  
                contentBlock.add(set.getKey());  
            }  
        }  
        //下面是取多个峰值节点中两个节点之间内容长度最大的内容  
        int matchNode = contentBlock.size();  
        //System.out.println("一共有"+matchNode+"峰值点");  
     /*   int lastContent = 0;//前一个两节点之间的内容长度  
        String context = null;//结果  
        if (matchNode>2) {   
            for(int i=1;i<matchNode;i++){  
                String result = "";  
                for(int j=contentBlock.get(i-1);j<contentBlock.get(i);j++){  
                    result +=map.get(j);  
                }  
                if (result.length()>lastContent) {  
                    lastContent = result.length();  
                    context = result;  
                }  
            }  
        } */ 
       
        String context = "";//结果  
        if (matchNode>2) {   
            for(int i=1;i<matchNode;i++){  
                for(int j=contentBlock.get(i-1);j<contentBlock.get(i);j++){  
                	context +=map.get(j);  
                }  
            }  
        }  
        return context==null?"":context;  
    }  
}  