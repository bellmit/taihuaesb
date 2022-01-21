package com.esb.distribute.service;


public class PsndocVO {
	@ElementField(key = "employeenumber")
	private String code;
	
	@ElementField(key = "employeename")
	private String name;
	
	@ElementField(key = "mobilephone")
	private String mobile;
	
	@ElementField(key = "orgcode",reference = "name")
	private String company;
	
	@ElementField(key = "departmentcode",reference = "code")
	private String dep;
	
	@ElementField
	private String jobStatus;
}
