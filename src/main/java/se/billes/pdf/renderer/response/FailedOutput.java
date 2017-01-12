package se.billes.pdf.renderer.response;

import java.util.List;

public class FailedOutput implements IOutput {
	
	private List<String> errors;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
