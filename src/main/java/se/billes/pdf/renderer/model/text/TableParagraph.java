package se.billes.pdf.renderer.model.text;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class TableParagraph extends AbstractParagraph{
	
	private float[] widths;
	private AbstractParagraph[] cells;

	
	public float[] getWidths() {
		return widths;
	}


	public void setWidths(float[] widths) {
		this.widths = widths;
	}


	public AbstractParagraph[] getCells() {
		return cells;
	}


	public void setCells(AbstractParagraph[] cells) {
		this.cells = cells;
	}

	private float[] getTotalWidthsAsPs(){
		float[] newWidths = new float[widths.length];
		int columnCounter = 0;
		for( float width : widths ){
			newWidths[columnCounter] = SizeFactory.millimetersToPostscriptPoints(width);
			columnCounter ++;
		}
		
		return newWidths;
 	}
	

	@Override
	public void onRender( PdfPCell cell ) throws PdfRenderException{
		
		
		PdfPTable table = new PdfPTable( widths.length  );

		
		
		try{
			table.setTotalWidth( getTotalWidthsAsPs() );
			table.setLockedWidth( true );
			table.setSpacingAfter( 0f );

			for( AbstractParagraph tableCell : cells ){

				PdfPCell c = new PdfPCell();
				float[] padding = new float[]{0f,0f,0f,0f}; 
				if( tableCell instanceof TableCell ){
					padding = ((TableCell) tableCell).getPadding();
				}
				c.setBorderWidth( 0f );
				c.setLeft(0);
				c.setTop(0);
				c.setRight( 0 );
				c.setBottom( 0 );
				c.setUseAscender( true );
				c.setIndent(0);
				c.setHorizontalAlignment( Element.ALIGN_LEFT );
				c.setVerticalAlignment( Element.ALIGN_TOP );
				c.setPaddingLeft( SizeFactory.millimetersToPostscriptPoints(padding[0]));
				c.setPaddingBottom( SizeFactory.millimetersToPostscriptPoints(padding[3]) );
				c.setPaddingRight( SizeFactory.millimetersToPostscriptPoints(padding[2])  );
				c.setPaddingTop(SizeFactory.millimetersToPostscriptPoints(padding[1]));
				c.setBorder( 0 );
				tableCell.onRender( c );
				
				table.addCell( c );
				
		
			}
			cell.addElement( table );
		}catch( Exception e ){
			throw new PdfRenderException(e);
		}
	}
}
