package se.billes.pdf.renderer.model.text;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;

public class AbstractPhrase {
	
	private Paragraph paragraph;
	private BaseFont baseFont;
	private BaseColor baseColor;
	private Float fontSize;
	private String fontRef;
	private String colorRef;
	
	public Float getFontSize() {
		return fontSize;
	}

	public void setFontSize(Float fontSize) {
		this.fontSize = fontSize;
	}
	

	public Paragraph getParagraph() {
		return paragraph;
	}
	
	public void setParagraph(Paragraph paragraph) {
		this.paragraph = paragraph;
	}
	
	public BaseFont getBaseFont() {
		return baseFont;
	}

	public void setBaseFont(BaseFont baseFont) {
		this.baseFont = baseFont;
	}

	public BaseColor getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}
	
	public String getFontRef() {
		return fontRef;
	}

	public void setFontRef(String fontRef) {
		this.fontRef = fontRef;
	}

	public String getColorRef() {
		return colorRef;
	}

	public void setColorRef(String colorRef) {
		this.colorRef = colorRef;
	}
	
	public void onRender( com.itextpdf.text.Paragraph paragraph ){	

	}
	
}
