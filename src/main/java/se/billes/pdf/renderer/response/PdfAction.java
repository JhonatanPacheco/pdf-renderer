package se.billes.pdf.renderer.response;

import java.util.ArrayList;
import java.util.List;

public class PdfAction extends AbstractAction{
	
	private String message;
	private boolean success;
	private List<Param> params = new ArrayList<Param>();
	private long executionOfPdfRendering;
	private String file;
	private long totalTimeOfExecution;
	
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

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public long getExecutionOfPdfRendering() {
		return executionOfPdfRendering;
	}
	public void setExecutionOfPdfRendering(long executionOfPdfRendering) {
		this.executionOfPdfRendering = executionOfPdfRendering;
	}
	public long getTotalTimeOfExecution() {
		return totalTimeOfExecution;
	}
	public void setTotalTimeOfExecution(long totalTimeOfExecution) {
		this.totalTimeOfExecution = totalTimeOfExecution;
	}

}
