package com.gachidata.user;

public class User {
	private String name;
	public User(String name, String add, String dept) {
		super();
		this.name = name;
		this.add = add;
		this.dept = dept;
	}
	public User() {
		this.name = "½Å¿µ¸¸";
		this.add = "¶±ÀÙ";
		this.dept = "¿µ¾÷";
	}

	private String add;
	private String dept;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}


}
