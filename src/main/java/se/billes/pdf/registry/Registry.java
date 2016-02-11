package se.billes.pdf.registry;

public class Registry {
	private String register;
	private int[] priorities;
	private String[] preCall;
	private Endpoint endpoint;
	
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public int[] getPriorities() {
		return priorities;
	}
	public void setPriorities(int[] priorities) {
		this.priorities = priorities;
	}
	public String[] getPreCall() {
		return preCall;
	}
	public void setPreCall(String[] preCall) {
		this.preCall = preCall;
	}
	
	public Endpoint getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}
	
	
}
