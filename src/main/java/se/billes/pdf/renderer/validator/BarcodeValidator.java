package se.billes.pdf.renderer.validator;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Barcode;
import se.billes.pdf.renderer.model.Barcode.BarCodeType;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.ColorFactory;
import se.billes.pdf.renderer.request.factory.FontFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;

public class BarcodeValidator {
	
	private Integer pageIndex;
	private Integer blockIndex;
	
	public BarcodeValidator withPageIndex( int index ){
		this.pageIndex = index;
		return this;
	}
	
	public BarcodeValidator withBlockIndex( int index ){
		this.blockIndex = index;
		return this;
	}
	
	public void validate(PdfRequest request, Barcode barcode) throws PdfRequestNotValidException {
		
		DocumentErrorFactory errorFactory = new DocumentErrorFactory().withPageIndex(pageIndex).withBlockIndex(blockIndex);
		
		if( barcode.getCode() == null ){
			throw new PdfRequestNotValidException( errorFactory.appendErrorString( "Code can not be null" ) );
		}
		
		if( barcode.getBarColorRef() == null ){
			barcode.setBarBaseColor( new ColorFactory().getBlack() );
		}else{
			BaseColor color = new ColorFactory().getBaseColorByRef(request, barcode.getBarColorRef() );
			if( color == null ){
				throw new PdfRequestNotValidException( "Could not find color ref for barColorRef" );
			}
			barcode.setBarBaseColor( color );
		}
		
		if( barcode.getCodeColorRef() == null ){
			barcode.setCodeBaseColor( new ColorFactory().getBlack() );
		}else{
			BaseColor color = new ColorFactory().getBaseColorByRef(request, barcode.getCodeColorRef() );
			if( color == null ){
				throw new PdfRequestNotValidException( "Could not find color ref for codeColorRef" );
			}
			barcode.setCodeBaseColor( color );
		}
		
		if( barcode.getAlign() == null ){
			barcode.setAlign( "bottom" );
		}else{
			if( !( barcode.getAlign().equals( "top") || barcode.getAlign().equals( "bottom" )) ){
				throw new PdfRequestNotValidException( "Align is not right. Must be any of [top,bottom]" );
			}
		}
		
		if( barcode.getFontSize() <= 0 ){
			throw new PdfRequestNotValidException( "Font size must be greater than 0" );
		}
		
		if( barcode.getBarCodeType() == null ){
			throw new PdfRequestNotValidException( "You must specify type. Any of [ean13,ean8,upca,upce]" );
		}else{
			BarCodeType type = BarCodeType.getBarCodeType( barcode.getBarCodeType() );
			if( type == null ){
				throw new PdfRequestNotValidException( "You must specify type. Any of [ean13,ean8,upca,upce]" );
			}
		}
		
		if( barcode.getFontRef() != null ){
			BaseFont font = new FontFactory().getBaseFontByRef(request, barcode.getFontRef());
			if( font == null ){
				throw new PdfRequestNotValidException( "Could not find font the font ref" );
			}
			barcode.setBaseFont(font);
		}
	}

}
