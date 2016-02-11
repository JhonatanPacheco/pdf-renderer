package se.billes.pdf.renderer.validator;

import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.request.factory.HyphenationFactory;

public class HyphenationValidator implements IPdfRequestValidatable{

	@Override
	public void validate(PdfRequest request) throws PdfRequestNotValidException {
		if( request.getHyphenation() != null ){
			request
			.setHyphenationAuto(
					new HyphenationFactory()
					.getHypenationAutoByLocale( request.getHyphenation())
			);
		}
	}

}
