package se.billes.pdf.firebase.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseCutmarks {
	private boolean ignoreCutStroke = false;
	
	public FirebaseCutmarks() {
		
	}

	public boolean isIgnoreCutStroke() {
		return ignoreCutStroke;
	}

	public void setIgnoreCutStroke(boolean ignoreCutStroke) {
		this.ignoreCutStroke = ignoreCutStroke;
	}
	
}
