package se.billes.pdf.renderer.validator;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.request.PdfRequest;

/**
 * 
 * @author Mathias Nilsson
 *
 */
public class SizeValidator implements IPdfRequestValidatable{
	public void validate( PdfRequest request ) throws PdfRequestNotValidException{
		if( request.getSize() == null || request.getSize().length != 2 ){
			throw new PdfRequestNotValidException( "Size must be an array of [width,height]" );
		}
	}
}
