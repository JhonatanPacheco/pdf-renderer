package se.billes.pdf.firebase.model;

import java.util.List;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseDocument {
	public List<Integer> size;
	public Cutmarks cutmarks;
	public String name;
	public List<FirebasePage> pages;
	public List<Color> colors;
	public List<Font> fonts;
	
	class Cutmarks{
		public Boolean ignoreCutStroke;
	}
	
	class Color {
		public String ref;
		public List<Double> color;
	
	}
	
	class Font {
		public String ref;
		public String encoding;
		public String path;
	}
}
