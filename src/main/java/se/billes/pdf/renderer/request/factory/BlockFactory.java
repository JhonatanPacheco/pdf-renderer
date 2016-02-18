package se.billes.pdf.renderer.request.factory;

import se.billes.pdf.renderer.model.BaseElement;
import se.billes.pdf.renderer.model.Block;
import se.billes.pdf.renderer.request.PdfDocument;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
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
public class BlockFactory {
	
	private PdfDocument getPdfDocument(BaseElement block){
		return block.getPage().getPdfDocument();
	}
	
	public float[] getBoundsInMMIncludeCutmark( BaseElement block ){
		PdfDocument request =  getPdfDocument(block);
		float[] position = block.getPosition();
		float leftInMM = position[0];
		float topInMM = position[1];
		if(  request.getCutmarks() != null ){
			leftInMM += SizeFactory.CUT_MARK;
			topInMM -= SizeFactory.CUT_MARK;
		}
		
		float[] dest = new float[position.length];
		
		System.arraycopy(position, 0, dest, 0, position.length);
		dest[0] = leftInMM;
		dest[1] = topInMM;
		
		
		return dest;
	}
		
	public float[] getBoundsInPs( BaseElement block ){
		float[] position = getBoundsInMMIncludeCutmark(block);
		float[] dest = new float[position.length];
		System.arraycopy(position, 0, dest, 0, position.length);
		for(int i = 0; i < position.length; i++){
			dest[i] = SizeFactory.millimetersToPostscriptPoints(dest[i]);
		}
	
		return dest;
	}
	
	public void createRoundRectangle(PdfContentByte cb, Block block ){
		PdfDocument request =  getPdfDocument(block);
		float[] positions = getBoundsInPs(block);
		float[] mmPositions = getBoundsInMMIncludeCutmark(block);
		PdfTemplate tp = cb.createTemplate(positions[2],positions[3]);
	
		tp.roundRectangle(
				0, 
				0, 
				positions[2], 
				positions[3], 
				SizeFactory.millimetersToPostscriptPoints( block.getRadius() )
		);
		
		
		
		
		// because pdf i calculated from bottom up
		cb.addTemplate(
				tp, 
				positions[0], 
				SizeFactory.millimetersToPostscriptPoints( request.getSize()[1] - ( mmPositions[1] + mmPositions[3]))
		);

	}
	
	public ColumnText createColumn(PdfContentByte cb, Block block){
		
		ColumnText ct = new ColumnText( cb );
		PdfDocument request =  getPdfDocument(block);
		float[] mmPositions = getBoundsInMMIncludeCutmark(block);
		ct.setSimpleColumn( 
				SizeFactory.millimetersToPostscriptPoints( mmPositions[0] ), 
				SizeFactory.millimetersToPostscriptPoints( request.getSize()[1] - ( mmPositions[1] + mmPositions[3]) ), 
				SizeFactory.millimetersToPostscriptPoints(  mmPositions[0] +  mmPositions[2] ),
				SizeFactory.millimetersToPostscriptPoints( request.getSize()[1] -  mmPositions[1] ), 0, Element.ALIGN_LEFT );
		
		return ct;
	}
	
}
