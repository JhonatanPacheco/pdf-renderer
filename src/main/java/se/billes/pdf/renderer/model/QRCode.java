package se.billes.pdf.renderer.model;

import java.util.Hashtable;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.BlockFactory;
import se.billes.pdf.renderer.request.factory.ColorFactory;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;
import com.itextpdf.text.pdf.qrcode.ErrorCorrectionLevel;

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
public class QRCode extends BaseElement{
	
	private String text;
	private String colorRef;
	private BaseColor baseColor;
	
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@SuppressWarnings("unchecked")
	public void onRender( PdfContentByte cb ) throws PdfRenderException{
		
		float[] positions = new BlockFactory().getBoundsInPs(this);
		
		try{
			@SuppressWarnings("rawtypes")
			Hashtable hintMap = new Hashtable();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			PdfRequest req = getPage().getPdfRequest();
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(getText(), BarcodeFormat.QR_CODE, (int) (positions[2]),(int) (positions[3]), hintMap);
			int matrixWidth = byteMatrix.getWidth();
			int matrixHeight = byteMatrix.getHeight();
			float pageHeight = req.getSize()[1];
			float top = getPosition()[1];

			if (getPage().getPdfRequest().getCutmarks() != null) {
				pageHeight += SizeFactory.CUT_MARK * 2;
				top += SizeFactory.CUT_MARK;
			}

			cb.setColorFill(getBaseColor());
			float pageHeightInPs = SizeFactory.millimetersToPostscriptPoints(pageHeight);
			float topInPs = SizeFactory.millimetersToPostscriptPoints(top);
			for (int i = byteMatrix.getTopLeftOnBit()[0]; i < matrixWidth; i++) {
				for (int j = byteMatrix.getTopLeftOnBit()[0]; j < matrixHeight; j++) {
					if (byteMatrix.get(i, j)) {
						
						cb.rectangle(positions[0] + (i - byteMatrix.getTopLeftOnBit()[0]),pageHeightInPs - ((topInPs + 1) + (j - byteMatrix .getTopLeftOnBit()[0])), 1, 1);
					}
				}
			}

			cb.fill();
			cb.setColorFill(new ColorFactory().getBlack()); 
  
		}catch( Exception e ){
			
		}
	}
	
}
