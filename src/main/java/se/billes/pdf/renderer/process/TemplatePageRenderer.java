package se.billes.pdf.renderer.process;

import java.io.IOException;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.BaseElement;
import se.billes.pdf.renderer.model.Page;
import se.billes.pdf.renderer.model.Template;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class TemplatePageRenderer {
	
	private Page page;
	
	public TemplatePageRenderer(Page page){
		this.page = page;
	}
	

	public void render( PdfWriter writer,Document document ) throws PdfRenderException{
		writer.setPageEvent( page );
		Template template = page.getTemplate();
		
		try {
			PdfReader reader = new PdfReader( template.getTemplatePath() );
			PdfImportedPage pageImportedPage = writer.getImportedPage(reader, template.getPage());
			Image pdfMirror;
			
			float width = SizeFactory.CUT_MARK;
			float height = SizeFactory.CUT_MARK;
			
			pdfMirror = Image.getInstance(pageImportedPage);
			pdfMirror.setAbsolutePosition( SizeFactory.millimetersToPostscriptPoints( width ),SizeFactory.millimetersToPostscriptPoints( height ));
			document.newPage();
			
			PdfContentByte cb = writer.getDirectContent();
			try {
				cb.addImage( pdfMirror );
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			if( page.getBlocks() != null ){
				for( BaseElement block : page.getBlocks() ){
					block.onRender( cb );
				}
			}
			
		} catch (BadElementException e) {
			e.printStackTrace();
			throw new PdfRenderException( e );
		} catch (IOException e) {
			throw new PdfRenderException( e );
		}
		
		
	}
}
