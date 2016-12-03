package com.rc.commons.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.rc.commons.Common;
/**
 * <p>公共方法类</p>
 * <p>数组通用类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class ArrayUtil {

	/**
	 * ArrayUtil的构造函数
	 */
	public ArrayUtil() {
	}

	/**
	 * 将String数组内的成员全部去处前后空格
	 * @param strArr String[]
	 */
	public static void doTrim(String[] strArr) {
		if (strArr != null) {
			for (int i = 0; i < strArr.length; i++) {
				strArr[i] = (strArr[i] != null ? strArr[i].trim() : null);
			}
		}
	}
	/**
	 * 将String2维数组内的成员全部去处前后空格
	 * @param strArr2D String[][]
	 */
	public static void doTrim(String[][] strArr2D) {
		if (strArr2D != null) {
			for (int i = 0; i < strArr2D.length; i++) {
				doTrim(strArr2D[i]);
			}
		}
	}
	/**
	 * 取得二维数组中的某一列
	 * @param strArr 二维数组
	 * @param num 第几列
	 * @return String[] 一维数组
	 */
	public static String[] getStrArr(String[][] strArr, int num) {
		if (strArr != null && (num >= 0 && num < strArr[0].length)) {
			String[] strRtn = new String[strArr.length];
			for (int i = 0; i < strArr.length; i++) {
				strRtn[i] = strArr[i][num];
			}
			return strRtn;
		}
		return null;
	}
	/**
	 * 判断一个字符串是否在一个字符串数组内
	 * @param strArr strArr
	 * @param str str
	 * @return boolean
	 */
	public static boolean isStrIn(String[] strArr, String str) {
		return getStrPos(strArr, str) < 0 ? false : true;
	}

	/**
	 * 判断一个整数值是否在一个整数数组内
	 * @param intArr intArr
	 * @param i i
	 * @return boolean
	 */
	public static boolean isIntIn(int[] intArr, int i) {
		return getIntPos(intArr, i) < 0 ? false : true;
	}

	public static int getIntPos(int[] intArr, int i) {
		String[] strArr = getStrArr(intArr);
		return getStrPos(strArr, String.valueOf(i));
	}
	/**
	 * 将字符串数组转换为带指定分隔符的字符串
	 * @param strArr String[]
	 * @param sepa String
	 * @return String
	 */
	public static String getStrJoin(String[] strArr, String sepa) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; strArr != null && i < strArr.length; i++) {
			sb.append(strArr[i]);
			if (i != (strArr.length - 1)) {
				sb.append(sepa);
			}
		}
		return sb.toString();
	}

	/**
	 * 转置一个二维Object数组
	 * @param inObj inObj
	 * @return Object[][]
	 */
	public static Object[][] getRotateArray(Object[][] inObj) {
		if (inObj == null
			|| (inObj != null && (inObj.length < 1 || inObj[0].length < 1))) {
			return null;
		}
		Object[][] outObj = new String[inObj[0].length][inObj.length];

		for (int i = 0; i < inObj[0].length; i++) {
			for (int j = 0; j < inObj.length; j++) {
				outObj[i][j] = inObj[j][i];
			}
		}
		return outObj;
	}

	/**
	 * 转置一个二维String数组
	 * @param inObj inObj
	 * @return String[][]
	 */
	public static String[][] getRotateArray(String[][] inObj) {
		if (inObj == null
			|| (inObj != null && (inObj.length < 1 || inObj[0].length < 1))) {
			return null;
		}
		String[][] outObj = new String[inObj[0].length][inObj.length];

		for (int i = 0; i < inObj[0].length; i++) {
			for (int j = 0; j < inObj.length; j++) {
				outObj[i][j] = inObj[j][i];
			}
		}
		return outObj;
	}

	/**
	 * 转换string[]为int[]
	 * @param strArr strArr
	 * @return int[]
	 */
	public static int[] getIntArr(String[] strArr) {
		if (strArr == null) {
			return null;
		}
		int[] intArr = new int[strArr.length];

		for (int i = 0; i < strArr.length; i++) {
			intArr[i] = Integer.parseInt(strArr[i]);
		}

		return intArr;
	}

	/**
	 * 转换String[]为double[]
	 * @param strArr strArr
	 * @return double[]
	 */
	public static double[] getDblArr(String[] strArr) {
		if (strArr == null) {
			return null;
		}
		double[] dblArr = new double[strArr.length];

		for (int i = 0; i < strArr.length; i++) {
			dblArr[i] = Double.parseDouble(strArr[i]);
		}

		return dblArr;
	}
	/**
	 * 将两个String[]合并
	 * @param strArr1 String[]
	 * @param strArr2 String[]
	 * @return String[]
	 */
	public static String[] getStrArrAdd(String[] strArr1, String[] strArr2) {
		Vector vct = getVector(strArr1);
		setVectorAppend(vct, strArr2);
		return getStrArr(vct);
	}
	/**
	 * 将double[]转换为String[]
	 * @param inDbls double[]
	 * @return String[]
	 */
	public static String[] getStrArr(double[] inDbls) {
		if (inDbls == null) {
			return null;
		}

		String[] strArr = new String[inDbls.length];

		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = String.valueOf(inDbls[i]);
		}

		return strArr;
	}
	/**
	 * 将int[]转换为String[]
	 * @param inInts int[]
	 * @return String[]
	 */
	public static String[] getStrArr(int[] inInts) {
		if (inInts == null) {
			return null;
		}

		String[] strArr = new String[inInts.length];

		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = String.valueOf(inInts[i]);
		}

		return strArr;
	}
	/**
	 * 修改String[]的长度
	 * @param strArr String[]
	 * @param iLen int
	 * @return String[]
	 */
	public static String[] getStrArrOfFixedLen(String[] strArr, int iLen) {
		if (strArr == null) {
			return null;
		} else if (strArr.length == iLen) {
			return strArr;
		} else {
			String[] newArr = new String[iLen];
			for (int i = 0; i < iLen; i++) {
				if (i < strArr.length) {
					newArr[i] = strArr[i];
				}
			}

			return newArr;
		}
	}

	/**
	 * 将一个Object Array转换成String Array
	 * @param inObj inObj
	 * @return String[]
	 */
	public static String[] getStrArr(Object[] inObj) {
		if (inObj == null) {
			return null;
		}
		String arrOut[] = new String[inObj.length];
		for (int i = 0; i < inObj.length; i++) {
			if (inObj[i] != null) {
				arrOut[i] = inObj[i].toString();
			} else {
				arrOut[i] = "";
			}
		}
		return arrOut;
	}

	/**
	 * 将一个存放String 的 Collection 转换成String 数组
	 * @param collection collection
	 * @return String[]
	 */
	public static String[] getStrArr(Collection collection) {
		if (collection == null) {
			return null;
		}
		return getStrArr(collection.toArray());
	}

	/**
	 * 转换boolean[]为String[]
	 * @param boolArr boolArr
	 * @return String[]
	 */
	public static String[] getStrArr(boolean[] boolArr) {
		if (boolArr == null) {
			return null;
		}
		String[] strArr = new String[boolArr.length];
		for (int i = 0; i < boolArr.length; i++) {
			strArr[i] = boolArr[i] ? "1" : "0";
		}
		return strArr;
	}

	/**
	 * 转换float[]为String[]
	 * @param inDbls inDbls
	 * @return String[]
	 */
	public static String[] getStrArr(float[] inDbls) {
		if (inDbls == null) {
			return null;
		}

		String[] strArr = new String[inDbls.length];

		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = String.valueOf(inDbls[i]);
		}

		return strArr;

	}

	/**
	 * 转换Date[]为String[]
	 * @param inDates inDates
	 * @return String[]
	 */
	public static String[] getStrArr(java.util.Date[] inDates) {
		if (inDates == null) {
			return null;
		}

		String[] strArr = new String[inDates.length];

		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = String.valueOf(inDates[i]);
		}

		return strArr;

	}

	/**
	 * 将一个存放String的Map 转换成String[][]数组
	 * @param map map
	 * @return String[][]
	 */
	public static String[][] getStrArr2D(Map map) {
		if (map == null) {
			return null;
		}

		String[][] strArr2D = new String[map.size()][2];
		Iterator it = map.keySet().iterator();
		for (int i = 0; it.hasNext(); i++) {
			strArr2D[i][0] = String.valueOf(it.next());
			strArr2D[i][1] = String.valueOf(map.get(strArr2D[i][0]));
		}

		return strArr2D;
	}

	/**
	 * 找出一个String在String 数组中的位置
	 * @param strArr strArr
	 * @param str str
	 * @return int
	 */
	public static int getStrPos(String[] strArr, String str) {
		int iPos = -1;

		for (int i = 0;
			strArr != null && str != null && i < strArr.length;
			i++) {
			//if(strArr[i].equals(str)){
			if (strArr[i] != null && strArr[i].equals(str)) {
				iPos = i;
				break;
			}
		}

		return iPos;
	}

	/**
	 *根据指定的字符将字符串分隔；返回String[]
	 * @param str str
	 * @param c c
	 * @return String[]
	 */
	public static String[] getStrArr(String str, char c) {
		return getStrArr(str, String.valueOf(c));
	}

	/**
	 *根据指定的字符串将字符串分隔；返回String[]
	 * @param strValue String
	 * @param sep 分隔符
	 * @return String[]
	 */
	public static String[] getStrArr(String strValue, String sep) {
		return getStrArr(Common.getStrList(strValue, sep));
	}
	/**
	 * 分析字符串,根据两个分隔符转换成String[][]
	 * @param str String
	 * @param sepFirst String
	 * @param sepSecond String
	 * @return String[][]
	 */
	public static String[][] getStrArr2D(
		String str,
		String sepFirst,
		String sepSecond) {
		int iFirst = 0;
		int iSecond = 0;
		String[] strArr = null;
		String[] linArr = null;
		String[][] strArr2D = null;
		Object[] objArr = null;

		if (str != null && str.length() > 0) {
			strArr = getStrArr(str, sepFirst);

			if (strArr != null) {
				iFirst = strArr.length;
				objArr = new Object[iFirst];

				for (int i = 0; i < iFirst; i++) {
					linArr = getStrArr(strArr[i], sepSecond);
					objArr[i] = linArr;
					if (linArr != null && linArr.length > iSecond) {
						iSecond = linArr.length;
					}
				}

				strArr2D = new String[iFirst][iSecond];

				for (int i = 0; i < iFirst; i++) {
					linArr = (String[]) objArr[i];
					for (int j = 0; j < iSecond; j++) {
						if (iSecond <= linArr.length) {
							strArr2D[i][j] = linArr[j];
						} else {
							strArr2D[i][j] = "";
						}
					}
				}
			}
		}

		return strArr2D;
	}

	/**
	 * 将一个String[]转换成Vector
	 * @param strArr strArr
	 * @return Vector
	 */
	public static Vector getVector(String[] strArr) {
		Vector vct = new Vector();
		setVectorAppend(vct, strArr);
		return vct;
	}

	/**
	 * 将String[]加到Vector中
	 * @param vct vct
	 * @param strArr strArr
	 */
	public static void setVectorAppend(Vector vct, String[] strArr) {
		if (vct != null && strArr != null) {
			for (int i = 0; i < strArr.length; i++) {
				vct.add(strArr[i]);
			}
		}
	}

	/**
	 * 将一个Object[]转换成Vector
	 * @param objArr objArr
	 * @return Vector
	 */
	public static Vector getVector(Object[] objArr) {
		Vector vct = new Vector();
		setVectorAppend(vct, objArr);
		return vct;
	}

	/**
	 * 将Object[]加到Vector中
	 * @param vct vct
	 * @param objArr objArr
	 */
	public static void setVectorAppend(Vector vct, Object[] objArr) {
		if (vct != null && objArr != null) {
			for (int i = 0; i < objArr.length; i++) {
				vct.add(objArr[i]);
			}
		}
	}

	/**
	 * 转换二维数组为HashMap
	 * @param strArr2D 二维数组
	 * @return HashMap
	 */
	public static HashMap getMap(String[][] strArr2D) {
		if (strArr2D == null) {
			return null;
		}
		HashMap map = new HashMap();
		for (int i = 0; i < strArr2D.length; i++) {
			map.put(strArr2D[i][0], strArr2D[i][1]);
		}
		return map;
	}

	/**
	 * 判断两组String数组包含内容是否相同,与顺序无关
	 * @param strArrIn1 strArrIn1
	 * @param strArrIn2 strArrIn2
	 * @return boolean
	 */
	public static boolean hasSameElements(
		String[] strArrIn1,
		String[] strArrIn2) {
		//Log.out.dPrintln("General.hasSameElements begin");
		if (strArrIn1 != null && strArrIn2 != null) {
			String[] strArr1 = (String[]) strArrIn1.clone();
			String[] strArr2 = (String[]) strArrIn2.clone();
			if (strArr1.length == strArr2.length) {
				Arrays.sort(strArr1);
				Arrays.sort(strArr2);
				int iSum = 0;
				for (int i = 0; i < strArr1.length; i++) {
					if (strArr1[i].equals(strArr2[i])) {
						iSum++;
					}
				}
				if (iSum == strArr1.length) {
					return true;
				}
			}

		} else if (strArrIn1 == strArrIn2) {
			//Log.out.dPrintln("General.hasSameElements out2");
			return true;
		}
		//Log.out.dPrintln("General.hasSameElements out3");
		return false;
	}

	/**
	 * 将一个String[] 转换成一个String[][],
	 * String[][]的第一维、第二维与String[]相同，
	 * @param strArr strArr
	 * @return String[][]
	 */
	public static String[][] getStrArr2DByPair(String[] strArr) {
		if (strArr == null) {
			return null;
		}

		String[][] strArr2D = new String[strArr.length][2];
		for (int i = 0; i < strArr.length; i++) {
			strArr2D[i][0] = strArr[i];
			strArr2D[i][1] = strArr[i];
		}

		return strArr2D;
	}
	/**
	 * 将两个String[]合并成一个Strng[][]
	 * @param strArrFirst String[]
	 * @param strArrSecond String[]
	 * @return String[][]
	 */
	public static String[][] getStrArr2DByJoin(
		String[] strArrFirst,
		String[] strArrSecond) {
		int iLength = 0;
		if (strArrFirst != null) {
			iLength = strArrFirst.length;
		}
		if (strArrSecond != null && strArrSecond.length > iLength) {
			iLength = strArrSecond.length;
		}

		String[][] strArr2D = new String[iLength][2];
		for (int i = 0; i < iLength; i++) {
			if (strArrFirst != null && i < strArrFirst.length) {
				strArr2D[i][0] = strArrFirst[i];
			}
			if (strArrSecond != null && i < strArrSecond.length) {
				strArr2D[i][1] = strArrSecond[i];
			}
		}
		return strArr2D;
	}
	/**
	 * 将二维数组加到Map里
	 * @param map map
	 * @param strArr2D 二维数组
	 */
	public static void setMapAppend(Map map, String[][] strArr2D) {
		if (map != null && strArr2D != null) {
			for (int i = 0; i < strArr2D.length; i++) {
				map.put(strArr2D[i][0], strArr2D[i][1]);
			}
		}
	}

	/**
	 * 按照数组中最长的元素补齐所有元素
	 * @param strArr strArr
	 * @param isRightAlign isRightAlign
	 */
	public static void doFillAll(String[] strArr, boolean isRightAlign) {
		int iCurLen;
		int iLen = getMaxLength(strArr);
		String spaces = null, strCur = null;

		for (int i = 0; strArr != null && i < strArr.length; i++) {
			strCur = strArr[i];

			if (strArr[i] != null) {
				iCurLen = strArr[i].trim().length();
				spaces = StringUtil.getStrSpace(iLen - iCurLen);
				if (isRightAlign) {
					strArr[i] = spaces + strCur;
				} else {
					strArr[i] = strCur + spaces;
				}
			} else {
				strArr[i] = StringUtil.getStrSpace(iLen);
			}
		}
	}

	/**
	 * 按照数组中最长的元素补齐所有元素
	 * @param strArr2D strArr2D
	 * @param strArr strArr
	 * @param colPos colPos
	 */
	public static void doSetColOfStrArr2D(
		String[][] strArr2D,
		String[] strArr,
		int colPos) {
		if (strArr2D != null && strArr != null && strArr2D.length > 0) {
			if (colPos < strArr2D[0].length && colPos >= 0) {
				for (int i = 0;
					i < strArr2D.length && i < strArr.length;
					i++) {
					strArr2D[i][colPos] = strArr[i];
				}
			}
		}
	}

	/**
	 * 获得数组中字符串的最大长度
	 * @param strArr strArr
	 * @return int
	 */
	public static int getMaxLength(String[] strArr) {
		int iLen = -1;
		if (strArr != null) {
			for (int i = 0; i < strArr.length; i++) {
				if (strArr[i] != null && strArr[i].length() > iLen) {
					iLen = strArr[i].length();
				}
			}
		}
		return iLen;
	}

	/**
	 * 获得一个Object数组的一部分
	 * @param objArr objArr
	 * @param iBegin iBegin
	 * @param iEnd iEnd
	 * @return Object[]
	 */
	public static Object[] getSubArr(Object[] objArr, int iBegin, int iEnd) {
		if (objArr == null) {
			return null;
		}
		if (objArr.length == 0) {
			return null;
		}
		if (iBegin >= iEnd) {
			return null;
		}
		if (iBegin >= objArr.length) {
			return null;
		}

		if (iEnd > objArr.length) {
			iEnd = objArr.length;
		}
		if (iBegin < 0) {
			iBegin = 0;
		}

		Object[] theArr = new Object[iEnd = iBegin];

		for (int i = iBegin; i < iEnd; i++) {
			theArr[i - iBegin] = objArr[i];
		}
		return theArr;
	}

	/**
	 * 从String中获取String[][]
	 * 一行为一维，第二维是用sepor隔开的，
	 * @param inStr inStr
	 * @param sepor sepor
	 * @return String[][]
	 */
	public static String[][] getStrArr2DSepByLine(String inStr, String sepor) {
		if (inStr == null) {
			return null;
		}
		String eol = "\r\n";
		String[][] strArr2D = getStrArr2D(inStr, eol, sepor);
		return strArr2D;
	}
	public static void main(String[] args) {
		String[] aa = new String[5];

	}
}
