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
