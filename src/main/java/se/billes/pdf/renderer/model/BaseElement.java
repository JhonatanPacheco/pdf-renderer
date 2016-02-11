package se.billes.pdf.renderer.model;

import se.billes.pdf.renderer.exception.PdfRenderException;

import com.itextpdf.text.pdf.PdfContentByte;

public  class BaseElement {
	
	private float[] position;
	private Page page;
	private String type;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position = position;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public  void onRender( PdfContentByte cb ) throws PdfRenderException{
		
	}
}
