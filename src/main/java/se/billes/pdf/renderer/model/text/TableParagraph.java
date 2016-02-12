package se.billes.pdf.renderer.model.text;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * This program is built on top of iText.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU Affero General Public License version 3 as published by the Free Software Foundation with 
 * the addition of the following permission added to Section 15 as permitted in Section 7(a): 
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY ITEXT GROUP NV, ITEXT GROUP 
 * DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of 
 * the GNU Affero General Public License along with this program; if not, 
 * see http://www.gnu.org/licenses/ or write to the Free Software Foundation, Inc., 51 Franklin Street, 
 * Fifth Floor, Boston, MA, 02110-1301 USA, or download the license from the following
 * URL: http://itextpdf.com/terms-of-use/ 
 * The interactive user interfaces in modified source and object code versions of this program must 
 * display Appropriate Legal Notices, as required under Section 5 of the GNU Affero General Public License.
 * In accordance with Section 7(b) of the GNU Affero General Public License, you must retain the producer line 
 * in every PDF that is created or manipulated using iText. You can be released from the requirements of the 
 * license by purchasing a commercial license. Buying such a license is mandatory as soon as you develop 
 * commercial activities involving the iText software without disclosing the source code of your own 
 * applications. These activities include: offering paid services to customers as an ASP, 
 * serving PDFs on the fly in a web application, shipping iText with a closed source product.
 */
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
