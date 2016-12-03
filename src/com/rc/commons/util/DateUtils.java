package com.rc.commons.util;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期处理工具类
 * @version 1.0
 */
public class DateUtils {
	private static SimpleDateFormat dateformater;
	private static SimpleDateFormat timeformater;
	private static SimpleDateFormat dateTimeformater;
	private static long DAY_IN_MILLISECOND = 0x5265c00L;
	private SimpleDateFormat timestampformater;
	public DateUtils() {
	}
	
	/**
	 * 把字符型日期表达式转换成java.util.Date
	 * @param strDate 型如 "yyyy-M-dd" 的日期字符串
	 * @return 转换后的java.util.Date对象；转换失败返回null
	 */
	public Date parseDate(String strDate) {
		Date date = null;
		try {
			date = getDateFormater().parse(strDate);
		} catch (Exception ex) {
			//  System.err.println(ex.getMessage());
		}
		return date;
	}
	
	/**
	 * 把字符型时间表达式转换成java.util.Date
	 * @param strDateTime 型如 "yyyy-M-dd HH:mm" 的时间字符串
	 * @return 转换后的java.util.Date对象；转换失败返回null
	 */
	public Date parseDateTime(String strDateTime) {
		Date date = null;
		try {
			date = getDateTimeFormater().parse(strDateTime);
		} catch (Exception ex) {
			//  System.err.println(ex.getMessage());
		}
		return date;
	}
	
	/**
	 * 获取某天的开始时刻（零时零分零秒）
	 * @param date  
	 * @return
	 */
	public Date getTodayStart(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(11, 0);
		gc.set(12, 0);
		gc.set(13, 0);
		gc.set(14, 0);
		return gc.getTime();
	}
	
	/**
	 * 获取昨天的java.util.Date对象
	 * add by Fangbaling
	 */
	public Date getYesterday() {
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.DAY_OF_MONTH, gc.get(Calendar.DAY_OF_MONTH) - 1);
		return gc.getTime();
	}
	
	/**
	 * 获取某天的最后时刻（第二天的起点）
	 * @param date
	 * @return
	 */
	public Date getTodayEnd(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dateDayAdd(date, 1));
		gc.set(11, 0);
		gc.set(12, 0);
		gc.set(13, 0);
		gc.set(14, 0);
		return gc.getTime();
	}
	
	/**
	 * 获取日期所在月的第一天
	 * @param month 日期 
	 * @return
	 */
	public Date getMonthBegin(Date month) {
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(month);
		int year = ca.get(1);
		int mon = ca.get(2);
		GregorianCalendar gCal = new GregorianCalendar(year, mon, 1);
		return gCal.getTime();
	}
	
	/**
	 * 获取某个月的第一天
	 * @param year  年份
	 * @param month 月份(1-12)
	 * @return
	 */
	public Date getMonthBegin(int year, int month) {
		if (month <= 0 || month > 12) {
			throw new IllegalArgumentException("month must from 1 to 12");
		} else {
			GregorianCalendar gCal = new GregorianCalendar(year, month - 1, 1);
			return gCal.getTime();
		}
	}
	
	/**
	 * 获取日期所在月份最后一天
	 * @param month
	 * @return
	 */
	public Date getMonthEnd(Date month) {
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(month);
		int year = ca.get(1);
		int mon = ca.get(2);
		int lastDay = ca.getActualMaximum(5);
		GregorianCalendar gCal = new GregorianCalendar(year, mon, lastDay);
		return gCal.getTime();
	}

	/**
	 * 获取指定月份的最后一天
	 * @param year 年份
	 * @param month 月份(1-12)
	 * @return
	 */
	public Date getMonthEnd(int year, int month) {
		Date start = getMonthBegin(year, month);
		return getMonthEnd(start);
	}

	/**
	 * 把字符型时间表达式转换成java.sql.Timestamp
	 * @param strTimestamp 型如 "yyyy-M-dd HH:mm" 的日期字符串
	 * @return
	 */
	public Timestamp parseTimestamp(String strTimestamp) {
		return new Timestamp(parseDateTime(strTimestamp).getTime());
	}

	/**
	 * 把java.util.Date转换成"yyyy-M-dd"字符串
	 * @param date
	 * @return
	 */
	public String formatDate(Date date) {
		if (date != null)
			return getDateFormater().format(date);
		else
			return "";
	}

	/**
	 * 把java.util.Date转换成"HH:mm"时间字符串
	 * @param date
	 * @return
	 */
	public String formatTime(Date date) {
		return getTimeFormater().format(date);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public String formatDateTime(Date date) {
		return getDateTimeFormater().format(date);
	}

	public final Date dateMonthAdd(Date date, int months) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		//GregorianCalendar _tmp = cal;
		int m = cal.get(2) + months;
		if (m < 0)
			m -= 12;
		//GregorianCalendar _tmp1 = cal;
		cal.roll(1, m / 12);
		//GregorianCalendar _tmp2 = cal;
		cal.roll(2, months);
		return cal.getTime();
	}

	public final Date dateDayAdd(Date date, int days) {
		long now = date.getTime() + (long) days * DAY_IN_MILLISECOND;
		return new Date(now);
	}

	private DateFormat getDateFormater() {
		if (dateformater == null)
			dateformater = new SimpleDateFormat("yyyy-M-dd");
		return dateformater;
	}

	private DateFormat getTimeFormater() {
		if (timeformater == null)
			timeformater = new SimpleDateFormat("HH:mm:ss");
		return timeformater;
	}

	private DateFormat getDateTimeFormater() {
		if (dateTimeformater == null)
			dateTimeformater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateTimeformater;
	}
	//add by fangbaling
	public int getAge(java.util.Date birthday) {
		GregorianCalendar gc = new GregorianCalendar();
		int now = gc.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthday);
		int birthDate = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, now);
		if (gc.after(calendar))
			return (now - birthDate);
		else
			return (now - birthDate - 1);
	}
	//add by fangbaling
	public String getBirthday(java.util.Date birthday) {
		if (birthday == null) {
			return "未知";
		}
		SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
		String birthdayMonth = sdfMonth.format(birthday);
		SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
		String birthdayDay = sdfDay.format(birthday);
		int birthMonth = new Integer(birthdayMonth).intValue();
		int birthDay = new Integer(birthdayDay).intValue();
		return (birthMonth + "月" + birthDay + "日");
	}
	public String parseOracleDate(String dateStr) {
		dateStr = "TO_DATE('" + dateStr + " 00:00:00','YYYY-MM-DD HH24:MI:SS')";
		return dateStr;
	}
	public String parseOracleDate(Date date) {
		String dateStr = formatDateTime(date);
		System.out.println(dateStr);
		dateStr = "TO_DATE('" + dateStr + "','YYYY-MM-DD HH24:MI:SS')";
		return dateStr;
	}
	public String parseOracleTimestamp(Timestamp date) {
		String dateStr = formatTimestamp(date);
		System.out.println(dateStr);
		dateStr = "TO_TIMESTAMP('" + dateStr + "','YYYY-MM-DD HH24:MI:ss.FF3')";
		return dateStr;
	}
	public String formatTimestamp(Timestamp date) {
		System.out.println(date.toString());
		return getTimestampFormater().format(date);
	}
	private DateFormat getTimestampFormater() {
		if (timestampformater == null)
			timestampformater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return timestampformater;
	}
	public Date buildDate(String year, String month, String day) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		try {
			cal.set(
				new Long(year).intValue(),
				new Long(month).intValue() - 1,
				new Long(day).intValue());
			return cal.getTime();
		} catch (Exception e) {
			return null;
		}
	}
	public String getStar(Date date) {
		return getStar(date.getMonth(), date.getDate());
	}
	public String getStar(int month, int day) {
		int temp = (month + 1) * 100 + day;
		if (temp >= 321 && temp <= 419) {
			return "白羊座";
		}
		if (temp >= 420 && temp <= 520) {
			return "金牛座";
		}
		if (temp >= 521 && temp <= 621) {
			return "双子座";
		}
		if (temp >= 622 && temp <= 722) {
			return "巨蟹座";
		}
		if (temp >= 723 && temp <= 822) {
			return "狮子座";
		}
		if (temp >= 823 && temp <= 922) {
			return "处女座";
		}
		if (temp >= 923 && temp <= 1023) {
			return "天秤座";
		}
		if (temp >= 1024 && temp <= 1121) {
			return "天蝎座";
		}
		if (temp >= 1122 && temp <= 1221) {
			return "射手座";
		}
		if (temp >= 1222 && temp <= 1231) {
			return "魔羯座";
		}
		if (temp >= 101 && temp <= 119) {
			return "魔羯座";
		}
		if (temp >= 120 && temp <= 218) {
			return "水瓶座";
		}
		if (temp >= 219 && temp <= 320) {
			return "双鱼座";
		}
		return "";
	}
	
	public void main(String[] args) {
		Timestamp ta = new Timestamp(new Date().getTime());
		//String kk = DateUtils.parseOracleTimestamp(ta);
		//System.out.println(kk);
	}
}
