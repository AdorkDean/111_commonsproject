package com.rc.commons.jms;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: MessageModel.java
 * @Description:
 * @author yinbinhome@163.com
 * @date 2011-12-4 上午12:03:59
 * @version V1.0
 */

public class MessageModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -407204008243729759L;
	
	//v1=送积分 v2=关键字 v3=
	private String version;
	
	
	
	//关键字
	private String keyword;
	
	//积分属性
	private Long userid;

	
    private Integer score;

    private Short opttype;

    private Long goodsid;

    private Date createDt;

    private Long opertor;

    private String remark; 
    //============================
    
    private String clickCount;
    
    private String clickType;
    
   
    
    
	public String getClickCount() {
		return clickCount;
	}

	public void setClickCount(String clickCount) {
		this.clickCount = clickCount;
	}

	public String getClickType() {
		return clickType;
	}

	public void setClickType(String clickType) {
		this.clickType = clickType;
	}

	public String getVersion() {
		return version;
	}
	
	
	// *********发送短信 邮件************
	
	private String userName;    //  用户名
	private String messageType; //  短信类型
	private String userPhone;   //  电话号码
	private String code; 		// 验证码
	private String order;  		// 用户订单
	private String postOrderCode; 	// 快递号
	


	private String postCompany;  	// 快递公司
	 private String postType; // 快递类型
	
//	private String orderTime;   // （订购）下单日期
//	private Double money;  		// 金额 （售价）
//	private String goodsName; 	// 商品名称
//	private String goodsSerial; // 商品编号 
//	private int goodsCount;  	// 商品数量 
	
	/**
	 * emailType=1. 提交订单邮件
	 * emailType=2. 支付成功
	 * emailType=3. 催款邮件 
	 * emailType=4. 商品出库 
	 * emailType=5. 订单完成
	 * emailType=6. 取消订单
	 * emailType=7. 上门自提
	 * emailType=8. 货到付款
	 */
	private Integer emailType; 
	

	//关键字构造器
	
	/**
	 * @param version =v1
	 * @param keyword
	 */
	public MessageModel(String version, String keyword) {
		super();
		this.version = version;
		this.keyword = keyword;
	}


	

	/**
	 * 积分赠送构造器
	 * @param version=v2
	 * @param userid
	 * @param score
	 * @param opttype
	 * @param goodsid
	 * @param createDt
	 * @param opertor
	 * @param remark
	 */
	public MessageModel(String version, Long userid, Integer score, Short opttype, Long goodsid, Date createDt, Long opertor, String remark) {
		super();
		this.version = version;
		this.userid = userid;
		this.score = score;
		this.opttype = opttype;
		this.goodsid = goodsid;
		this.createDt = createDt;
		this.opertor = opertor;
		this.remark = remark;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	
	public MessageModel(String version, Long goodsid,String clickCount, String clickType) {
		super();
		this.version = version;
		this.goodsid = goodsid;
		this.clickCount = clickCount;
		this.clickType = clickType;
	}
	
	public MessageModel(String version) {
		super();
		this.version = version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Short getOpttype() {
		return opttype;
	}

	public void setOpttype(Short opttype) {
		this.opttype = opttype;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Long getOpertor() {
		return opertor;
	}

	public void setOpertor(Long opertor) {
		this.opertor = opertor;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private MessageModel() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getPostOrderCode() {
		return postOrderCode;
	}

	public void setPostOrderCode(String postOrderCode) {
		this.postOrderCode = postOrderCode;
	}

	public String getPostCompany() {
		return postCompany;
	}

	public void setPostCompany(String postCompany) {
		this.postCompany = postCompany;
	}

	public Integer getEmailType() {
		return emailType;
	}

	public void setEmailType(Integer emailType) {
		this.emailType = emailType;
	}

	
	
	
	

}
