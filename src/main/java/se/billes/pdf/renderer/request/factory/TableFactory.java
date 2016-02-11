package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.exception.PdfRenderException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class TableFactory {
	
	public PdfPTable createTable( float widthInPs ){
		PdfPTable table = new PdfPTable(1);
		table.setTotalWidth(widthInPs);
		table.setLockedWidth( true );
		table.setSpacingBefore(0);
		table.setSpacingAfter(0);
		return table;
	}
	
	public void apply(ColumnText ct,PdfPTable table,PdfPCell cell ) throws PdfRenderException{
		table.addCell(cell);
		ct.addElement( table );
		
		try {
			ct.go();
		} catch (DocumentException e) {
			throw new PdfRenderException( e );
		}
	}
}
