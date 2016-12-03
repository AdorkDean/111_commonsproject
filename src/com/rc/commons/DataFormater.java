package com.rc.commons;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

public class DataFormater {
	private static DecimalFormat  f = new DecimalFormat();
	public static String format(double value,int min,int max){
		f.setMaximumFractionDigits(max);
		f.setMinimumFractionDigits(min);
		f.setGroupingUsed(false);
		return f.format(value);
	}
	public static String format(BigDecimal b){
		double value = b.doubleValue();
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		f.setGroupingUsed(false);
		return f.format(value);
	}
	public static String format(double value){
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		f.setGroupingUsed(false);
		return f.format(value);
	}
	public static String format(String value){
		if(StringUtils.isBlank(value))
			return "0.00";
		
		double v = Double.parseDouble(value);
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		f.setGroupingUsed(false);
		return f.format(v);
	}
	public static void main(String[] args){
		System.out.println(DataFormater.format("1212121123.000"));
		
	}
}
