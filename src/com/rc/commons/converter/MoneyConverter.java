package com.rc.commons.converter;

import java.math.BigDecimal;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import com.rc.commons.util.Money;

/**
 * <p>公共方法类</p>
 * <p>Money类型转换</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 */

public final class MoneyConverter implements Converter {
	/**
	 * 初始值
	 */
	private Object defaultValue = null;
	/**
	 * 是否使用初始值
	 */
	private boolean useDefault = true;
	/**
	 * MoneyConverter类的构造函数
	 */
	public MoneyConverter() {
		this.defaultValue = null;
		this.useDefault = false;
	}
	/**
	 * MoneyConverter类的构造函数
	 * @param defaultValue 设置初始值
	 */
	public MoneyConverter(Object defaultValue) {
		this.defaultValue = defaultValue;
		this.useDefault = true;
	}
	/**
	 * 类型转换 Object类型转换为Money类型
	 * @param type 要转换的值的类型
	 * @param value 要转换的值
	 * @exception 如果转换不能成功则抛出ConversionException异常
	 * @return Object 转换之后的返回值
	 */
	public Object convert(Class type, Object value) {

		if (value == null || "".equals(value.toString().trim())) {
			return (defaultValue);
		} else {
			String strobj = "";
			try {
				if (value instanceof BigDecimal) {
					strobj =
						new Money(
							(BigDecimal) value,
							4,
							BigDecimal.ROUND_HALF_UP)
							.toString();
					strobj = strobj.substring(1, strobj.length());
				} else if (value instanceof String) {
					strobj =
						new Money(
							new BigDecimal((String) value),
							4,
							BigDecimal.ROUND_HALF_UP)
							.toString();
					strobj = strobj.substring(1, strobj.length());
				} else {
					strobj = "";
				}
			} catch (Exception e) {
				if (useDefault) {
					return (defaultValue);
				} else {
					throw new ConversionException(e);
				}
			}
			return strobj;
		}
	}
}
