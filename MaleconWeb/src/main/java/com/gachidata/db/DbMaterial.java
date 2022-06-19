package com.gachidata.db;

public class DbMaterial {
	private String connName;
	private String hostName;
	private String serviceName;
	private String portName;
	private String userName;
	private String password;
	
	public DbMaterial(String connName, String hostName, String serviceName, String portName, String userName,
			String password) {
		super();
		this.connName = connName;
		this.hostName = hostName;
		this.serviceName = serviceName;
		this.portName = portName;
		this.userName = userName;
		this.password = password;
	}
	
	public String getConnName() {
		return connName;
	}
	public void setConnName(String connName) {
		this.connName = connName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
