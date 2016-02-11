package se.billes.pdf.renderer.process;



import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.BaseElement;
import se.billes.pdf.renderer.model.Page;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Delegator for creating new pages. 
 * @author Mathias
 *
 */
public class NewPageRenderer {
	
	private Page page;
	
	public NewPageRenderer(Page page ){
		this.page = page;
	}
	
	public void render( PdfWriter writer, Document document ) throws PdfRenderException{
		writer.setPageEvent(page);
		if( page.getBlocks() == null || page.getBlocks().length == 0 ){
			writer.setPageEmpty( false );
			writer.newPage();
		}else{
			document.newPage();
			PdfContentByte cb = writer.getDirectContent();
			for( BaseElement block : page.getBlocks() ){
				block.onRender( cb );
			}
		}
	}
}
