package se.billes.pdf.request.incoming;

public class IncomingRequest {
	private InputRequest input;
	private String chainId;
	private Integer triggerIndex;
	private String key;
	
	public InputRequest getInput() {
		return input;
	}
	public void setInput(InputRequest input) {
		this.input = input;
	}
	public String getChainId() {
		return chainId;
	}
	public void setChainId(String chainId) {
		this.chainId = chainId;
	}
	public Integer getTriggerIndex() {
		return triggerIndex;
	}
	public void setTriggerIndex(Integer triggerIndex) {
		this.triggerIndex = triggerIndex;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
