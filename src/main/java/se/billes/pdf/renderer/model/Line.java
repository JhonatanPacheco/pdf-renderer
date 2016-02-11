package se.billes.pdf.renderer.model;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;

public class Line extends BaseElement {
	
	private String colorRef;
	private BaseColor baseColor;
	private float thickness;
	private float[] moveTo;
	

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

	

	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	public float[] getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(float[] moveTo) {
		this.moveTo = moveTo;
	}

	@Override
	public void onRender(PdfContentByte cb) throws PdfRenderException {
		
		Integer[] pageSizes = getPage().getPdfRequest().getSize();
		float[] pos = getPosition();
		float[] moveTo = getMoveTo();
		cb.setLineWidth( getThickness() );
		cb.setColorStroke(  getBaseColor() );		
		float y1 = pageSizes[1] + SizeFactory.CUT_MARK - pos[1]; // seams strange but pdf starts bottom and up.
		float y2 = pageSizes[1] + SizeFactory.CUT_MARK - moveTo[1];
		// x , y. y is backward in pdf so start pos is the entire page height
		cb.moveTo( SizeFactory.millimetersToPostscriptPoints( pos[0] + SizeFactory.CUT_MARK ) , SizeFactory.millimetersToPostscriptPoints( y1 ) );
		cb.lineTo( SizeFactory.millimetersToPostscriptPoints(  moveTo[0] + SizeFactory.CUT_MARK ) , SizeFactory.millimetersToPostscriptPoints( y2 ) );
		cb.stroke();
		
	}
}
