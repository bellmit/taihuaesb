package com.esb.distribute.service;


public class DeptVO {
	@ElementField(key = "departmentcode")
	private String code;
	
	@ElementField(key = "departmentname")
	private String name;
	
	private String shortname;
	
	@ElementField(key = "orgcode",reference = "name")
	private String companyCode;
	

	private String deptLevel;
	
	@ElementField(key = "fathercode",reference = "name")
	private String parentCode;
	
	@ElementField(key = "enableflag")
	private String enableState;
}
