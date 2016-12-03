package com.rc.commons.util;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>公共方法类</p>
 * <p>该类实现了 Money pattern，该设计模式是为了
 * 更好的处理特定精度的数值类型，在这里是人民币金额
 * ，加以一定的扩充，还可以处理多国货币</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class Money implements Comparable, Serializable {
	public static final Money ZERO = new Money("0");

	private BigDecimal amount;
	private int decimal;
	private int roundingMode;
	/**
	 * Money的构造函数
	 * @param amount String
	 */
	public Money(String amount) {
		if (amount != null && !"".equals(amount.trim())) {
			this.amount = init(new BigDecimal(amount));
		} else {
			this.amount = ZERO.getAmount();
		}
	}
	/**
	 * Money的构造函数
	 * @param amount BigDecimal
	 */
	public Money(BigDecimal amount) {
		this.amount = init(amount);
	}
	/**
	 * Money的构造函数
	 * @param amount BigDecimal
	 * @param decimal int
	 * @param roundingMode int
	 */
	public Money(BigDecimal amount, int decimal, int roundingMode) {
		this.amount = init(amount, decimal, roundingMode);
	}
	/**
	 * 初始化
	 * @param amount BigDecimal
	 * @return BigDecimal
	 */
	private BigDecimal init(BigDecimal amount) {
		return init(amount, 2, BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * 初始化
	 * @param amount BigDecimal
	 * @param decimal int
	 * @param roundingMode int
	 * @return BigDecimal
	 */
	private BigDecimal init(BigDecimal amount, int decimal, int roundingMode) {
		if (amount != null) {
			this.decimal = decimal;
			this.roundingMode = roundingMode;
			return amount.setScale(decimal, roundingMode);
		}

		return null;
	}

	/**
	 * 返回货币数量
	 *
	 * @return BigDecimal
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 实现比大小方法
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object obj) {
		Money other = (Money) obj;

		if (other == null || amount == null)
			return -1;

		return amount.compareTo(other.amount);
	}

	/**
	 * 判断是否小于0
	 *
	 * @return boolean
	 */
	public boolean isLessThanZero() {
		if (compareTo(Money.ZERO) == -1)
			return true;
		return false;
	}

	/**
	 * 加
	 * @param other 金额类型
	 * @return Money
	 */
	public Money add(Money other) {
		if (other != null && amount != null) {
			return new Money(
				amount.add(other.amount),
				this.decimal,
				this.roundingMode);
		}
		return this;
	}

	/**
	 * 减
	 * @param other 金额类型
	 * @return Money
	 */
	public Money subtract(Money other) {
		if (other != null && amount != null) {
			return new Money(
				amount.subtract(other.amount),
				this.decimal,
				this.roundingMode);
		}
		return this;
	}

	/**
	 * 与整数相乘
	 * @param value 整数
	 * @return Money 金额类型
	 */
	public Money multiply(int value) {
		if (amount == null)
			return this;

		return new Money(
			amount.multiply(new BigDecimal(String.valueOf(value))),
			this.decimal,
			this.roundingMode);
	}

	/**
	 * 与大数，并按特定取舍模式相乘
	 * @param value 大数
	 * @param roundingMode 特定取舍模式
	 * @return Money 金额类型
	 */
	public Money multiply(BigDecimal value, int roundingMode) {
		if (amount == null || value == null)
			return this;
		return new Money(
			amount.multiply(value).setScale(amount.scale(), roundingMode),
			this.decimal,
			this.roundingMode);
	}

	/**
	 * 判断金额类型是否相等
	 * @param o 金额类型的对象
	 * @return 是否相等
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Money))
			return false;

		Money other = (Money) o;
		if (amount != null)
			return other.amount.equals(amount);

		return false;
	}
	/**
	 * 哈希数
	 * @return int
	 */
	public int hashCode() {
		if (amount == null)
			return -1;
		return amount.hashCode();
	}
	/**
	 * 转换成String
	 * @return String
	 */
	public String toString() {
		if (amount == null) {
			return null;
		}
		StringBuffer result = new StringBuffer("￥");
		result.append(amount.toString());
		return result.toString();
	}

}
