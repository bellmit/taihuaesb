package com.esb.distributerz.Vo;

import java.io.Serializable;

public class MdMapingVO implements Serializable{

	private static final long serialVersionUID = -5488253314295792733L;
	private String mdmCode;
	private String entityCode;  
	private String busiDataId;
	private boolean success;
	private String message;
	public MdMapingVO() {
		super();
	}
	public MdMapingVO(String mdmCode, String entityCode, String busiDataId) {
		super();
		this.mdmCode = mdmCode;
		this.entityCode = entityCode;
		this.busiDataId = busiDataId;
	}
	public String getMdmCode() {
		return mdmCode;
	}
	public void setMdmCode(String mdmCode) {
		this.mdmCode = mdmCode;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getBusiDataId() {
		return busiDataId;
	}
	public void setBusiDataId(String busiDataId) {
		this.busiDataId = busiDataId;
	}
	@Override
	public String toString() {
		return "MdMapingVO [mdmCode=" + mdmCode + ", entityCode=" + entityCode
				+ ", busiDataId=" + busiDataId + "]";
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
