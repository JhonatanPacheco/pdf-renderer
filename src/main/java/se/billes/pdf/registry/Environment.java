package se.billes.pdf.registry;

public class Environment {
	
	private String environment; // server, standalone
	private String pathToJsonDocument;
	private String mountPath;
	
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getPathToJsonDocument() {
		return pathToJsonDocument;
	}
	public void setPathToJsonDocument(String pathToJsonDocument) {
		this.pathToJsonDocument = pathToJsonDocument;
	}
	public String getMountPath() {
		return mountPath;
	}
	public void setMountPath(String mountPath) {
		this.mountPath = mountPath;
	}
	
	public boolean isStandAlone(){
		if( environment == null ) return false;
		if( environment.equals( "standalone")) return true;
		
		return false;
 	}
}
