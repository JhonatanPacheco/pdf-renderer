package se.billes.pdf.renderer.validator;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.model.BaseElement;
import se.billes.pdf.renderer.model.Page;
import se.billes.pdf.renderer.request.PdfRequest;

/**
 * Validates the Json request. The order of the validators are
 * important. The ColorValidator needs to be in place before the block validator
 * @author Mathias
 *
 */
public class PdfRequestValidator {
	 
	List<IPdfRequestValidatable> validatables = new ArrayList<IPdfRequestValidatable>();
	@Inject SizeValidator sizeValidator;
	@Inject PathValidator pathValidator;
	@Inject PageValidator pageValidator;
	@Inject FontValidator fontValidator;
	
	public void validateAll(PdfRequest request) throws PdfRequestNotValidException{
		validatables.add( pathValidator );
		validatables.add( sizeValidator);
		validatables.add( new NameValidator() );
		validatables.add( new HyphenationValidator() );
		validatables.add( pageValidator );
		validatables.add( new ColorValidator() );
		validatables.add( fontValidator );
		validatables.add( new BaseElementValidator() );
		
		for( IPdfRequestValidatable validatable : validatables ){
			validatable.validate(request);
		}
		
		for( Page page : request.getPages() ){
			page.setPdfRequest(request);
			if( page.getBlocks() != null ){
				for( BaseElement block : page.getBlocks() ){
					block.setPage(page);
				}
			}
		}
		
	}
}
