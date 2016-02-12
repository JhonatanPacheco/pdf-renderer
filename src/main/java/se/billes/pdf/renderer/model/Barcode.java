package se.billes.pdf.renderer.model;

import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.BlockFactory;
import se.billes.pdf.renderer.request.factory.SizeFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BaseFont;
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
public class Barcode extends BaseElement{
	
	private String code;
	private float fontSize;
	private String barCodeType;
	private String barColorRef;
	private String codeColorRef;
	private BaseColor barBaseColor;
	private BaseColor codeBaseColor;
	private boolean showGuardBars;
	private float rotation;
	private String align; // top, bottom
	private String fontRef;
	private BaseFont baseFont;
	
	public enum BarCodeType{
		EAN13( "ean13",BarcodeEAN.EAN13 ),
		EAN8( "ean8" , BarcodeEAN.EAN8 ),
		UPCA( "upca", BarcodeEAN.UPCA ),
		UPCE( "upce", BarcodeEAN.UPCE );
		
		private String type;
		private int codeType;
		
		BarCodeType( String type, int codeType ){
			this.type = type;
			this.codeType = codeType;
		}
		
		public int getCodeType() {
			return codeType;
		}

		public String getType(){
			return type;
		}
		
		public static BarCodeType getBarCodeType( String type ){
			for( BarCodeType bar : values() ){
				if( bar.getType().equals( type )) return bar;
			}
			
			return null;
		}
	}
	
	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public void onRender( PdfContentByte cb ) throws PdfRenderException{
		
		float[] positions = new BlockFactory().getBoundsInPs(this);
		
		BarcodeEAN barcode = new BarcodeEAN();
		barcode.setCodeType( BarCodeType.getBarCodeType(barCodeType).codeType );
		barcode.setCode( getCode() );
		barcode.setSize( getFontSize() );
		barcode.setGuardBars(isShowGuardBars());
		barcode.setBarHeight( positions[3] );
		barcode.setTextAlignment(Element.ALIGN_RIGHT);
		if( getBaseFont() != null ){
			barcode.setFont(getBaseFont());
		}

		if( align.equals( "top" )){
			barcode.setBaseline( -1f );
		}
		
		PdfRequest request = getPage().getPdfRequest();
		float pageHeight = request.getSize()[1];
		float pageHeightInPs = SizeFactory.millimetersToPostscriptPoints(pageHeight);
		
		com.itextpdf.text.Image image  = barcode.createImageWithBarcode(cb, getBarBaseColor(), getCodeBaseColor());
		image.setRotationDegrees( rotation );
		image.setAbsolutePosition( positions[0] ,  pageHeightInPs - ( positions[1] + positions[3] + fontSize ) );
		try {
			cb.addImage( image );
		} catch (DocumentException e) {
			throw new PdfRenderException(e);
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



	public String getBarColorRef() {
		return barColorRef;
	}

	public void setBarColorRef(String barColorRef) {
		this.barColorRef = barColorRef;
	}

	public String getCodeColorRef() {
		return codeColorRef;
	}

	public void setCodeColorRef(String codeColorRef) {
		this.codeColorRef = codeColorRef;
	}

	public BaseColor getBarBaseColor() {
		return barBaseColor;
	}

	public void setBarBaseColor(BaseColor barBaseColor) {
		this.barBaseColor = barBaseColor;
	}

	public BaseColor getCodeBaseColor() {
		return codeBaseColor;
	}

	public void setCodeBaseColor(BaseColor codeBaseColor) {
		this.codeBaseColor = codeBaseColor;
	}

	public boolean isShowGuardBars() {
		return showGuardBars;
	}

	public void setShowGuardBars(boolean showGuardBars) {
		this.showGuardBars = showGuardBars;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getBarCodeType() {
		return barCodeType;
	}

	public void setBarCodeType(String barCodeType) {
		this.barCodeType = barCodeType;
	}

	public String getFontRef() {
		return fontRef;
	}

	public void setFontRef(String fontRef) {
		this.fontRef = fontRef;
	}

	public BaseFont getBaseFont() {
		return baseFont;
	}

	public void setBaseFont(BaseFont baseFont) {
		this.baseFont = baseFont;
	}

}
