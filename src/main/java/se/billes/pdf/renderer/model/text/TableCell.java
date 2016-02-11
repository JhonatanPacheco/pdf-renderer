package se.billes.pdf.renderer.model.text;


public class TableCell extends Paragraph{
	
	private float[] padding = new float[]{0f,0f,0f,0f};


	public float[] getPadding() {
		return padding;
	}

	public void setPadding(float[] padding) {
		this.padding = padding;
	}
	
}
