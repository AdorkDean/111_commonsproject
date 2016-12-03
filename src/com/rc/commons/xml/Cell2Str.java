package com.rc.commons.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.rc.commons.xml.bean.Address;
import com.rc.commons.xml.bean.Cell;
import com.rc.commons.xml.bean.ReJson;
import com.rc.commons.xml.bean.Row;
import com.rc.commons.xml.bean.Table;
import com.rc.commons.xml.bean.Workbook;
import com.rc.commons.xml.bean.Worksheet;
import com.thoughtworks.xstream.XStream;


/**  
 * @Title: Test.java
 * @Description: cell工具类，把数据转换成字符串，用于前台显示或者打印使用
 * 					setrows方法把list转成字符串，
 * 					array2Xml方法把二维数组转成字符串。
 * @author yinbinhome@163.com  
 * @date 2012-11-17 下午12:57:48
 * @version V1.0  
 */

public class Cell2Str {
	private Workbook Workbook=new Workbook();
	private List rowlist=new ArrayList();
	
	public String book(){
		Table Table=new Table();
		Table=settables(getRowlist());
		
		Worksheet Worksheet=new Worksheet();
		Worksheet.setTable(Table);
		Workbook.setWorksheet(Worksheet);
		
		XStream xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
		xstream.useAttributeFor(Cell.class, "Index");
		xstream.useAttributeFor(Row.class, "Index");
		xstream.alias("Worksheet", Worksheet.class);
		xstream.alias("Workbook", Workbook.class);
		xstream.alias("Row", Row.class);
		xstream.alias("Cell", Cell.class);
		String xml = xstream.toXML(Workbook);
		xml=updateXml(xml);
		ReJson rj=new ReJson();
		rj.setXml(xml);
		rj.setCount(rowlist.size()+"");
		JSONObject jsonStr=JSONObject.fromObject(rj);
		String reStr=jsonStr.toString();
		
		return reStr;
	}
	
	/**
	 * 设置table的内容
	 * @param list
	 * @return
	 */
	private Table settables(List list){
		Table Table=new Table();
		Table.setRowclude(list);
		return Table;
	}
	
	/**
	 * 设置行内容
	 * @param index
	 * @param list
	 * @return
	 */
	public void setrows(String index,List<String> list){
		Row Row=new Row();
		Row.setIndex(index);
		List cells1=new ArrayList();
		int i=1;
		for(String celldata:list){
			Cell cell=setcells(""+i++,celldata);
			cells1.add(cell);
		}
		Row.setRowcells(cells1);
		rowlist.add(Row);
	}
	
	/**
	 * 设置单元格内容
	 * @param index
	 * @param data
	 * @return
	 */
	private Cell setcells(String index,String data){
		Cell cell=new Cell();
		cell.setData(data);
		cell.setIndex(index);
		return cell;
	}

	/**
	 * 去掉无用字符串
	 * @param xml
	 * @return
	 */
	private String updateXml(String xml){
		xml=xml.replace("      <rowclude>", "");
		xml=xml.replace("      </rowclude>", "");
		xml=xml.replace("      <rowcells>", "");
		xml=xml.replace("      </rowcells>", "");
		xml=xml.replaceAll("\n", "");
		xml=xml.replaceAll(">\\s*<", "><");
		xml=xml.replaceAll("<Data>", "<Data  Type=\"String\">");
		return xml;
	}

	
	public  void createFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
			resultFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void array2Xml(String[][] arr){
		for(int i=0;i<arr.length;i++){
			List list=new ArrayList();
			for(int j=0;j<arr[i].length;j++){
				list.add(arr[i][j]);
			}
			setrows(i+1+"",list);
		}
	}


	private  Workbook getWorkbook() {
		return Workbook;
	}

	private void setWorkbook(Workbook workbook) {
		Workbook = workbook;
	}

	private List getRowlist() {
		return rowlist;
	}

	private void setRowlist(List rowlist) {
		this.rowlist = rowlist;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[][] arr={
		 {"1-900","1000","1200","1300"},{"2-500","800"},{"3-900","1000","1100","1200","1300"},{"700","800"}
		};
//		String[][] arr=new String[24][25];
//		arr[6][13]="123456";
//		arr[6][17]="17-6";
//		arr[11][11]="11-11";
//		arr[13][15]="15-13";
		Cell2Str t=new Cell2Str();
		t.array2Xml(arr);
//		List list1=new ArrayList();
//		list1.add("aaa");
//		list1.add("bbb");
//		list1.add("ccc");
//		List list2=new ArrayList();
//		list2.add("ddd");
//		list2.add("eee");
//		list2.add("fff");
//		List list3=new ArrayList();
//		list3.add("ggg");
//		list3.add("hhh");
//		list3.add("iii");
//		t.setrows("1", list1);
//		t.setrows("2", list2);
//		t.setrows("3", list3);
		String xml=t.book();
		System.out.println(xml);
		t.createFile("d:/123.txt", xml);
		Address add=new Address("尹滨","北京","清河小营桥北200米青尚办公区218室","15801313717");
		JSONObject jsonStr=JSONObject.fromObject(add);
		System.out.println(jsonStr);
	}
	
	
}
