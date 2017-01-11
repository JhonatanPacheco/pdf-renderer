package se.billes.pdf.process;

import se.billes.pdf.firebase.FirebaseSchedule;
import se.billes.pdf.renderer.exception.PdfRenderException;
import se.billes.pdf.renderer.exception.PdfRequestNotValidException;
import se.billes.pdf.renderer.process.Renderer;
import se.billes.pdf.renderer.request.PdfRequest;
import se.billes.pdf.renderer.response.PdfAction;
import se.billes.pdf.renderer.response.PdfResponse;
import se.billes.pdf.renderer.validator.PdfRequestValidator;

import com.google.inject.Inject;

public class Delegator {
	@Inject PdfRequestValidator pdfRequestValidator;
	@Inject FirebaseSchedule firebaseSchedule;
	
	public void execute(PdfRequest request) {
		try {
			pdfRequestValidator.validateAll(request);
			new Renderer(request) {
				@Override
				public void onRendered(PdfResponse response) {
					
				}
				
			}.onRender();
		} catch (PdfRequestNotValidException e) {
			e.printStackTrace();
		} catch (PdfRenderException e) {
			e.printStackTrace();
		}
	}
	
	private static PdfResponse generateFailResponse(Exception e, PdfRequest request ){
		PdfResponse response = new PdfResponse();
		PdfAction action = new PdfAction();
		action.setMessage(e.getMessage());
		if( request != null )
		action.setParams(request.getParams());
		action.setSuccess(false);
		response.setAction(action);
		
		return response;
	}
	
}
