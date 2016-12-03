package com.rc.commons.freemarker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**  
 * @Title: FileUtil.java
 * @Description: 
 * @author yinbinhome@163.com  
 * @date 2011-5-3 下午04:33:27
 * @version V1.0  
 */

public class FileUtil {
	
	public static  String readFileByLines(String fileName) {
        File file = new File(fileName);       
        BufferedReader reader = null;
        StringBuffer sb=new StringBuffer("");
        try {            
        	InputStreamReader read = new InputStreamReader (new FileInputStream(file),"utf-8");

            reader = new BufferedReader(read);
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                line++;
                
                sb.append(tempString);
                
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString().replaceAll("(>\\s*<)", "><");
    }
	
	public static void createFile(String content,String fileName){
		File file=new File(fileName);

		String parent=file.getParent();
		File f=new File(parent);		
		if(!f.exists()){
			f.mkdirs();
		}
        try {
        	OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        	outputStream.write(content);
        	outputStream.close();
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
//            FileWriter writer = new FileWriter(fileName, false);
//            writer.write(content);
//            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	public static void main(String[] args) {
		FileUtil.createFile("1233", "D:/temp/123/123/12.html");
	}
}
