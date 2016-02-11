package se.billes.pdf.renderer.validator;

import java.io.File;

import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.request.PdfRequest;

import com.google.inject.Inject;

public class PathValidator implements IPdfRequestValidatable {

	@Inject Config config;
	
	@Override
	public void validate(PdfRequest request) throws PdfRequestNotValidException {
	
		
		if( request.getPath() == null ){
			throw new PdfRequestNotValidException( "Path can not be null" );
		}
		File file = new File(config.getRun().getMountPath(),request.getPath());
		
		if( ! file.exists()){
			throw new PdfRequestNotValidException( "Path " + file.getAbsolutePath() + " does not exists" );
		}
		
		request.setPath(file.getAbsolutePath());
	}

}
