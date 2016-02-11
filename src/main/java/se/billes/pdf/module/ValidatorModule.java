package se.billes.pdf.module;

import se.billes.pdf.renderer.validator.PdfRequestValidator;

import com.google.inject.AbstractModule;

public class ValidatorModule extends AbstractModule{

	@Override
	protected void configure() {
		
		bind(PdfRequestValidator.class).to(PdfRequestValidator.class);
	}

}
