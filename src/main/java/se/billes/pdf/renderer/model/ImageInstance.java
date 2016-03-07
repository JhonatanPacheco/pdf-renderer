package se.billes.pdf.renderer.model;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfReader;

public class ImageInstance {
	private Image image;
	private PdfReader pdfReader;
	
	public ImageInstance(Image image,PdfReader pdfReader){
		this.image = image;
		this.pdfReader = pdfReader;
	}

	public Image getImage() {
		return image;
	}

	public PdfReader getPdfReader() {
		return pdfReader;
	}
}
