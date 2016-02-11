package se.billes.pdf.renderer.model;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.text.AbstractParagraph;
import se.billes.pdf.renderer.process.RenderParagraphs;
import se.billes.pdf.renderer.request.factory.BlockFactory;
import se.billes.pdf.renderer.request.factory.CellFactory;
import se.billes.pdf.renderer.request.factory.TableFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.HyphenationAuto;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class Block extends BaseBlock{
	
	
	private boolean useAscender;
	private float[] margins = new float[]{0,0,0,0}; // left,right,top,bottom
	private int rotation;
	private String backgroundRef;
	private BaseColor baseColor;
	private AbstractParagraph[] paragraphs;
	private String verticalAlign = "top"; // do not move to BaseBlock because image does not have a default value
	
	public HyphenationAuto getHyphenationAuto(){
		return getPage().getHyphenationAuto();
	}

	public String getVerticalAlign() {
		return verticalAlign;
	}
	public void setVerticalAlign(String verticalAlign) {
		this.verticalAlign = verticalAlign;
	}
	

	public boolean isUseAscender() {
		return useAscender;
	}

	public void setUseAscender(boolean useAscender) {
		this.useAscender = useAscender;
	}

	public float[] getMargins() {
		return margins;
	}

	public void setMargins(float[] margins) {
		this.margins = margins;
	}


	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public String getBackgroundRef() {
		return backgroundRef;
	}

	public void setBackgroundRef(String backgroundRef) {
		this.backgroundRef = backgroundRef;
	}

	public BaseColor getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}


	public AbstractParagraph[] getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(AbstractParagraph[] paragraphs) {
		this.paragraphs = paragraphs;
	}

	public void onRender( PdfContentByte cb ) throws PdfRenderException{
		float[] positions = new BlockFactory().getBoundsInPs(this);
		new BlockFactory().createRoundRectangle(cb, this);
		ColumnText ct = new BlockFactory().createColumn(cb,this);
		PdfPTable table = new TableFactory().createTable(positions[2]);
		PdfPCell cell = new CellFactory().createCell(this);
		new RenderParagraphs().render(cell, this);
		new TableFactory().apply(ct, table, cell);	
	}
}
