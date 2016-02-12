package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.model.Border;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;

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
