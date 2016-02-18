package se.billes.pdf.renderer.process;

import se.billes.pdf.renderer.model.Cutmarks;
import se.billes.pdf.renderer.request.PdfDocument;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

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
public class CutmarksRenderer {
	
	private PdfDocument request;
	public CutmarksRenderer(PdfDocument request){
		this.request = request;
	}
	
	public void render(PdfWriter writer, Document document){
		
		Cutmarks cutmarks = request.getCutmarks();
		if(  cutmarks != null && ! cutmarks.isIgnoreCutStroke()  ){
			
			float width = SizeFactory.CUT_MARK;
			float height = SizeFactory.CUT_MARK;
			float lineWidth = SizeFactory.CUT_MARK_LINE;
			float lineHeight = SizeFactory.CUT_MARK_LINE;
			Rectangle rect = document.getPageSize();
			PdfContentByte cb = writer.getDirectContent();
			cb.setLineWidth( 0.1f );


			cb.moveTo( SizeFactory.millimetersToPostscriptPoints(width), 0);
			cb.lineTo( SizeFactory.millimetersToPostscriptPoints(width),SizeFactory.millimetersToPostscriptPoints(lineHeight));
			cb.stroke();
			
			
			cb.moveTo( 0, SizeFactory.millimetersToPostscriptPoints(height));
			cb.lineTo( SizeFactory.millimetersToPostscriptPoints(lineWidth),SizeFactory.millimetersToPostscriptPoints(height));
			cb.stroke();
			
			// Upper left cut mark
			cb.moveTo( SizeFactory.millimetersToPostscriptPoints(width), rect.getHeight() );
			cb.lineTo( SizeFactory.millimetersToPostscriptPoints(width), rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(lineHeight));
			cb.stroke();
			
			cb.moveTo( 0, rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(height));
			cb.lineTo( SizeFactory.millimetersToPostscriptPoints(lineWidth),rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(height));
			cb.stroke();
			
			/**
			 * Upper right cut mark
			 */
			cb.moveTo( rect.getWidth(), rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(height));
			cb.lineTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(lineWidth),rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(height));
			cb.stroke();
			cb.moveTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(width), rect.getHeight() );
			cb.lineTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(width),rect.getHeight() - SizeFactory.millimetersToPostscriptPoints(lineHeight));
			cb.stroke();
			
			/**
			 * Lower right cut mark
			 */
			cb.moveTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(width), 0 );
			cb.lineTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(width),SizeFactory.millimetersToPostscriptPoints(lineHeight));
			cb.stroke();
			
			cb.moveTo( rect.getWidth() , SizeFactory.millimetersToPostscriptPoints(height) );
			cb.lineTo( rect.getWidth() - SizeFactory.millimetersToPostscriptPoints(lineWidth),SizeFactory.millimetersToPostscriptPoints(height));
			cb.stroke();
		}
	}
}	
