package com.cn.zbt.crawlmeta.dm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetCharset {
	// I used 1000 bytes at first, but  found that some documents have
	  // meta tag well past the first 1000 bytes.
	  // (e.g. http://cn.promo.yahoo.com/customcare/music.html)
	  private static final int CHUNK_SIZE = 2000;

	 private static Pattern metaPattern =
			    Pattern.compile("<meta\\s+([^>]*http-equiv=(\"|')?content-type(\"|')?[^>]*)>",
			        Pattern.CASE_INSENSITIVE);
	private static Pattern charsetPattern =
			    Pattern.compile("charset=\\s*([a-z][_\\-0-9a-z]*)",
			        Pattern.CASE_INSENSITIVE);
	public static void main(String[] args) throws IOException {
		String url="http://hsb.hsw.cn/2006-05/29/content_5455541.htm";
		Document doc= Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36").timeout(5000).get();
		String content=doc.toString();
		String encoding=sniffCharacterEncoding(ByteBuffer.wrap(content.getBytes("GBK")));
		System.out.println(encoding);
		
		if(null==encoding||"".equals(encoding)){
			encoding="UTF-8";
		}
		byte[] bytes = content.getBytes(); 
		String str = new String(bytes, encoding); 
		System.out.println(str);
		//System.out.println(getEncoding(content));

		
	}
	private static String sniffCharacterEncoding(ByteBuffer content) {
	    int length = Math.min(content.remaining(), CHUNK_SIZE);

	    // We don't care about non-ASCII parts so that it's sufficient
	    // to just inflate each byte to a 16-bit value by padding.
	    // For instance, the sequence {0x41, 0x82, 0xb7} will be turned into
	    // {U+0041, U+0082, U+00B7}.
	    String str = "";
	    try {
	      str = new String(content.array(), content.arrayOffset() + content.position(),
	          length, Charset.forName("ASCII").toString());
	    } catch (UnsupportedEncodingException e) {
	      // code should never come here, but just in case...
	      return null;
	    }

	    Matcher metaMatcher = metaPattern.matcher(str);
	    String encoding = null;
	    if (metaMatcher.find()) {
	      Matcher charsetMatcher = charsetPattern.matcher(metaMatcher.group(1));
	      if (charsetMatcher.find())
	        encoding = new String(charsetMatcher.group(1));
	    }

	    return encoding;
	  }
}
