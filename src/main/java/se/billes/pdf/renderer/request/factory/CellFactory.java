package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.alignment.VerticalAlign;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;

public class CellFactory {
	
	public PdfPCell createCell( Block block ){
		float[] margins = block.getMargins();
		PdfPCell cell =  new PdfPCell();
		cell.setBorderWidth(0);
		cell.setVerticalAlignment( VerticalAlign.getByName(block.getVerticalAlign()).getAlignment() );
		cell.setLeft(0);
		cell.setTop(0);
		cell.setRight(0);
		cell.setBottom(0);
		cell.setUseAscender( block.isUseAscender() );
		cell.setIndent(0);
		cell.setPaddingLeft( SizeFactory.millimetersToPostscriptPoints( margins[0]) );
		cell.setPaddingBottom( SizeFactory.millimetersToPostscriptPoints(margins[3]) );
		cell.setPaddingRight( SizeFactory.millimetersToPostscriptPoints(margins[1]) );
		cell.setPaddingTop( SizeFactory.millimetersToPostscriptPoints(margins[2]) );
		cell.setFixedHeight(SizeFactory.millimetersToPostscriptPoints( block.getPosition()[3] ));
		cell.setBorder(0);
		cell.setCellEvent( new CellBlockEvent().createEvent(block));
		cell.setRotation( block.getRotation() );
		return cell;
	}
	
	public void renderEmptyCell(PdfPCell cell ){
		Paragraph pr = new Paragraph();
		Chunk chunk = new Chunk(  " " );
		pr.add( chunk );
		cell.addElement( pr );
	}
	
	public PdfPCell getFillCell(){
		PdfPCell fillCell = new PdfPCell();
		fillCell.setBorderWidth( 0f );
		fillCell.setLeft(0);
		fillCell.setTop(0);
		fillCell.setRight( 0 );
		fillCell.setBottom( 0 );
		fillCell.setUseAscender( true );
		fillCell.setIndent(0);
		fillCell.setHorizontalAlignment( Element.ALIGN_LEFT );
		fillCell.setVerticalAlignment( Element.ALIGN_BOTTOM );
		fillCell.setPaddingLeft( 0f);
		fillCell.setPaddingBottom(0f);
		fillCell.setPaddingRight(0f );
		fillCell.setPaddingTop( 0f );
		fillCell.setBorder( 0 );
		renderEmptyCell(fillCell);
		
		return fillCell;
	}
}
