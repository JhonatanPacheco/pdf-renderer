package se.billes.pdf.firebase.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseRequestInput {
	private FirebaseDocument document;
	private String path;
	private String key;
	private Long startExecutionTime;
	
	public FirebaseRequestInput() {
		
	}
	
	public FirebaseDocument getDocument() {
		return document;
	}

	public void setDocument(FirebaseDocument document) {
		this.document = document;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getStartExecutionTime() {
		return startExecutionTime;
	}

	public void setStartExecutionTime(Long startExecutionTime) {
		this.startExecutionTime = startExecutionTime;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
