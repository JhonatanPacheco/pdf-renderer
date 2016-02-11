package se.billes.pdf.renderer.model.text;

import com.itextpdf.text.pdf.PdfPCell;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.model.Block;

public class AbstractParagraph {
	private Block block;
	
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
	public void onRender( PdfPCell cell ) throws PdfRenderException{
		
	}
}
