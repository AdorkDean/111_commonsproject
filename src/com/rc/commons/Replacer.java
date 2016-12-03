package com.rc.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Replacer {
	public Replacer() {
	}

	public static void main(String args[]) {

		try {
			      String dirname = "D:\\test\\vo";
			      String newDirname = "d:/test/newVo";
			if (args.length == 1) {
				if (args[0] != null) {
					dirname = args[0];
				}
			}
			if (args.length == 2) {
				if (args[1] != null) {
					newDirname = args[1];
				}
			}

			File folder = new File(dirname);
			
			System.out.println("folder.isDirectory() :" + folder.isDirectory());
			if (folder.isDirectory()) {
				
				String[] files = folder.list();
				int num = 0;
				
				for (int i = 0; i < files.length; i++) {
					
					if (files[i].endsWith("hbm.xml")) {
						num++;
						System.out.println("file " + num + ":" + files[i]);
						File oldFile = new File(dirname + "/" + files[i]);
						FileReader fr = new FileReader(oldFile);
						FileWriter fw =
							new FileWriter(newDirname + "/" + files[i]);
						
						BufferedReader br = new BufferedReader(fr);
						String line;
						boolean sqlFlag = true;
						boolean tableFlag = true;
						String tableName = "";
						
						String lastline = "";
						
						while ((line = br.readLine()) != null) {
							if (line.lastIndexOf("java.lang.Object") > 0) {
							  line = line.replaceFirst("java.lang.Object", "java.lang.String");
							}
							
							if (line.lastIndexOf("set") > 0) {
								line = line.replaceFirst("set", "bag");
							}
							if(lastline.endsWith("name=\"id\"")){
							
							if (line.lastIndexOf("java.math.BigDecimal") > 0) {
								
								line =
									line.replaceFirst(
										"java.math.BigDecimal",
										"long");
							}}

							if (line.lastIndexOf("column=\"ID\"") > 0) {
								line =
									line.replaceFirst(
										"column=\"ID\"",
										"column=\"ID\""
											+ "\n"
											+ "unsaved-value=\"0\"");
							}

							if (tableFlag && line.indexOf("table=") > 0) {

								tableFlag = false;
								tableName =
									line.substring(
										line.indexOf("\"") + 1,
										line.lastIndexOf("\""));
								System.out.println("");
								System.out.println(
									"      tableName :" + tableName);
							}

							if (sqlFlag
								&& !"".equals(tableName)
								&& line.lastIndexOf(
									"generator-class=\"assigned\"")
									> 0) {

								line =
									line.replaceFirst("assigned", "sequence");
							}


							if (sqlFlag
								&& !"".equals(tableName)
								&& line.lastIndexOf(
									"<generator class=\"assigned\" />")
									> 0) {
								sqlFlag = false;
								String seqName = "";
								if (tableName.endsWith("IES")) {
									tableName =
										tableName.substring(
											0,
											tableName.lastIndexOf("IES"));
									tableName = tableName + "Y";
								} else if (tableName.endsWith("S")) {
									tableName =
										tableName.substring(
											0,
											tableName.lastIndexOf("S"));
								}
								seqName = tableName + "_SEQ";
								System.out.println(
									"        seqName :" + seqName);
								System.out.println("");
								line =
									"<generator class=\"sequence\">\n"
										+ "<param name=\"sequence\">"
										+ seqName
										+ "</param>\n"
										+ "</generator>\n";
							}
							lastline=line;
							fw.write(line + "\n");
						}
						fw.close();
						fr.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
