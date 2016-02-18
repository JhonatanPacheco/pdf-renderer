package se.billes.pdf.registry;

import se.billes.pdf.renderer.response.AbstractAction;

public class RegistryAction extends AbstractAction{
	
	private int[] priorities;
	private String[] preCall;
	private Endpoint endpoint;
	private String plugin;
	
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
	public String getPlugin() {
		return plugin;
	}
	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}
	
}
