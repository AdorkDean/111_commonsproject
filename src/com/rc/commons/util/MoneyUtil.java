package com.rc.commons.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

/**
 * <p>公共方法类</p>
 * <p>提供有关金额的实用方法集</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class MoneyUtil {
	public MoneyUtil() {
	}
	/**
	 * 将输入的金额转化成"￥X,XXX.XX"格式的字符串
	 * @param ym 指定的金额
	 * @return 格式化的字符串
	 */
	public static String bigDecimal2Money(BigDecimal ym) {
		if (ym == null) {
			return "0.00";
		} else {
			BigDecimal money = new Money(ym).getAmount();
			NumberFormat df = NumberFormat.getCurrencyInstance(Locale.CHINA);
			String mStr = df.format(money);
			return mStr;
		}
	}

	/**
	 * 返回格式化成money型的字符串
	 * @param obj Object 被转换的对象
	 * @return String 格式化成money型的字符串
	 */
	public static String objToMoney(Object obj) {
		if (obj == null) {
			return "0.00";
		} else {
			try {
				if (obj instanceof BigDecimal) {
					return bigDecimal2Money((BigDecimal) obj);
				} else if (obj instanceof String) {
					return bigDecimal2Money(new BigDecimal((String) obj));
				} else {
					return "0.00";
				}
			} catch (Exception e) {
				return "0.00";
			}
		}
	}

	/**
	 * 返回"净值"值（小数点后4位）
	 * @param obj Object 被转换的对象
	 * @return String 格式化后的字符串
	 */
	public static String obj2NetValue(Object obj) {
		if (obj == null) {
			return "";
		} else {
			String strobj = "";
			try {
				if (obj instanceof BigDecimal) {
					strobj =
						new Money(
							(BigDecimal) obj,
							4,
							BigDecimal.ROUND_HALF_UP)
							.toString();
					strobj = strobj.substring(1, strobj.length());
				} else if (obj instanceof String) {
					strobj =
						new Money(
							new BigDecimal((String) obj),
							4,
							BigDecimal.ROUND_HALF_UP)
							.toString();
					strobj = strobj.substring(1, strobj.length());
				} else {
					strobj = "";
				}
			} catch (Exception e) {
				strobj = "";
			}
			return strobj;
		}
	}
	/**
	 * 将元转换成分
	 * @param strMoney 输入的元
	 * @return int -1 失败，否则是转换成的分
	 */
	public static int getMoneyYtoF(String strMoney) {
		int iReturn = 0;
		try {
			iReturn =
				new Long(Math.round(Double.parseDouble(strMoney) * 100))
					.intValue();
		} catch (Exception e) {
			iReturn = 0;
		}
		return iReturn;
	}

	/**
	 * 将分转换成元
	 * @param iMoney 输入的分
	 * @return int -1 失败，否则是转换成的元
	 */
	public static String getMoneyFtoY(long iMoney) {
		String strReturn = "0";
		try {
			strReturn =
				java.text.NumberFormat.getInstance().format(iMoney / 100.0);
			if (strReturn.lastIndexOf(',') != -1) {
				char[] chars = strReturn.toCharArray();
				Vector v = new Vector();
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] != ',') {
						v.add(new java.lang.Character(chars[i]));
					}
				}
				chars = new char[v.size()];
				for (int i = 0; i < v.size(); i++) {
					Character ch = (Character) v.get(i);
					chars[i] = ch.charValue();
				}
				strReturn = new String(chars);
			}
		} catch (Exception e) {
			strReturn = "0";
		}
		return strReturn;
	}
	/**
	 * 把数值转换成.00格式
	 * @param lMoneyValue long
	 * @return String
	 * */
	public static String getMoneyFormat(long lMoneyValue) {
		double fValue = lMoneyValue / (float) 100.00;
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
		df.setDecimalSeparatorAlwaysShown(true);
		String strValue = df.format(fValue);
		int iLen = strValue.length();
		int iZeroIndex = strValue.lastIndexOf(".");
		for (int i = 0; i < iZeroIndex + 2 - (iLen - 1); i++) {
			strValue += "0";
		}
		return strValue;
	}
	public static String bigDecimal2VMoney(BigDecimal ym) {
		if (ym == null) {
			return "0";
		} else {
			BigDecimal money = new Money(ym).getAmount();
			NumberFormat df = NumberFormat.getCurrencyInstance(Locale.CHINA);
			String mStr = df.format(money);
			mStr = mStr.replaceAll("￥", "(e)");
			mStr = mStr.substring(0, mStr.length() - 3);
			return mStr;
		}
	}
}
