package se.billes.pdf.renderer.validator;

import se.billes.pdf.renderer.exception.CreateColorException;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Color;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.ColorFactory;

/**
 * Validates and creates BaseColor
 * @author Mathias
 *
 */
public class ColorValidator implements IPdfRequestValidatable{ 
	
	@Override
	public void validate(PdfRequest request) throws PdfRequestNotValidException {
		
		if( request.getColors() != null && request.getColors().length > 0 ){
			for( Color color : request.getColors() ){
				try {
					color.setBaseColor( new ColorFactory().createBaseColor(color));
				} catch (CreateColorException e) {
					throw new PdfRequestNotValidException(e);
				}
			}
		}
	}
}
