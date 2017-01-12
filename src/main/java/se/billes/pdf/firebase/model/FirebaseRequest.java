package se.billes.pdf.firebase.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseRequest {
	public FirebaseRequestInput input;
	public String chainId;
	public Integer triggerIndex;
	public String key;
}
