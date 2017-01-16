package se.billes.pdf.firebase.model;

import java.util.List;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebasePage {
	
	public List<FirebaseBlock> blocks;
	public Template template;
	
	class Template{
		public String templatePath;
		public Integer page;
	}
	
}
