package se.billes.pdf.renderer.model;

import com.itextpdf.text.pdf.BaseFont;

public class Font {
	
	private String ref;
	private String encoding;
	private String path;
	private BaseFont baseFont; // created when initialized
	
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public BaseFont getBaseFont() {
		return baseFont;
	}
	public void setBaseFont(BaseFont baseFont) {
		this.baseFont = baseFont;
	}
	
}
