package se.billes.pdf.renderer.response;

import java.util.ArrayList;
import java.util.List;

public class Response {
	private String message;
	private boolean success;
	private List<Param> params = new ArrayList<Param>();
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<Param> getParams() {
		return params;
	}
	public void setParams(List<Param> params) {
		this.params = params;
	}
	
	
}
