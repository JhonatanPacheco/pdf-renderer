package se.billes.pdf.renderer.process;

import java.io.FileReader;
import java.util.Date;

import se.billes.pdf.json.BlockTypeSelector;
import se.billes.pdf.registry.Config;
import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.validator.PdfRequestValidator;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;

public class Standalone {
	@Inject BlockTypeSelector blockTypeSelector;
	@Inject Config config;
	@Inject PdfRequestValidator pdfRequestValidator;
	
	public void run() throws PdfRenderException{
		
		try{
			Gson gson = blockTypeSelector.createGson();
			JsonReader reader = new JsonReader(new FileReader(config.getRun().getPathToJsonDocument()));
			final PdfRequest request = gson.fromJson(reader,PdfRequest.class);
			request.setStartExecutionTime(new Date().getTime());
			pdfRequestValidator.validateAll(request);
			new Renderer(request) {
				@Override
				public void onRendered(FileRendered fileRendered) {
					System.out.println(fileRendered);
				}
			}.onRender();
		}catch( Exception e ){
			throw new PdfRenderException(e);
		}
	}
}
