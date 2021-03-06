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
import org.jsoup.nodes.Document;
  /**
   * 基于行块间文字密度提取正文
   * @author Administrator
   *
   */
  
public class TestCtext {  
  
    /**  
     * 行分块的大小(块大小=BLOCKS+1)  
     */  
    private static int BLOCKS = 0;  
    /**  
     * 判断为正文的文字骤变率  
     */  
   // private static float CHANGE_RATE = 0.6f;  
    /**  
     * 每行最小长度  
     * @throws IOException 
     */  
   // private static int MIN_LENGTH = 9;  
      
    public static void main(String[] args) throws IOException {  
    	String url="https://www.zhihu.com/question/34659387";
    	Document doc=		 Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36").timeout(5000).get();
    	String s =doc.select("p").text();
    	System.out.println(s);
        String html="";
		try {
			html = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36").timeout(5000).get().toString();
			html = deleteLabel(html);
			 Map<Integer, String>  map=new HashMap<Integer, String>();
			 map=splitBlock(html,6);
			 System.out.println(map.toString());
			String content=judgeBlocks(splitBlock(html,6),0.3f);
			 System.out.println(content);
		    // System.out.println(CommonUtils.checkContent("aaa为纪念红军长征胜利80周年，山东人民广播电台联合山东中西医结合学会脑心同治专业委员会和步长制药，在济南举行了\"共铸中国心，脑心同治在行动--寻找共和国的功臣\"公益活动。山东台记者崔立霞报道：（题目：慰问抗战老兵时间：）【压混：您坐下吧王爷爷，我给您介绍下，我是山东人民广播电台范青，您好……】12号下午，我台记者与步长制药和省中医药大学的专家共20人走进94岁的抗战老兵王默村的家里，为他带去了国药准字号的心脑血管药品和部分生活用品。山东步长制药股份有限公司副董事长、山东丹红制药有限公司董事长赵菁当面表达了对抗战老兵王默村的崇敬与感激：【因为你们给我们打下的天下，才有我们现在的幸福生活，所以我们不能忘了你们。王默村：我做的工作很少，微薄的一点工作，受到大家的爱护、大家的关爱，我很感激。】现场，专家也为老兵王默村做了相关检查，并提醒日常生活的注意事项。随后又看望慰问了90岁抗战老兵于文卿，记者与于文卿促膝交谈，聆听老人的抗战故事。13号上午，该公益活动工作人员走进了济南漱玉平民大药房的6家门店和济南长清区中医医院，举行了大型义诊活动，数千名听众免费接受了健康检查。"));  
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
        String regEx_href ="<a\\s+[^>]*?>[\\s\\S]*?<\\/a>";// 定义href标签的正则表达式    
        String regEx_anno = "<!--[\\s\\S]*?-->"; //html注释  
        //String regEx_title = "<title[^>]*?>[\\s\\S]*?<\\/title>"; //定义title标签的正则表达式 
        //html = html.replaceAll(regEx_title, "");
        html=html.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&nbsp;", "");
        html = html.replaceAll(regEx_href, "");  
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
	public static Map<Integer, String> splitBlock(String text, int MIN_LENGTH){  
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
    public static String judgeBlocks(Map<Integer, String> map,float CHANGE_RATE){  
        Set<Entry<Integer, String>> sets = map.entrySet();  
        List<Integer> contentBlock = new ArrayList<Integer>();  
        int currentBlock = map.get(0).length(); //当前行的长度  
        int lastBlock = 0; //上一行的长度  
          
        for(Entry<Integer, String> set:sets){  
            lastBlock = currentBlock;  
            currentBlock = set.getValue().length();  
            float between = (float)Math.abs(currentBlock - lastBlock)/Math.max(currentBlock, lastBlock);  
            if (between>=CHANGE_RATE) {
            	System.out.println("-------"+set.getKey());
                contentBlock.add(set.getKey());
               // System.out.println(map.get(set.getKey()));
            }  
        }  
        //下面是取多个峰值节点中两个节点之间内容长度最大的内容  
        int matchNode = contentBlock.size();  
        //System.out.println("一共有"+matchNode+"峰值点");  
  /*      int lastContent = 0;//前一个两节点之间的内容长度  
        String context = null;//结果  
        if (matchNode>2) {   
            for(int i=1;i<matchNode;i++){  
                String result = "";  
                for(int j=contentBlock.get(i-1);j<contentBlock.get(i);j++){  
                	System.out.println("-----:"+j);
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
                	System.out.println("-----:"+j);
                	context +=map.get(j);  
                }  
                 
            }  
        }   
        return context==null?"":context;  
    }  
}  