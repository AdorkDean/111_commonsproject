package com.rc.commons.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.rc.commons.date.UtilDate;
/**  
* @Title: ExcelUtil.java
* @Description: excel工具类，生成的表格类型都是文本格式的，需要特殊格式的单元格需要自行写方法。
* @author yinbinhome@163.com
* @date 2012-2-17 下午01:38:39
* @version V1.0  
*/ 
public class ExcelUtil {
	
	/**
	 * @param filename 要生成excel的文件名字，带路径
	 * @param headList 表头
	 * @param contentArray 内容数组，是一个二维数组
	 */
	public static boolean createExcel(String filename, List<String> headList, String[][] contentArray) {
		try {
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(filename));
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet(" 第一页 ", 0);
			WritableCellFormat format=new WritableCellFormat();
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setWrap(true);
			// 写列头
			
			for (int i=0;i<headList.size();i++) {
				Label label = new Label(i, 0, headList.get(i));
				sheet.addCell(label);
			}
			//i 行 j 列
			for (int i = 0; i < contentArray.length; i++) { 
				for (int j = 0; j < contentArray[i].length; j++) {
					Label label = new Label(j, i+1, contentArray[i][j],format);
					sheet.addCell(label);
				}
			}
			book.write();
			book.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	/**
	 * 带格式化的导出
	 * @param filename 要生成excel的文件名字，带路径
	 * @param headList 表头
	 * @param contentArray 内容数组，是一个二维数组
	 */
	public static boolean createExcelByFormat(String filename, List<String> headList, String[][] contentArray) {
		try { 
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(filename));
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet(" 第一页 ", 0);

			// 写列头
			WritableCellFormat format1 = new WritableCellFormat();
			format1.setBackground(jxl.format.Colour.DARK_RED);
			//format1.setAlignment(jxl.format.Alignment.CENTRE);
			for (int i=0;i<headList.size();i++) {
				Label label = new Label(i, 0, headList.get(i),format1);
				sheet.addCell(label);
			}
			//i 行 j 列
			for (int i = 0; i < contentArray.length; i++) {
				for (int j = 0; j < contentArray[i].length; j++) {
					Label label = new Label(j, i+1, contentArray[i][j]);
					sheet.addCell(label);
				}
			}
			book.write();
			book.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void ReadExcel()  {
	         try   {
	            Workbook book  =  Workbook.getWorkbook( new  File( " test.xls " ));
	             //  获得第一个工作表对象
	            Sheet sheet  =  book.getSheet( 0 );
	             //  得到第一列第一行的单元格
	            Cell cell1  =  sheet.getCell( 0 ,  0 );
	            String result  =  cell1.getContents();
	            System.out.println(result);
	            book.close();
	        }   catch  (Exception e)  {
	            System.out.println(e);
	        }
	}
	public static void main(String[] args) throws Exception {
		String name = "C:/Users/laihama/Desktop/temp/test/excel_export_temp_" + UtilDate.getOrderNum() + ".xls";
		String[][] a=new String[3][4]; //行*列
		a[0][0]=1+"";
		a[0][1]=2+"";
		a[0][2]=3+"";
		a[0][3]=4+"";
		a[1][0]=5+"";
		a[1][1]=6+"";
		a[1][2]=7+"";
		a[1][3]=8+"";
		a[2][0]=9+"";
		a[2][1]=10+"";
		a[2][2]=11+"";
		a[2][3]=12+"";
		List<String> list=new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		
		ExcelUtil.createExcelByFormat(name,list,a);
	}  
}
