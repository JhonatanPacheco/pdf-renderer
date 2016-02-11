package se.billes.pdf.renderer.process;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.text.AbstractParagraph;
import se.billes.pdf.renderer.request.factory.CellFactory;

import com.itextpdf.text.pdf.PdfPCell;

public class RenderParagraphs {
	
	public void render( PdfPCell cell, Block block ) throws PdfRenderException{
		
		if( block.getParagraphs() != null || block.getParagraphs().length  > 0 ){
			for( AbstractParagraph paragraph : block.getParagraphs()  ){
				paragraph.onRender( cell );
			}
		}else{
			new CellFactory().renderEmptyCell(cell);
		}

	}
}
