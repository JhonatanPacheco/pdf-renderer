package se.billes.pdf.renderer.validator;

import java.io.File;

import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.exception.CreateFontException;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Font;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.FontFactory;

import com.google.inject.Inject;

/**
 * Validates and creates Fonts
 * @author Mathias
 *
 */
public class FontValidator implements IPdfRequestValidatable{

	@Inject Config config;
	
	@Override
	public void validate(PdfRequest request) throws PdfRequestNotValidException {
		
		if( request.getFonts() != null && request.getFonts().length > 0 ){
			for( Font font : request.getFonts() ){
				try {
					File file = new File( config.getRun().getMountPath(),font.getPath() );
					if( ! file.exists() ){
						throw new PdfRequestNotValidException( "Font with path: " + file.getAbsolutePath() + " does not exists" );
					}
					font.setPath(file.getAbsolutePath());
					font.setBaseFont( new FontFactory().createBaseFont(font));
				} catch (CreateFontException e) {
					throw new PdfRequestNotValidException(e);
				}catch( Exception e ){
					throw new PdfRequestNotValidException(e);
				}
			}
		}else{
			throw new PdfRequestNotValidException( "You need to specify fonts" );
		}
	}
}