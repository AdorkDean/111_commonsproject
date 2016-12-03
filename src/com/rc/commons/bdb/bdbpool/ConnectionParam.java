package com.rc.commons.bdb.bdbpool;

/**  
 * @Title: ConnectionParam.java
 * @Description: 
 * @author yinbinhome@163.com  
 * @date 2011-5-16 下午09:41:20
 * @version V1.0  
 */

public class ConnectionParam {
    private String bdbPath=""; // 数据库目录  
    private int minConnection=5; // 数据库连接池最小连接数  
    private int maxConnection=10; // 数据库连接池最大连接数  
    private long timeoutValue; // 连接的最大空闲时间  
    private long waitTime; // 取得连接的最大等待时间  
    private int incrementalConnections=5; //连接池自动增加连接的数量  
       
    
  
    public String getBdbPath() {
		return bdbPath;
	}

	public void setBdbPath(String bdbPath) {
		this.bdbPath = bdbPath;
	}

	public int getMinConnection() {   
        return minConnection;   
    }   
  
    public void setMinConnection(int minConnection) {   
        this.minConnection = minConnection;   
    }   
  
    public int getMaxConnection() {   
        return maxConnection;   
    }   
  
    public void setMaxConnection(int maxConnection) {   
        this.maxConnection = maxConnection;   
    }   
  
    public long getTimeoutValue() {   
        return timeoutValue;   
    }   
  
    public void setTimeoutValue(long timeoutValue) {   
        this.timeoutValue = timeoutValue;   
    }   
  
    public long getWaitTime() {   
        return waitTime;   
    }   
  
    public void setWaitTime(long waitTime) {   
        this.waitTime = waitTime;   
    }   
  
    public void setIncrementalConnections(int incrementalConnections) {   
        this.incrementalConnections = incrementalConnections;   
    }   
  
    public int getIncrementalConnections() {   
        return incrementalConnections;   
    }  
}
