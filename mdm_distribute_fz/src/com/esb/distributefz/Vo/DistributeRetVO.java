package com.esb.distributefz.Vo;

import java.io.Serializable;
import java.util.List;


public class DistributeRetVO implements Serializable{

	private static final long serialVersionUID = 683574201644656079L;

	private List<MdMapingVO> mdMappings;
	private boolean success;
	private String message;

	public DistributeRetVO() {
		super();
	}

	public List<MdMapingVO> getMdMappings() {
		return mdMappings;
	}

	public void setMdMappings(List<MdMapingVO> mdMappings) {
		this.mdMappings = mdMappings;
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

	@Override
	public String toString() {
		return "DistributeRetVO [mdMappings=" + mdMappings + ", success="
				+ success + ", message=" + message + "]";
	}

}
