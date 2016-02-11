package se.billes.pdf.renderer.model;

public class BaseBlock extends BaseElement {
	
	private float radius;
	private Border border;
	
	
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public Border getBorder() {
		return border;
	}
	public void setBorder(Border border) {
		this.border = border;
	}
	
}
