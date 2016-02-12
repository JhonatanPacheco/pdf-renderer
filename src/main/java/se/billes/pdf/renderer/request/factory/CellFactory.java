package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.alignment.VerticalAlign;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;

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
