package se.billes.pdf.firebase.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseRequestInput {
	public FirebaseDocument document;
	public String path;
	public String key;
	public Long startExecutionTime;
}
