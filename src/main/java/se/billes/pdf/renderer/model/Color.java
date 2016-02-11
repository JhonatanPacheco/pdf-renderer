package se.billes.pdf.renderer.model;

import com.itextpdf.text.BaseColor;

public class Color {
	private String ref;
	private float[] color;
	private BaseColor baseColor;
	
	public BaseColor getBaseColor() {
		return baseColor;
	}
	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public float[] getColor() {
		return color;
	}
	public void setColor(float[] color) {
		this.color = color;
	}
	
	
	
	
}
