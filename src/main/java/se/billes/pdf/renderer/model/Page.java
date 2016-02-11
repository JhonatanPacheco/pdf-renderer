package se.billes.pdf.renderer.model;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.process.CutmarksRenderer;
import se.billes.pdf.renderer.process.NewPageRenderer;
import se.billes.pdf.renderer.process.TemplatePageRenderer;
import se.billes.pdf.renderer.request.PdfRequest;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.HyphenationAuto;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class Page extends PdfPageEventHelper{
	
	private BaseElement[] blocks;
	private Template template;
	private PdfRequest pdfRequest;
	
	public void onNewPage(PdfWriter writer, Document document ) throws PdfRenderException{
		if( template == null ){
			new NewPageRenderer(this).render(writer, document);
		}else{
			new TemplatePageRenderer(this).render(writer, document);
		}
	}
	
	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		new CutmarksRenderer(pdfRequest).render(writer, document);
	}
	
	public HyphenationAuto getHyphenationAuto(){
		return pdfRequest.getHyphenationAuto();
	}
	
	public BaseElement[] getBlocks() {
		return blocks;
	}

	public void setBlocks(BaseElement[] blocks) {
		this.blocks = blocks;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public PdfRequest getPdfRequest() {
		return pdfRequest;
	}

	public void setPdfRequest(PdfRequest pdfRequest) {
		this.pdfRequest = pdfRequest;
	}
}
