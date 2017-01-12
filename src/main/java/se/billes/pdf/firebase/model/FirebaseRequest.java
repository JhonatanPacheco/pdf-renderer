package se.billes.pdf.firebase.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseRequest {
	private FirebaseRequestInput input;
	private String chainId;
	private Integer triggerIndex;
	private String key;
	
	public FirebaseRequest() {
		
	}
	
	public FirebaseRequestInput getInput() {
		return input;
	}
	public void setInput(FirebaseRequestInput input) {
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
