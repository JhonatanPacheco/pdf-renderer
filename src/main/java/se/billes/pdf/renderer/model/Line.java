package se.billes.pdf.renderer.model;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;

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
public class Line extends BaseElement {
	
	private String colorRef;
	private BaseColor baseColor;
	private float thickness;
	private float[] moveTo;
	

	public String getColorRef() {
		return colorRef;
	}

	public void setColorRef(String colorRef) {
		this.colorRef = colorRef;
	}

	public BaseColor getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(BaseColor baseColor) {
		this.baseColor = baseColor;
	}

	

	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	public float[] getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(float[] moveTo) {
		this.moveTo = moveTo;
	}

	@Override
	public void onRender(PdfContentByte cb) throws PdfRenderException {
		
		Integer[] pageSizes = getPage().getPdfRequest().getSize();
		float[] pos = getPosition();
		float[] moveTo = getMoveTo();
		cb.setLineWidth( getThickness() );
		cb.setColorStroke(  getBaseColor() );		
		float y1 = pageSizes[1] + SizeFactory.CUT_MARK - pos[1]; // seams strange but pdf starts bottom and up.
		float y2 = pageSizes[1] + SizeFactory.CUT_MARK - moveTo[1];
		// x , y. y is backward in pdf so start pos is the entire page height
		cb.moveTo( SizeFactory.millimetersToPostscriptPoints( pos[0] + SizeFactory.CUT_MARK ) , SizeFactory.millimetersToPostscriptPoints( y1 ) );
		cb.lineTo( SizeFactory.millimetersToPostscriptPoints(  moveTo[0] + SizeFactory.CUT_MARK ) , SizeFactory.millimetersToPostscriptPoints( y2 ) );
		cb.stroke();
		
	}
}
