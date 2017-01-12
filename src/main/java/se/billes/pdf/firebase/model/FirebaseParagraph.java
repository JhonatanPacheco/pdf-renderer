package se.billes.pdf.firebase.model;

import java.util.List;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseParagraph {
	public Double leading;
	public String colorRef;
	public String fontRef;
	public String horizontalAlign;
	public Double fontSize;
	public List<Double> indent;
	public Boolean useHyphenation;
	public List<Double> widths; // TableParagraph
	public List<FirebasePhrase> phrases;
	public List<FirebasePhrase> cells; // TableParagraph

}
