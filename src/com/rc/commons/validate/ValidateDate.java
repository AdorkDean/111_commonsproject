package com.rc.commons.validate;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>公共方法类</p>
 * <p>校验日期</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 */

public class ValidateDate implements Validator{
       /**
        * 初始化
        */
        public void init() {
        }

        /**
         * 日期是否有效
         * @param value Object
         * @return String
         */
        public String validate(Object value) {
                String date = (String) value;
                try {
                        Date d = Date.valueOf(date);
                        return null;
                } catch (RuntimeException e) {
                        return "日期格式不正确(格式应为：yyyy-MM-dd)！";
                }
        }

        /**
         * 正则表达式验证
         * @param sDate
         * @return
         */
        public boolean isValidDate(String sDate) { 
    		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}"; 
    		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))" 
    		+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|" 
    		+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?" 
    		+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?(" 
    		+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?" 
    		+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))"; 
    		if ((sDate != null)) { 
    			Pattern pattern = Pattern.compile(datePattern1); 
    			Matcher match = pattern.matcher(sDate); 
    			
    			if (match.matches()) { 
    				pattern = Pattern.compile(datePattern2); 
    			
    				match = pattern.matcher(sDate); 
    				return match.matches(); 
    			} 
    			else { 
    				return false; 
    			} 
    		}
    		
    		return false; 
    	} 
        
        //日期比较
    	public boolean compDate(String startTime,String endTime){		
    		if (Integer.parseInt(endTime.substring(0, 4)) > Integer.parseInt(startTime.substring(0, 4)))
    			return true;
    		else if (Integer.parseInt(endTime.substring(0, 4)) == Integer.parseInt(startTime.substring(0, 4)))
    		{
    			if (Integer.parseInt(endTime.substring(5, 7)) > Integer.parseInt(startTime.substring(5, 7)))
    				return true;
    			else if(Integer.parseInt(endTime.substring(5, 7)) == Integer.parseInt(startTime.substring(5, 7))){
    				if(Integer.parseInt(endTime.substring(8,10)) >= Integer.parseInt(startTime.substring(8,10)))
    					return true;
    				else
    					return false;
    			}
    			else
    				return false;
    		}
    		else
    			return false;
    	}
}
