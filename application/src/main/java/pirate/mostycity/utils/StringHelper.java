package pirate.mostycity.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.wicket.util.string.Strings;

public class StringHelper implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static String getFileExtension(String fileName) {

		String extension = null;
		if (fileName != null) {
			int dotIndex = fileName.lastIndexOf(".");
			if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
				extension = fileName.substring(dotIndex + 1).toLowerCase();
			}
		}
		return extension;
	}
	
	public static String getSubString(String s, int maxLength){
		if (s == null) {
			return null;
		}
		if(s.length()<maxLength){
			return s;
		}else{
			Matcher m = Pattern.compile("^.{0,"+maxLength+"}\\b").matcher(s); 
			m.find(); 
			String res = m.group(0);
			if(res.contains("<d")&& !res.contains("</div>")){
				res = s.substring(0, s.indexOf("</div>")+6);
			}else
			if(res.contains("<d")&&res.contains("</div>")){
				String sub = res.substring(res.indexOf("</div>")+6);
				if(sub.contains("<d")){
					sub = sub.substring(sub.indexOf("<d"));
					int max = res.length()-sub.length();
					res = s.substring(0, max);
				}
			}
			
			maxLength = res.length()- checkOnLink(res);
			res = res.substring(0, maxLength);
			
			return res+"...";
		}
	}
	
	private static int checkOnLink(String s){
		
		int i = 0;
		
		while(s.contains("<a")){
			s = s.substring(s.indexOf("<a"));
			if(s.contains("</a>")){
				s = s.substring(s.indexOf("</a>")+4);
			}else{
				return s.length();
			}
		}
		return i;
	}
	public static String toMultilineMarkup(String replaceArea) {

		if (replaceArea == null) {
			return null;
		}

		return Strings.toMultilineMarkup(replaceArea).toString();
	}
	
	public static boolean isEmpty(String s) {

		return s == null || s.trim().length() == 0;
	}
	
	public static String formatDate(Date date, String format){
		return DateFormatUtils.format(date, format).toString();
	}
	
	public static String formatDate(Date date){
		return formatDate(date, "dd-MM-yyyy HH:mm:ss");
	}
}
