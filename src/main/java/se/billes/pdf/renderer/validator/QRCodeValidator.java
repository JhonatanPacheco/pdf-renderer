package se.billes.pdf.renderer.validator;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.QRCode;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.ColorFactory;

import com.itextpdf.text.BaseColor;

public class QRCodeValidator {
	
	private Integer pageIndex;
	private Integer blockIndex;
	
	public QRCodeValidator withPageIndex( int index ){
		this.pageIndex = index;
		return this;
	}
	
	public QRCodeValidator withBlockIndex( int index ){
		this.blockIndex = index;
		return this;
	}
	
	public void validate(PdfRequest request, QRCode qr) throws PdfRequestNotValidException {
		
		DocumentErrorFactory errorFactory = new DocumentErrorFactory().withPageIndex(pageIndex).withBlockIndex(blockIndex);
		
		if( qr.getText() == null ){
			throw new PdfRequestNotValidException( errorFactory.appendErrorString( "You must specify text for qr") );
		}

		if( qr.getColorRef() == null ){
			qr.setBaseColor( new ColorFactory().getBlack() );
		}else{
			BaseColor color = new ColorFactory().getBaseColorByRef(request, qr.getColorRef() );
			if( color == null ){
				throw new PdfRequestNotValidException( errorFactory.appendErrorString( "Could not find color ref for qr") );
			}
			qr.setBaseColor(color);
		}
		
	}

}
