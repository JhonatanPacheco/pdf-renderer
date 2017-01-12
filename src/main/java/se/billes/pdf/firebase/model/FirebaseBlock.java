package se.billes.pdf.firebase.model;

import java.util.List;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class FirebaseBlock {
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Float> getPosition() {
		return position;
	}

	public void setPosition(List<Float> position) {
		this.position = position;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public String getVerticalAlign() {
		return verticalAlign;
	}

	public void setVerticalAlign(String verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public String getBackgroundRef() {
		return backgroundRef;
	}

	public void setBackgroundRef(String backgroundRef) {
		this.backgroundRef = backgroundRef;
	}

	public List<Float> getMargins() {
		return margins;
	}

	public void setMargins(List<Float> margins) {
		this.margins = margins;
	}

	public boolean isUseAscender() {
		return useAscender;
	}

	public void setUseAscender(boolean useAscender) {
		this.useAscender = useAscender;
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	private String type;
	private List<Float> position;
	private float radius;
	private int rotation;
	private String verticalAlign = "top";
	private String backgroundRef;
	private List<Float> margins;
	private boolean useAscender = false;
	private Border border;
	
	//paragraphs - An array of paragraphs. Will be discussed next.

	
	public FirebaseBlock(){
		
	}
	
	class Border {
		private float thickness;
		private String colorRef;
		
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
	}
}
