package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.Border;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;

public class CellBlockEvent {
	
	public PdfPCellEvent createEvent(final Block block ){
		return new PdfPCellEvent(){
			public void cellLayout(PdfPCell cell, Rectangle rect,PdfContentByte[] canvas) {
				
				float radiusInPs = SizeFactory.millimetersToPostscriptPoints( block.getRadius() );
				PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
				PdfTemplate template = cb.createTemplate( rect.getWidth() , rect.getHeight());
				template.roundRectangle(0, 0,  rect.getWidth() ,rect.getHeight() , radiusInPs);
				template.clip();
				template.newPath();
				
				if( block.getBaseColor() != null ){
					template.setColorFill(block.getBaseColor());
				}
				
				Border border = block.getBorder();

				if( border != null ){
					template.setLineWidth( SizeFactory.millimetersToPostscriptPoints( border.getThickness()) );
					template.setColorStroke( border.getBaseColor());
				}
				
				template.roundRectangle(0, 0,  rect.getWidth() ,rect.getHeight() ,radiusInPs);
					
				if( block.getBaseColor() != null || border != null ){
					if( block.getBaseColor() != null &&  border != null ){
						template.fillStroke();
					}else if( block.getBaseColor() != null ){
						template.fill();
					}else{
						
						template.stroke();
					}
				}
				cb.addTemplate( template, rect.getLeft(), rect.getBottom());
			}
		};
	}
}
