package se.billes.pdf.renderer.validator;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.request.PdfRequest;

public class NameValidator implements IPdfRequestValidatable {

	@Override
	public void validate(PdfRequest request) throws PdfRequestNotValidException {
		if( request.getName() == null ){
			throw new PdfRequestNotValidException( "Name can not be null" );
		}
		
		if( ! request.getName().toLowerCase().endsWith( ".pdf") ){
			throw new PdfRequestNotValidException( "Name must have an extension of .pdf" );
		}
	}

}
