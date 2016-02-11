package se.billes.pdf.renderer.model;

import com.itextpdf.text.BaseColor;

public class Border {
	
	private float thickness;
	private String colorRef;
	private BaseColor baseColor;
	
	public float getThickness() {
		return thickness;
	}
	public void setThickness(float thickness) {
		this.thickness = thickness;
	}
	public String getColorRef() {
		return colorRef;
	}
	public void setColorRef(String colorRef) {
		this.colorRef = colorRef;
	}
	public BaseColor getBaseColor() {
		return baseColor;
	}
	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}
}
