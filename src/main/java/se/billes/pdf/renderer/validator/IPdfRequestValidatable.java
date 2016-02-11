package se.billes.pdf.renderer.validator;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.request.PdfRequest;

public interface IPdfRequestValidatable {
	
	void validate( PdfRequest request ) throws PdfRequestNotValidException;
}
