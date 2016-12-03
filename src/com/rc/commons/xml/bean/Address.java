package com.rc.commons.xml.bean;

/**  
 * @Title: Address.java
 * @Description: 
 * @author yinbinhome@163.com  
 * @date 2013-9-17 下午05:49:54
 * @version V1.0  
 */

public class Address {
	private String name;
	private String city;
	private String add;
	private String tel;
	
	public Address(String name, String city, String add, String tel) {
		super();
		this.name = name;
		this.city = city;
		this.add = add;
		this.tel = tel;
	}
	public Address() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
}
