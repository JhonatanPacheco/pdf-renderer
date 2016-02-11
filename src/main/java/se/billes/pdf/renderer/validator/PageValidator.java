package se.billes.pdf.renderer.validator;

import java.io.File;

import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.Page;
import se.billes.pdf.renderer.request.PdfRequest;

import com.google.inject.Inject;

public class PageValidator implements IPdfRequestValidatable {

	@Inject Config config;
	
	@Override
	public void validate(PdfRequest request) throws PdfRequestNotValidException {
		
		if( request.getPages() == null || request.getPages().length == 0 ){
			throw new PdfRequestNotValidException( "Document must contain pages" );
		}
		
		for( Page page : request.getPages() ){
			if( page.getTemplate() != null ){
				if( page.getTemplate().getTemplatePath() == null ){
					throw new PdfRequestNotValidException( "path for template can not be null" );
				
				}else{
					File file = new File( config.getRun().getMountPath(), page.getTemplate().getTemplatePath());
					if( ! file.exists() ){
						throw new PdfRequestNotValidException( "Could not find file for template: " + file.getAbsolutePath() );
					}
					page.getTemplate().setTemplatePath(file.getAbsolutePath());
				}
				
				if( page.getTemplate().getPage() < 1 ){
					throw new PdfRequestNotValidException( "Page must begin with page 1 or more." );
				}
			}

		}
	}

}
