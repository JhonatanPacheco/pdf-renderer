package se.billes.pdf.registry;

public class FirebaseConfig {
	private String serviceAccountFile;
	private String databaseURL;
	private String pluginPath;
	private String incomingPath;
	private String runningPath;
	private String completedPath;
	
	public String getServiceAccountFile() {
		return serviceAccountFile;
	}
	public void setServiceAccountFile(String serviceAccountFile) {
		this.serviceAccountFile = serviceAccountFile;
	}
	public String getDatabaseURL() {
		return databaseURL;
	}
	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}
	public String getPluginPath() {
		return pluginPath;
	}
	public void setPluginPath(String pluginPath) {
		this.pluginPath = pluginPath;
	}
	public String getIncomingPath() {
		return incomingPath;
	}
	public void setIncomingPath(String incomingPath) {
		this.incomingPath = incomingPath;
	}
	public String getRunningPath() {
		return runningPath;
	}
	public void setRunningPath(String runningPath) {
		this.runningPath = runningPath;
	}
	public String getCompletedPath() {
		return completedPath;
	}
	public void setCompletedPath(String completedPath) {
		this.completedPath = completedPath;
	}
}
