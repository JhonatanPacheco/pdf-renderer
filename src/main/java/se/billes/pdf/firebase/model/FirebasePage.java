package se.billes.pdf.firebase.model;

import java.util.List;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebasePage {
	
	private List<FirebaseBlock> blocks;
	//private Template template;
	
	public FirebasePage() {
		
	}

	public List<FirebaseBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<FirebaseBlock> blocks) {
		this.blocks = blocks;
	}
}
